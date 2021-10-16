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
public class Password implements ValueObject{

    private String password;
    
    public Password(String password) {
        this.password = password;
    }
    
    protected Password() {
        //for ORM
    }

}
