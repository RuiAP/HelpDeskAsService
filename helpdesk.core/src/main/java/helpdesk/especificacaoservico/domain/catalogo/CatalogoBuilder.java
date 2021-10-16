package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.DomainFactory;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;

import java.util.HashSet;
import java.util.Set;

public class CatalogoBuilder  implements DomainFactory<Catalogo> {

    private Identificador identificador;

    private DescricaoBreve descricaoBreve;

    private DescricaoCompleta descricaoCompleta;

    private TituloCatalogo titulo;

    private Icone icone;

    private NivelCriticidade nivelCriticidade;

    private Set<Equipa> equipasComAcesso;

    private Set<Colaborador> colaboradoresResponsaveis;

    public CatalogoBuilder withIdentificador(String identificador) {
        this.identificador = new Identificador(identificador);
        this.equipasComAcesso = new HashSet<>();
        this.colaboradoresResponsaveis = new HashSet<>();
        return this;
    }

    public CatalogoBuilder withDescricaoBreve(String descricaoBreve) {
        this.descricaoBreve = new DescricaoBreve(descricaoBreve);
        return this;
    }

    public CatalogoBuilder withDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = new DescricaoCompleta(descricaoCompleta);
        return this;
    }

    public CatalogoBuilder withTitulo(String titulo) {
        this.titulo = new TituloCatalogo(titulo);
        return this;
    }

    public CatalogoBuilder withIcone(String icone) {
        this.icone = new Icone(icone);
        return this;
    }

    public boolean adicionaColaboradorResponsavel(Colaborador colaborador) {
        return colaboradoresResponsaveis.add(colaborador);
    }

    public boolean adicionaEquipaAosAcessos(Equipa equipa) {
        return equipasComAcesso.add(equipa);
    }

    public void adicionaNivelCriticidade(NivelCriticidade nivelCriticidade) {
        this.nivelCriticidade = nivelCriticidade;
    }

    @Override
    public Catalogo build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Catalogo(identificador, descricaoBreve,descricaoCompleta, titulo, icone, equipasComAcesso, colaboradoresResponsaveis, nivelCriticidade);
    }

}
