package helpdesk.servicoEmails.impl;

import helpdesk.servicoEmails.EmailService;

public class EmailServiceMockImpl implements EmailService {


    @Override
    public boolean enviarEmail(String emailDestino, String assunto, String conteudo) {
        System.out.println();
        System.out.println("Email para: "+emailDestino);
        System.out.println("Assunto: "+assunto);
        System.out.println("Mensagem: "+conteudo);
        System.out.println();
        return true;
    }
}
