/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.funcao;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Asus
 */
@Entity
public class Funcao implements AggregateRoot<Designacao>{
    
    @Version
    private Long version;
    
    @EmbeddedId
    private Designacao designacao;
    
    public Funcao(String designacao) {
        this.designacao = new Designacao(designacao);
    }
    
    protected Funcao() {
        //for ORM
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Funcao funcao = (Funcao) o;
        return Objects.equals(designacao, funcao.designacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designacao);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Designacao identity() {
        return this.designacao;
    }

    @Override
    public String toString() {
        return  designacao.value();
    }
}
