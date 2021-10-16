/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.equipa;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;

import java.util.Objects;
import javax.persistence.*;


@Entity
public class Equipa implements AggregateRoot<CodigoEquipa> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoEquipa codigoEquipa;

    @Column
    private Acronimo acronimo;

    @Column
    private Designacao designacao;

    @OneToOne
    private Colaborador colaboradorReponsavel;

    @OneToOne
    private TipoEquipa tipoEquipa;

    public Equipa(String codigoEquipa, String acronimo, String designacao, Colaborador colaboradorReponsavel, TipoEquipa tipoEquipa) {

        if (codigoEquipa == null || acronimo == null || designacao == null || colaboradorReponsavel == null || tipoEquipa==null) {
            throw new IllegalArgumentException();
        }
        this.codigoEquipa = new CodigoEquipa(codigoEquipa);
        this.acronimo = new Acronimo(acronimo);
        this.designacao = new Designacao(designacao);
        this.colaboradorReponsavel = colaboradorReponsavel;
        this.tipoEquipa = tipoEquipa;
    }

    public Equipa() {
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
        Equipa equipa = (Equipa) o;
        return Objects.equals(codigoEquipa, equipa.codigoEquipa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoEquipa);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }


    @Override
    public CodigoEquipa identity() {
        return this.codigoEquipa;
    }


    @Override
    public String toString() {
        return designacao + " (" + acronimo+")";
    }
}
