package helpdesk.protocoloComunicacao;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.InetAddress;
import java.util.Arrays;


public class HelpDeskProtocolServer {

    private static final Logger LOGGER = LogManager.getLogger(HelpDeskProtocolServer.class);

    private static class ClientHandler extends Thread {
        private  SSLSocket clientSocket;
        private final HelpDeskProtocolMessageParser parser;
        private boolean exitMessage = false;

        public ClientHandler(final SSLSocket socket, final HelpDeskProtocolMessageParser parser) {
            this.clientSocket = socket;
            this.parser = parser;
        }

        @Override
        public void run() {
            final InetAddress clientIP = clientSocket.getInetAddress();
            final int port = clientSocket.getPort();
            LOGGER.debug("Acepted connection from {}:{}", clientIP.getHostAddress(), port);

            try (DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream()) ){

                int codigo;
                String conteudo;

                while (!exitMessage) {

                    String[] mensagem = ProtocolMessage.receberMensagem(in);
                    codigo = Integer.parseInt(mensagem[0]);
                    conteudo = mensagem[1];


                    //LOGGER.debug("Received message:---- Code:{}, Content:{}", codigo, conteudo);
                    LOGGER.info("Received message:---- Code:{}", codigo);
                    if (codigo == ProtocolCodes.Fim.codigo()){
                        exitMessage=true;
                    }

                    final HelpDeskProtocolRequest request = parser.parse(codigo,conteudo);

                    final byte[] resposta = request.execute();
                    ProtocolMessage.enviarMensagem(resposta, out);

                    //LOGGER.debug("Sent message:-------- Code:{}, Content:{}",resposta[1], new String (Arrays.copyOfRange(resposta, 3, resposta.length)) );
                    LOGGER.info("Sent message:-------- Code:{}",resposta[1]);

                }
            } catch (final IOException e) {
                LOGGER.error(e);
            } finally {
                try {
                    clientSocket.close();
                    clientSocket = null;
                } catch (final IOException e) {
                    LOGGER.error("While closing the client socket", e);
                }
            }
            LOGGER.debug("Ending thread {}:{}\n", clientIP.getHostAddress(), port);
        }
    }

    /**
     * Wait for connections.
     *
     * @param port
     */
    @SuppressWarnings("java:S2189")
    private void listen(final int port, final HelpDeskProtocolMessageParser parser) {
        try (

            SSLServerSocket serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);

        ) {
            while (true) {
                final SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                new ClientHandler(clientSocket, parser).start();
            }
        } catch (final IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * Wait for connections.
     *
     * @param port
     * @param blocking
     *            if {@code true} the socket runs in its own thread.
     */
    public void start(final int port,final HelpDeskProtocolMessageParser parser, final boolean blocking) {


        if (blocking) {
            listen(port, parser);
        } else {
            new Thread(() -> listen(port,parser)).start();
        }
    }


}
