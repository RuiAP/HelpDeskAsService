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
public class ContactoTelefonico implements ValueObject {
    private String contactoTelefonico;
    private static final String PATTERN = "^[0-9]{9}$";
    
    public ContactoTelefonico(String contactoTelefonico) {
        //        if(contactoTelefonico.matches(PATTERN)) {
        this.contactoTelefonico = contactoTelefonico;
//        } else {
//            throw new IllegalArgumentException("O contacto telefónico deve conter só 9 algarismos");
//        }
    }

    protected ContactoTelefonico() {

    }

    @Override
    public String toString() {
        return String.format("Telefone: %s\n", contactoTelefonico);
    }
}
