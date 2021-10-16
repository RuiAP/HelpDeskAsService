package helpdesk.nivelservico.domain.nivelcriticidade;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class NivelCriticidade implements AggregateRoot<Designacao> {

    @Version
    private Long version;

    @EmbeddedId
    Designacao designacao;

    @Column
    Etiqueta etiqueta;

    @Column
    Escala escala;

    @Column
    Cor cor;

    @Column
    Objetivo objetivoResolucao;

    /*@Column
    Objetivo objetivoAprovacao;*/


    public NivelCriticidade(String designacao, String etiqueta, int escala, int cor, String objetivoResolucao, String objetivoAprovacao) {
        this.designacao = new Designacao(designacao);
        this.etiqueta = new Etiqueta(etiqueta);
        this.escala = new Escala(escala);
        this.cor = new Cor(cor);
        this.objetivoResolucao = new Objetivo(objetivoResolucao);
        //this.objetivoAprovacao = new Objetivo(objetivoAprovacao);
    }

    protected NivelCriticidade(){
        //for ORM
    }
    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Designacao identity() {
        return designacao;
    }

    @Override
    public String toString() {
        return "NivelCriticidade " +
                designacao +
                " => objetivoResolucao " + objetivoResolucao ;
    }
}
