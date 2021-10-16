package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Catalogo implements AggregateRoot<Identificador> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private Identificador identificador;

    @Column
    private DescricaoBreve descricaoBreve;

    @Column
    private DescricaoCompleta descricaoCompleta;

    @Column
    private TituloCatalogo tituloCatalogo;

    @OneToOne
    private NivelCriticidade nivelCriticidade;

    @Column
    private Icone icone;

    @ManyToMany
    private Set<Equipa> equipasComAcesso;

    @ManyToMany(targetEntity = Colaborador.class, fetch = FetchType.EAGER)
    private Set<Colaborador> colaboradoresResponsaveis;


    protected Catalogo() {
    }

    public Catalogo(Identificador identificador, DescricaoBreve descricaoBreve,
                    DescricaoCompleta descricaoCompleta, TituloCatalogo titulo,
                    Icone icone, Set<Equipa>equipasComAcesso,
                    Set<Colaborador> colaboradoresResponsaveis, NivelCriticidade nivelCriticidade) {

        Preconditions.nonNull(identificador, "Identificador não pode ser nulo");
        Preconditions.nonNull(descricaoBreve, "Descrição Breve não pode ser nula");
        Preconditions.nonNull(descricaoCompleta, "Descrição Completa não pode ser nula");
        Preconditions.nonNull(titulo, "Título do Catálogo não pode ser nula");
        Preconditions.nonNull(icone, "Ícone do Catálogo não pode ser nula");
        Preconditions.nonNull(equipasComAcesso, "Set de Equipas com acesso não pode ser nula");
        Preconditions.nonNull(colaboradoresResponsaveis, "Set de colaboradores responsáveis não pode ser nulo");
        Preconditions.nonNull(nivelCriticidade, "O nível de criticidade não pode ser nulo");

        this.identificador = identificador;
        this.descricaoBreve = descricaoBreve;
        this.descricaoCompleta = descricaoCompleta;
        this.tituloCatalogo = titulo;
        this.icone = icone;
        this.equipasComAcesso = equipasComAcesso;
        this.colaboradoresResponsaveis=colaboradoresResponsaveis;
        this.nivelCriticidade = nivelCriticidade;
    }


    public Set<Colaborador> getColaboradoresResponsaveis() {
        return colaboradoresResponsaveis;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Catalogo)) {
            return false;
        }

        final Catalogo that = (Catalogo) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public Identificador identity() {
        return this.identificador;
    }


    @Override
    public String toString() {
        return tituloCatalogo +" ("+identificador+") - "+ descricaoBreve + " - Nível" + nivelCriticidade.identity();
    }
}
