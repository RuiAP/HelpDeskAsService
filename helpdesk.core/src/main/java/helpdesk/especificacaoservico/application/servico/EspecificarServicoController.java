package helpdesk.especificacaoservico.application.servico;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.especificacaoservico.application.catalogo.ListarCatalogoService;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;

import java.util.*;

public class EspecificarServicoController {

    //private Servico servico;
    private final ServicoService svc;

//    private Map<String, Formulario> forms;
//    private Map<String, HashSet<AtributoFormulario>> atributos;

    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    private final ListarEquipaService listarEquipasService = new ListarEquipaService();
    private final ListarColaboradorService listarColaboradoresService = new ListarColaboradorService();
    private final ListarCatalogoService listarCatalogosService = new ListarCatalogoService();
    private final ListarServicoService listarServicoService = new ListarServicoService();

    public EspecificarServicoController() {
        svc = new ServicoService();
    }

    public Servico criaServico(String codigo, String titulo) {
        return  new Servico(codigo, titulo);
    }


    public String terminaEspecificacaoServico(Servico servico) {
        if (servico == null)
            throw new IllegalArgumentException("Serviço não pode ser nulo.");

        return  servico.defineEspecificacaoComoCompleto();
    }

    public Servico guardaServico(Servico servico) {
        if (servico == null)
            throw new IllegalArgumentException("Serviço não pode ser nulo.");

        return svc.adicionaServico(servico);
    }

    public Iterable<Catalogo> todosCatalogos() {
        return listarCatalogosService.todosCatalogos();
    }

    public Iterable<Colaborador> todosColaboradores() {
        return listarColaboradoresService.todosColaboradores();
    }

    public Iterable<Equipa> todasEquipas() {
        return listarEquipasService.todasEquipas();
    }

    public Iterable<Servico> servicosIncompletos() {
        return listarServicoService.servicosIncompletos();
    }



}
