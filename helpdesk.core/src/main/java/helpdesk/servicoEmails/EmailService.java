package helpdesk.servicoEmails;

public interface EmailService {

    public boolean enviarEmail(String emailDestino,String assunto,  String conteudo);

}
