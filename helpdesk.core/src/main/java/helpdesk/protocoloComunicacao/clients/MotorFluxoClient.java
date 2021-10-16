package helpdesk.protocoloComunicacao.clients;


import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.Application;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Optional;

import static helpdesk.protocoloComunicacao.ProtocolCodes.*;
import static java.lang.Thread.sleep;


public class MotorFluxoClient {

    private static final Logger LOGGER = LogManager.getLogger(MotorFluxoClient.class);

    private static SSLSocket socket = null;

    private static DataOutputStream out = null;
    private static DataInputStream in = null;

    static MotorFluxoClient motorFluxoClient;



    private MotorFluxoClient() {

    }

    private static final Object lock = new Object();

    public  static MotorFluxoClient getInstance() {
        synchronized (lock){
            if(motorFluxoClient != null){
                return motorFluxoClient;
            }
        }
        motorFluxoClient =  new MotorFluxoClient();
        newConnection();
        return motorFluxoClient;
    }

    public static boolean isConnected(){
        return socket != null &&  socket.isConnected();
    }



    public static void closeConnection() {
        try {
            ProtocolMessage.enviarMensagem(Fim.codigo(), "", out);
            ProtocolMessage.receberMensagem(in);
            socket.close();
            socket=null;
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static void newConnection() {
        try {

                System.setProperty("javax.net.ssl.trustStore",Application.settings().getProperty("common.truststore.location"));
                System.setProperty("javax.net.ssl.trustStorePassword",Application.settings().getProperty("common.truststore.password"));

                InetAddress serverIp = InetAddress.getByName(Application.settings().getProperty("motorfluxoServer.ip"));
                int portNumber = Integer.parseInt(Application.settings().getProperty("motorfluxoServer.portNumber"));

                socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(serverIp,portNumber);

                OutputStream socketOut = socket.getOutputStream();
                out = new DataOutputStream(socketOut);

                InputStream socketIn = socket.getInputStream();
                in = new DataInputStream(socketIn);


        } catch (ConnectException ce){
            //LOGGER.error(ce);
            if(ce.getMessage().equals("Connection refused: connect")){
                System.out.println("Motor de Fluxo de Atividades não está disponível... a tentar reconectar.");
            }
        }catch (IOException e) {
            LOGGER.error(e);
        }
    }






    public String submeterPedido(String identificador) {
        try {
            ProtocolMessage.enviarMensagem(PedidoSubmetido.codigo(),identificador, out);
            String[] resposta = ProtocolMessage.receberMensagem(in);
            return resposta[0] + ": " + resposta[1];

        } catch (IOException e) {
            System.out.println("Erro ao comunicar com o motor de fluxos...");
            //LOGGER.error(e);
            newConnection();
            return null;
        }
    }


    public String infoTarefas() {
        Optional<Colaborador> opColab= AuthzRegistry.authorizationService().session()
                .flatMap(s -> new ListarColaboradorService().colaboradorByEmail(s.authenticatedUser().email().toString()));

        String userId = "";

        if(opColab.isEmpty()){
            throw new IllegalArgumentException("O systemUser atual não tem Colaborador ou Equipa associado.");
        }else{
            userId = opColab.get().identity().toString();
        }

        try {
            ProtocolMessage.enviarMensagem(InformacaoDeTarefas.codigo(),userId, out);
            String[] resposta =  ProtocolMessage.receberMensagem(in);
            return resposta[1];

        } catch (IOException e) {
            System.out.println("Erro ao comunicar com o motor de fluxos...");
            //LOGGER.error(e);
            newConnection();
            return null;
        }
    }


    public String realizarTarefa(String codigoTarefa) {

        try {
            ProtocolMessage.enviarMensagem(TarefaConcluida.codigo(),codigoTarefa, out);
            String[] resposta =  ProtocolMessage.receberMensagem(in);
            return resposta[0] + ": " + resposta[1];

        } catch (IOException e) {
            System.out.println("Erro ao comunicar com o motor de fluxos...");
            //LOGGER.error(e);
            newConnection();
            return null;
        }
    }

}
