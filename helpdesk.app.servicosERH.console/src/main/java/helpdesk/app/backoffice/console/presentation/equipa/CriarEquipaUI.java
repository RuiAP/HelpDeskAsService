package helpdesk.app.backoffice.console.presentation.equipa;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.app.backoffice.console.presentation.catalogo.ColaboradorPrinter;
import helpdesk.estruturaorganica.application.equipa.RegistarEquipaController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;

public class CriarEquipaUI extends AbstractUI {

    public RegistarEquipaController controller;


    public CriarEquipaUI() {
        this.controller = new RegistarEquipaController();
    }

    @Override
    protected boolean doShow() {
        System.out.println("Introduza os dados para criar equipa:");
        final String codigo = Console.readLine("Código");
        final String acronimo = Console.readLine("Acrónimo");
        final String designacao = Console.readLine("Designação");

        final Iterable<Colaborador> colaboradores = controller.colaboradoresPossiveis();

        final SelectWidget<Colaborador> selectorColaboradores = new SelectWidget<>("Selecione o colaborador responsável da equipa:", colaboradores,
                new ColaboradorPrinter());
        selectorColaboradores.show();
        final Colaborador colaboradorSelecionado = selectorColaboradores.selectedElement();



        final Iterable<TipoEquipa> tiposEquipas = controller.todosTiposEquipas();
        final SelectWidget<TipoEquipa> selectorTiposEquipas = new SelectWidget<>("Selecione um tipo de Equipa que identifique a equipa:", tiposEquipas,
                new TipoEquipaPrinter());
        selectorTiposEquipas.show();
        final TipoEquipa tipoEquipa = selectorTiposEquipas.selectedElement();


        Equipa equipa =  controller.RegistarEquipa(codigo, acronimo, designacao, colaboradorSelecionado,tipoEquipa);
        boolean resultado = equipa != null;

        if(resultado){
            System.out.println("Equipa criada:");
            System.out.println(equipa);
        }else{
            System.out.println("Erro ao criar a equipa. Equipa não foi gravada.");
        }

        return resultado;

    }


    @Override
    public String headline() {
        return "Criar nova Equipa";
    }


}
