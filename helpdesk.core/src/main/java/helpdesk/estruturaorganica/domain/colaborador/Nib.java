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
public class Nib implements ValueObject {
    private String nib;
    private static final String PATTERN = "^[0-9]{16}$";
    
    public Nib(String nib) {
        this.nib = nib;
    }

    protected Nib() {

    }
    
    @Override
    public String toString() {
        return String.format("Nib: %s\n", nib);
    }   
}
