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
public class EstadoCivil implements ValueObject {

    private String estadoCivil;
    
    public EstadoCivil(String designacao) {
        this.estadoCivil = designacao;
    }

    protected EstadoCivil() {

    }

    @Override
    public String toString() {
        return String.format("Estado civil: %s\n", estadoCivil);
    }
}
