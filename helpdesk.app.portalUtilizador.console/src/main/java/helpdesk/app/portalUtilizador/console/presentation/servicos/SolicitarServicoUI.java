package helpdesk.app.portalUtilizador.console.presentation.servicos;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.solicitacaoservico.application.pedido.SolicitarServicoController;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.domain.pedido.UrgenciaPedido;
import org.hibernate.mapping.Set;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SolicitarServicoUI extends AbstractUI {

    SolicitarServicoController controller;
    Pedido pedido;

    public SolicitarServicoUI() {
        this.controller = new SolicitarServicoController();
    }

    protected void escolheServico(Servico servico) {
        pedido = controller.escolheServico(servico);
    }

    @Override
    protected boolean doShow() {

        try {

            for (AtributoFormulario atributo: controller.dadosFormularioAPreencher()) {
                String value = "";
                if(!atributo.getTipoDadosBase().equals(TipoDadosBase.SELECAOVALORES))
                    value = Console.readLine(atributo.getEtiquetaApresentacao().toString() + ": "+ atributo.getDescricaoAjuda());
                else{
                    final SelectWidget<String> selector = new SelectWidget<String>("Selecione uma das opções:\n", atributo.getOptionValues());
                    selector.show();
                    value = selector.selectedElement();
                }
                controller.preencheAtributoFormulario(atributo, value);
            }

            Date data = Console.readDate("Data de resolução pretendida (dd/mm/aaaa)");
            LocalDateTime date = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            pedido.defineDataResolucaoPretendida(date);

            final SelectWidget<UrgenciaPedido> selector = new SelectWidget<UrgenciaPedido>("Selecione a urgência do pedido\n", Arrays.asList(UrgenciaPedido.values()));
            selector.show();
            UrgenciaPedido urgenciaPedido = selector.selectedElement();
            pedido.defineUrgenciaPedido(urgenciaPedido);

            while(Console.readBoolean("Deseja adicionar ficheiro? (s)Sim ou (n)Não")){
                String file = Console.readLine("Indique o caminho do ficheiro");
                pedido.adicionarFicheiro(file);
            }

            Pedido pedidoSolicitado = controller.submetePedido(pedido);
            if(pedidoSolicitado != null){
                System.out.println("Pedido solicitado corretamente:");
                System.out.println(pedidoSolicitado);
                return true;
            }else{
                System.out.println("Erro ao solicitar pedido. Operação cancelada.");
                return false;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return true;
        }
    }


    @Override
    public String headline() {
        return "Solicitação Serviço";
    }


//    private String readNonemptyLine(String prompt, String original) throws Exception {
//
//        String res = "";
//        if (original.isEmpty())
//            res = Console.readNonEmptyLine(prompt, "O campo não pode ser vazio.");
//        else
//            res = Console.readLine(prompt + " (" + original + ")");
//
//        if (res.equals("quit")) {
//            AskForSave();
//        }
//
//        if (!res.trim().isEmpty() && !res.equals(original))
//            return res;
//        else
//            return original;
//    }

}
