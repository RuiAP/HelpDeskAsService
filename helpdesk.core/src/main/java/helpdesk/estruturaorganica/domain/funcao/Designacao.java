/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.funcao;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Asus
 */
@Embeddable
public class Designacao implements ValueObject, Comparable<Designacao> {
    private String designacao;
    
    public Designacao(String designacao) {
        this.designacao = designacao;
    }

    protected Designacao() {

    }


    public String value() {
        return designacao;
    }

    
    @Override
    public String toString() {
        return String.format("Designacao: %s\n", designacao);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Designacao designacao1 = (Designacao) o;
        return Objects.equals(designacao, designacao1.designacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designacao);
    }

    @Override
    public int compareTo(Designacao o) {
        return designacao.compareTo(o.designacao);
    }
}
