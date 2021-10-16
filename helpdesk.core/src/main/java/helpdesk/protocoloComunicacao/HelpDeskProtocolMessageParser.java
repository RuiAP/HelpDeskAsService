package helpdesk.protocoloComunicacao;

public interface HelpDeskProtocolMessageParser {

     HelpDeskProtocolRequest parse(int codigo, String mensagem);

}