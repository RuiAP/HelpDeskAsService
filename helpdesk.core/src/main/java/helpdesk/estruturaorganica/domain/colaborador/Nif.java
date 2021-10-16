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
public class Nif implements ValueObject {
    private String nif;
    private static final String NIF_PATTERN = "^[0-9]{9}$";
    
    public Nif(String nif) {
        this.nif = nif;
    }

    protected Nif() {

    }
    
    @Override
    public String toString() {
        return String.format("Nif: %s\n", nif);
    }
}
