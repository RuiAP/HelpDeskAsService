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
public class NomeBreve implements ValueObject {
    private static final String PATTERN = "^[A-Z a-z]+$";
    private String nomeBreve;
    
    public NomeBreve(String nome) {
            nomeBreve = nome;
    }

    protected NomeBreve() {

    }

    @Override
    public String toString() {
        return String.format("Nome breve: %s\n", this.nomeBreve);
    }
}
