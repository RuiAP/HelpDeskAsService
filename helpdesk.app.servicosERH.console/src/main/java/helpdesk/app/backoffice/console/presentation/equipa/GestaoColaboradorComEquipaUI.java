package helpdesk.app.backoffice.console.presentation.equipa;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.app.backoffice.console.presentation.catalogo.ColaboradorPrinter;
import helpdesk.app.backoffice.console.presentation.catalogo.EquipaPrinter;
import helpdesk.estruturaorganica.application.colaborador.GestaoColaboradorEquipaController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public abstract class GestaoColaboradorComEquipaUI extends AbstractUI {

    
    GestaoColaboradorEquipaController controller;

    /**
     * True se for para associar e false se for para remover
     */
    private boolean associacao;


    public GestaoColaboradorComEquipaUI(){
        this.controller = new GestaoColaboradorEquipaController();
    }

    @Override
    protected boolean doShow() {
        return false;
    }

    @Override
    public String headline() {
        return "Associar/Remover Colaboradoer";
    }

    public void escolheAssociacao(){
        associacao=true;
    }
    public void escolheRemocao(){
        associacao=false;
    }



    protected void mostrarEquipasRestantes(Colaborador colaborador){
        Iterable<Equipa> equipasRestantes = controller.listarEquipasColab(colaborador);
        if(!equipasRestantes.iterator().hasNext()){
            System.out.println("--- sem equipas a mostrar ---");
        }else{
            controller.listarEquipasColab(colaborador).forEach(System.out::println);
        }
    }


    protected Colaborador solicitarColaborador(){
        //TODO adicionar opção de procurar por nrMecanografico

        final Iterable<Colaborador> colaboradores = this.controller.listarTodosColaboradores();
        final SelectWidget<Colaborador> selectorColaboradores = new SelectWidget<>("Escolha um colaborador da lista:", colaboradores,
                new ColaboradorPrinter());
        selectorColaboradores.show();
        if(selectorColaboradores.selectedOption() == 0){
            System.out.println("Operação cancelada.");
            return null;
        }
        return selectorColaboradores.selectedElement();
    }

     protected Equipa solicitarEquipa(Colaborador colaboradorSelecionado, boolean associacao, String prompt){
        final Iterable<Equipa> equipas = this.controller.todasEquipas();
        final SelectWidget<Equipa> selectorEquipas = new SelectWidget<>(prompt, equipas,
                new EquipaPrinter());
        selectorEquipas.show();

        if(selectorEquipas.selectedOption() == 0){
            System.out.println("Operação cancelada.");
            return null;
        }

        return selectorEquipas.selectedElement();

    }
}



