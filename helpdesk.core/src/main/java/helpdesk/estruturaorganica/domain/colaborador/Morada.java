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
public class Morada implements ValueObject {

    private String distrito;
    
    public Morada(String distrito) {
        this.distrito = distrito;
    }

    protected Morada(){};
    
    @Override
    public String toString() {
        return "Morada{" +
                "distrito='" + distrito + '\'' +
                '}';
    }
}
