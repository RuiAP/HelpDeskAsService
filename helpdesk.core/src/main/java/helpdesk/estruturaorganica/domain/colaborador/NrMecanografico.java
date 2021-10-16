/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.colaborador;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Asus
 */
@Embeddable
public class NrMecanografico implements ValueObject, Comparable<NrMecanografico> {

    private static final long serialVersionUID = 1L;

    private String nrMecanografico;
    
    public NrMecanografico(String nrMecanografico) {
        this.nrMecanografico = nrMecanografico;
    }
    
    protected NrMecanografico() {
        //for ORM
    }

    public static NrMecanografico valueOf(final String nrMecanografico) {
        return new NrMecanografico(nrMecanografico);
    }

    public String value(){
        return this.nrMecanografico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NrMecanografico)) return false;
        NrMecanografico that = (NrMecanografico) o;
        return Objects.equals(nrMecanografico, that.nrMecanografico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrMecanografico);
    }
    
    @Override
    public String toString() {
        return  nrMecanografico;
    }
    
    @Override
    public int compareTo(NrMecanografico o) {
        return this.nrMecanografico.compareTo(o.nrMecanografico);
    }
}
