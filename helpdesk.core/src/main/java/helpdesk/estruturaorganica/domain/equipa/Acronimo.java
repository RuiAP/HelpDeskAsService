/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.equipa;

/**
 *
 * @author danie
 */
import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Acronimo implements ValueObject, Comparable<Acronimo> {

    String acronimo;

    public Acronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public Acronimo() {
        // for ORM
    }

    @Override
    public String toString() {
        return acronimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Acronimo acronimo1 = (Acronimo) o;
        return Objects.equals(acronimo, acronimo1.acronimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acronimo);
    }

    @Override
    public int compareTo(Acronimo o) {
        return acronimo.compareTo(o.acronimo);
    }



}
