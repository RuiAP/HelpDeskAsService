package helpdesk.solicitacaoservico.application.pedido;


import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.especificacaoservico.application.catalogo.ListarCatalogoService;
import helpdesk.especificacaoservico.application.servico.ListarServicoService;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;

import java.util.Optional;


@UseCaseController
public class ConsultarServicosController {

    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();


    private final ListarCatalogoService listarCatalogoService = new ListarCatalogoService();
    private final ListarServicoService listarServicoService = new ListarServicoService();
    private final ListarColaboradorService listarColaboradorService = new ListarColaboradorService();


    private final Colaborador colaboradorAtual;

    public ConsultarServicosController() {
        this.colaboradorAtual = listarColaboradorService.colaboradorAtual();
    }





    public Iterable<Catalogo> catalogosPermitidos() {
        return listarCatalogoService.catalogosPorColaborador(colaboradorAtual);
    }

    public Iterable<Servico> servicosPermitidosByCatalogo(Catalogo catalogo){
        return listarServicoService.servicosPermitidosByCatalogo(catalogo.identity(), colaboradorAtual.identity());
    }

    public Iterable<Servico> servicosPermitidosByKeyword(String keyword){
        return listarServicoService.servicosPermitidosByKeyword(keyword, colaboradorAtual.identity());
    }


    public Iterable<Servico> servicosPermitidosByTitulo(String titulo) {
        return listarServicoService.servicosPermitidosByTitulo(titulo, colaboradorAtual.identity());
    }


    //não está a filtrar só os permitidos para efeitos de demonstração
    public Optional<Servico> servicoById(String codigoServico){
        return listarServicoService.servicoById(codigoServico);
    }
}
