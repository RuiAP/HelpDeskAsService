package helpdesk.protocoloComunicacao.clients;

import helpdesk.Application;
import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.execucaofluxo.domain.tarefas.TarefaAutomatica;
import helpdesk.persistence.PersistenceContext;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;


import static helpdesk.protocoloComunicacao.ProtocolCodes.*;
import static helpdesk.protocoloComunicacao.ProtocolCodes.Fim;
import static java.lang.Thread.sleep;

public class ExecutorTarefasClient {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorTarefasClient.class);

    private SSLSocket socket = null;

    private DataOutputStream out = null;
    private DataInputStream in = null;

    private static ExecutorTarefasClient executorTarefasClient;


    public  ExecutorTarefasClient() {
    }


    public  void closeConnection() {
        try {
            socket.close();
            socket=null;
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }


    public void getConnection(int portNumber) {
        try {

            if(socket==null){
                System.setProperty("javax.net.ssl.trustStore", Application.settings().getProperty("common.truststore.location"));
                System.setProperty("javax.net.ssl.trustStorePassword",Application.settings().getProperty("common.truststore.password"));

                InetAddress serverIp = InetAddress.getByName(Application.settings().getProperty("executorTarefas.ip"));
                //int portNumber = Integer.parseInt(Application.settings().getProperty("executorTarefas.portNumber"));

                String ports[] =  (Application.settings().getProperty("executorTarefas.portNumber")).split(";");
                int port = Integer.parseInt(ports[0]);
                try {
                    port = Integer.parseInt(ports[portNumber]);
                }catch (Exception ex){
                    LOGGER.error("Porta não encontrada "+ex.getMessage());
                }

                socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(serverIp,port);

                OutputStream socketOut = socket.getOutputStream();
                out = new DataOutputStream(socketOut);

                InputStream socketIn = socket.getInputStream();
                in = new DataInputStream(socketIn);
            }

        } catch (IOException e) {
            LOGGER.error(e);
            try{
                sleep(1000);
                getConnection(portNumber);
            } catch (Exception exception) {
                LOGGER.error(e);
            }
        }
    }



    public boolean realizarTarefaAutomatica(TarefaAutomatica tarefa, List<AtributoPreenchido> atributos) {
        String codigoTarefa = tarefa.identity().toString();

        try {
            String mensagem = construirMensagemRealizarTarefa(tarefa, atributos);
            ProtocolMessage.enviarMensagem(RealizarTarefaAutomatica.codigo(),mensagem, out);
            String[] resposta =  ProtocolMessage.receberMensagem(in);
            ProtocolMessage.enviarMensagem(Fim.codigo(),"", out);
            if( Integer.parseInt(resposta[0]) == RealizadoComSucesso.codigo()
            || Integer.parseInt(resposta[0]) == FeedbackRealizacaoTarefa.codigo()){
                return true;
            }else{
                LOGGER.debug("Tarefa '"+codigoTarefa+"' não foi realizada corretamente. "+resposta[1]);
                return false;
            }

        } catch (IOException e) {
            LOGGER.error(e);
            return false;
        }
    }


    private static String construirMensagemRealizarTarefa(TarefaAutomatica tarefa, List<AtributoPreenchido> atributos){
        StringBuilder sb = new StringBuilder();
        final String SEPARADOR = "--";

        sb.append(tarefa.identity().toString());
        sb.append(SEPARADOR);
        sb.append(tarefa.scriptDeRealizacao());
        sb.append(SEPARADOR);

        sb.append("emailcolaborador");
        sb.append(SEPARADOR);
        sb.append(tarefa.getPedido().solicitante().email());
        sb.append(SEPARADOR);
        sb.append("codigopedido");
        sb.append(SEPARADOR);
        sb.append(tarefa.getPedido().identity());
        sb.append(SEPARADOR);


        for(AtributoPreenchido atributo : atributos){
            sb.append(atributo.getNomeVariavel());
            sb.append(SEPARADOR);
            sb.append(atributo.getValue());
            sb.append(SEPARADOR);
        }

        return sb.toString();
    }

}
