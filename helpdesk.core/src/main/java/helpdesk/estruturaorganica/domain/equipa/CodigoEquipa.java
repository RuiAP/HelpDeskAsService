/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.equipa;

import eapli.framework.domain.model.ValueObject;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;

import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author danie
 */
@Embeddable
public class CodigoEquipa implements ValueObject, Comparable<CodigoEquipa> {

    String codigoEquipa;

    protected CodigoEquipa(String codigoEquipa) {
        this.codigoEquipa = codigoEquipa;
    }

    protected CodigoEquipa() {
        // for ORM
    }

    public static CodigoEquipa valueOf(final String codigoEquipa) {
        return new CodigoEquipa(codigoEquipa);
    }

    @Override
    public String toString() {
        return codigoEquipa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CodigoEquipa codigo1 = (CodigoEquipa) o;
        return Objects.equals(codigoEquipa, codigo1.codigoEquipa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoEquipa);
    }

    @Override
    public int compareTo(CodigoEquipa o) {
        return codigoEquipa.compareTo(o.codigoEquipa);
    }

}
