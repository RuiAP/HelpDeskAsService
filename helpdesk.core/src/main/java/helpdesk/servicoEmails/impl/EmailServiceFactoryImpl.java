package helpdesk.servicoEmails.impl;

import helpdesk.servicoEmails.EmailService;
import helpdesk.servicoEmails.EmailServiceFactory;

public class EmailServiceFactoryImpl implements EmailServiceFactory {

    @Override
    public EmailService emailService() {
        return new EmailServiceMockImpl();
    }


}
