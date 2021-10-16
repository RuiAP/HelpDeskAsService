/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.colaborador;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Embeddable;

/**
 *
 * @author Asus
 */
@Embeddable
public class EmailInstitucional implements ValueObject {
    private String emailInstitucional;
    private static final String PATTERN = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    
    public EmailInstitucional(String email) {
        if(email.matches(PATTERN)) {
            this.emailInstitucional = email;
        } else {
            throw new IllegalArgumentException("Email contém carateres ilegais");
        }
    }


    protected EmailInstitucional() {

    }

    public static EmailInstitucional valueOf(String emailInstitucional){
       return new EmailInstitucional(emailInstitucional);
    }

    
    @Override
    public String toString() {
        return emailInstitucional;
    }
}
