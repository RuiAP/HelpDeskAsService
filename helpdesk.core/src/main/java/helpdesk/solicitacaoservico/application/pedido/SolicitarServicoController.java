package helpdesk.solicitacaoservico.application.pedido;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.especificacaoservico.domain.servico.AtributoFormulario;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.domain.pedido.*;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

import java.util.*;


public class SolicitarServicoController {


    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    private final ListarPedidoService listarPedidoService = new ListarPedidoService();
    private final PedidoRepository pedidoRepository = PersistenceContext.repositories().pedidos();


    private Servico servico;
    private List<AtributoPreenchido> dadosAtributos;


    public SolicitarServicoController() {
        dadosAtributos = new LinkedList<>();
    }



    public Pedido escolheServico(Servico servico) {
        Colaborador colaboradorAtual = listarColaboradorService.colaboradorAtual();
        this.servico = servico;
        return new Pedido(listarPedidoService.findLastId(), servico, colaboradorAtual);

    }

    public List<AtributoFormulario> dadosFormularioAPreencher() {
        return servico.getFormularioSolicitacao().getAtributosFormulario();
    }

    public void preencheAtributoFormulario(AtributoFormulario atributoFormulario, String value) {
        dadosAtributos.add(new AtributoPreenchido(atributoFormulario.getNomeVariavel(),
                atributoFormulario.getEtiquetaApresentacao(),
                atributoFormulario.getTipoDadosBase(), value));
    }



    public Pedido submetePedido(Pedido pedido) throws Exception {

        Pedido p = null;
        pedido.guardarDadosFormulario(dadosAtributos);
        if (pedido.submetePedido()) {
            p = pedidoRepository.save(pedido);
            if(p!=null){
               notificarMotorFluxo(p);
            }
        }
        return pedidoRepository.ofIdentity(p.identity()).get();
    }


    private void notificarMotorFluxo(Pedido p){
        MotorFluxoClient.getInstance().submeterPedido(p.identity().toString() );

    }

    public Optional<Pedido> pedidoPorId(String id){
        return listarPedidoService.pedidoPorId(id);
    }
}
