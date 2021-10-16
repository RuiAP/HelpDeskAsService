package helpdesk.especificacaoservico.application.catalogo;


import eapli.framework.application.UseCaseController;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.CatalogoBuilder;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;
import helpdesk.nivelservico.application.ListarNivelCriticidadeService;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;


@UseCaseController
public class CriarCatalogoController {

    CriarCatalogoService svc;
    CatalogoBuilder catalogoBuilder;
    Catalogo catalogo;

    ListarEquipaService listarEquipaService = new ListarEquipaService();
    ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    ListarNivelCriticidadeService listarNivelCriticidadeService = new ListarNivelCriticidadeService();


    public CriarCatalogoController() {
        catalogoBuilder = new CatalogoBuilder();
        svc = new CriarCatalogoService();
    }

    public Iterable<NivelCriticidade> todosNiveisCriticidade(){
        return listarNivelCriticidadeService.todosNiveisCriticidade();
    }

    public void atribuiParametros(String id, String titulo, String descBreve, String descCompleta, String icone) {
        catalogoBuilder.withIdentificador(id);
        catalogoBuilder.withTitulo(titulo);
        catalogoBuilder.withDescricaoBreve(descBreve);
        catalogoBuilder.withDescricaoCompleta(descCompleta);
        catalogoBuilder.withIcone(icone);
    }

    public void atribuiColaboradorResponsavel(Colaborador colaborador) {
        catalogoBuilder.adicionaColaboradorResponsavel(colaborador);
    }

    public void atribuiAcessoAEquipa(Equipa equipa) {
        catalogoBuilder.adicionaEquipaAosAcessos(equipa);
    }

    public void atribuiNivelCriticidade(NivelCriticidade nivelCriticidade) {
        catalogoBuilder.adicionaNivelCriticidade(nivelCriticidade);
    }

    public Catalogo build() {
        catalogo = catalogoBuilder.build();
        return  catalogo;
    }

    public Catalogo guardaCatalogo(){
        if(catalogo==null)
            return null;

        return svc.save(catalogo);
    }

    public Iterable<Equipa> listarEquipas(){
        return listarEquipaService.todasEquipas();
    }

    public Iterable<Colaborador> listarColaboradores(){
        return listarColaboradorService.todosColaboradores();
    }
}
