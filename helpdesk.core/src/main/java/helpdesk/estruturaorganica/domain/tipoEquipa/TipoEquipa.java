package helpdesk.estruturaorganica.domain.tipoEquipa;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

import java.awt.*;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class TipoEquipa implements AggregateRoot<CodigoUnicoTipoEquipa> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoUnicoTipoEquipa codigoUnico;

    @Column
    private Descricao descricao;

    @Column
    private Cor cor;

    public TipoEquipa(String codigoUnico, String descricao, Color cor) {

        Preconditions.nonNull(codigoUnico,"O código único do tipo de Equipa não pode ser nulo ou vazio");
        Preconditions.nonNull(descricao,"A descrição do tipo de Equipa não pode ser nula ou vazia");
        Preconditions.nonNull(cor,"É necessário definir a cor associada ao Tipo de Equipa");

        this.codigoUnico = new CodigoUnicoTipoEquipa(codigoUnico);
        this.descricao = new Descricao(descricao);
        this.cor = new Cor(cor.getRGB());
    }

    protected TipoEquipa() {
        //for ORM
    }

    private static boolean nameMeetsMinimumRequirements(final String name) {
        return !StringPredicates.isNullOrEmpty(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoEquipa tipoEquipa = (TipoEquipa) o;
        return Objects.equals(codigoUnico, tipoEquipa.codigoUnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoUnico);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public CodigoUnicoTipoEquipa identity() {
        return this.codigoUnico;
    }

    @Override
    public String toString() {
        return descricao.toString();
    }
}
