package helpdesk.solicitacaoservico.application.pedido;

import helpdesk.solicitacaoservico.domain.pedido.Pedido;

public class ConsultarPedidosController {

    ListarPedidoService pedidoService = new ListarPedidoService();

    public ConsultarPedidosController() {
    }



    //Sem validação de permissões para facilitar a demonstração
    public Iterable<Pedido> todosPedidos(){
        return pedidoService.todosPedidos();
    }

    public Iterable<Pedido> pedidosEmCurso(){
        return pedidoService.pedidosEmCursoPorData();
    }

    public Iterable<Pedido> pedidosConcluidos(){
        return pedidoService.pedidosConcluidosPorData();


    }

}
