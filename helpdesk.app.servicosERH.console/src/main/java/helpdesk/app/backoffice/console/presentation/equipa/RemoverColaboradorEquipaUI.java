package helpdesk.app.backoffice.console.presentation.equipa;

import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public class RemoverColaboradorEquipaUI extends GestaoColaboradorComEquipaUI{

    private boolean associacao = false;

    @Override
    protected boolean doShow() {


        Colaborador colaboradorSelecionado = solicitarColaborador();
        if(colaboradorSelecionado == null){
            return false;
        }


        Equipa equipaSelecionada = solicitarEquipa(colaboradorSelecionado, associacao,"\nSelecione a equipa da qual deseja remover o Colaborador:");
        if(equipaSelecionada == null){
            return false;
        }

        Colaborador colabAtualizado  = controller.removerColaboradorDeEquipa(colaboradorSelecionado, equipaSelecionada);

        if(colabAtualizado != null){
            System.out.println("Operação realizada com sucesso.");
            System.out.println("Equipas a que pertence o colaborador "+ colabAtualizado + ":");
            mostrarEquipasRestantes(colaboradorSelecionado);
            return true;
        }else{
            System.out.println("Ocorreu um erro ao remover colaborador da equipa. Verifique os dados e tente novamente.");
        }

        return false;
    }



    @Override
    public String headline() {
        return "Remover colaborador de uma equipa";
    }


}
