package helpdesk.app.backoffice.console.presentation.equipa;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public class AssociarColaboradorEquipaUI extends GestaoColaboradorComEquipaUI{


    private boolean associacao = true;

    @Override
    protected boolean doShow() {

        Colaborador colaboradorSelecionado = solicitarColaborador();
        if(colaboradorSelecionado == null){
            return false;
        }

        Equipa equipaSelecionada = solicitarEquipa(colaboradorSelecionado, associacao,"\nSeleciona a qual equipa deseja adicionar o Colaborador:");
        if(equipaSelecionada == null){
            return false;
        }

        Colaborador colabAtualizado  = controller.adicionarColaboradorAEquipa(colaboradorSelecionado,equipaSelecionada);

        if(colabAtualizado != null){
            System.out.println("Operação realizada com sucesso.");
            System.out.println("Equipas a que pertence o colaborador "+ colabAtualizado + ":");
            mostrarEquipasRestantes(colaboradorSelecionado);
            return true;
        }else{
            System.out.println("Ocorreu um erro ao associar colaborador à equipa. Verifique os dados e tente novamente.");
        }

        return true;
    }


    @Override
    public String headline() {
        return "Associar colaborador a uma equipa";
    }

}
