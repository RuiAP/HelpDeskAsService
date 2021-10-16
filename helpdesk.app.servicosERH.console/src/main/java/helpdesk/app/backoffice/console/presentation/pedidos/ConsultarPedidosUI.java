package helpdesk.app.backoffice.console.presentation.pedidos;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListUI;
import helpdesk.solicitacaoservico.application.pedido.ConsultarPedidosController;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConsultarPedidosUI extends AbstractUI {

    ConsultarPedidosController controller = new ConsultarPedidosController();

    /**
     * 1 - todos os pedidos
     * 2 - pedidos em curso
     * 3 - pedidos concluidos
     */
    private int tipoPedidos;

    public ConsultarPedidosUI(int tipoPedidos) {
        this.tipoPedidos = tipoPedidos;
    }
    @Override
    protected boolean doShow() {
        Iterable<Pedido> pedidos = new LinkedList<>();

        if(tipoPedidos ==1){
            System.out.println("Histórico de todos os pedidos do sistema:\n");
            pedidos = controller.todosPedidos();
        } else if(tipoPedidos == 2){
            System.out.println("Todos os pedidos em curso:\n");
            pedidos = controller.pedidosEmCurso();
        }else if (tipoPedidos == 3){
            System.out.println("Todos os pedidos concluídos:\n");
            pedidos = controller.pedidosConcluidos();
        }else{
            System.out.println("Sem filtro selecionado.");
        }


        Iterator<Pedido> it = pedidos.iterator();
        Pedido p;
        int number = 1;

        if(it.hasNext()){

            while(it.hasNext()){
                p= it.next();
                System.out.println(number+". "+p+" está em estado "+p.estado());
                number++;
            }

        }else{
            System.out.println("\t\t------- Sem Pedidos a mostrar -------");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Consultar Pedidos";
    }
}
