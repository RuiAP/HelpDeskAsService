package helpdesk.app.portalUtilizador.console.presentation.servicos;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.solicitacaoservico.application.pedido.ConsultarServicosController;

public class ConsultarCatalogosUI extends AbstractUI {
    private final ConsultarServicosController controller = new ConsultarServicosController();



    @Override
    protected boolean doShow() {

        //Usar antes menu, submenu ou ListUI?

        Catalogo catalogoSelecionado = null;
        Servico servicoSelecionado = null;
        int optionSelectedServicos = -1;

        do {

            Iterable<Catalogo> catalogosPermitidos = controller.catalogosPermitidos();
            final SelectWidget<Catalogo> selectorCatalogo = new SelectWidget("Selecione um catálogo para visualizar os serviços correspondentes:", catalogosPermitidos);
            selectorCatalogo.show();
            catalogoSelecionado = selectorCatalogo.selectedElement();
            if (catalogoSelecionado == null) {
                System.out.println("Operação cancelada.");
                return false;
            }


            Iterable<Servico> servicosPermitidos = controller.servicosPermitidosByCatalogo(catalogoSelecionado);
            final SelectWidget<Servico> selectorServicos = new SelectWidget("Selecione o serviço a solicitar:", servicosPermitidos);
            selectorServicos.show();
            optionSelectedServicos = selectorServicos.selectedOption();
            servicoSelecionado = selectorServicos.selectedElement();

        }while(optionSelectedServicos ==  0);

        if(servicoSelecionado == null) {
            System.out.println("Operação cancelada");
            return false;
        }


        SolicitarServicoUI solicitarUi = new SolicitarServicoUI();
        solicitarUi.escolheServico(servicoSelecionado);
        solicitarUi.show();
        return true;

    }



    @Override
    public String headline() {
        return "Catálogos e serviços";
    }
}
