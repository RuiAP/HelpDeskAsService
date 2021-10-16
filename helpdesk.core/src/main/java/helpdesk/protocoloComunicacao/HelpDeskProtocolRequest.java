
package helpdesk.protocoloComunicacao;


import static helpdesk.protocoloComunicacao.ProtocolCodes.*;

public abstract class HelpDeskProtocolRequest {

    protected final String mensagem;
    protected final int codigo;


    protected HelpDeskProtocolRequest(final int codigo, final String mensagem){
        this.mensagem = mensagem;
        this.codigo = codigo;
    }




    /**
     * Executes the requested action and builds the response to the client.
     *
     * @return the response to send back to the client
     */
    public abstract byte[] execute();

    /**
     * Indicates the object is a goodbye message, that is, a message that will close the
     * connection to the client.
     *
     * @return {@code true} if the object is a a goodbye message.
     */
    public boolean isExitMessage() {
        return this.codigo == Fim.codigo();
    }

    public static HelpDeskProtocolRequest createRequest(final int codigo, final String mensagem) {
        return new HelpDeskProtocolRequest(codigo, mensagem){
            @Override
            public byte[] execute() {
                return ProtocolMessage.construirResposta(super.codigo, super.mensagem);
            }
        };
    }

    protected static HelpDeskProtocolRequest erroDeConteudo() {
        return createRequest(ErroDeConteudo.codigo(), "Erro de conteudo /Bad Request");
    }

    protected static HelpDeskProtocolRequest erroDeServidor() {
        return createRequest(ErroDoServidor.codigo(), "Erro do servidor/Server Error.");
    }


}
