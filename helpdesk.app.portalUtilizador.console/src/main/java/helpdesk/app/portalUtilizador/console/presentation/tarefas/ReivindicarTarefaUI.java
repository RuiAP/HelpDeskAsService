package helpdesk.app.portalUtilizador.console.presentation.tarefas;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.execucaofluxo.application.tarefas.ReivindicarTarefaController;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;

import java.util.ArrayList;
import java.util.Iterator;

public class ReivindicarTarefaUI extends AbstractUI{

    ReivindicarTarefaController reivindicarController = new ReivindicarTarefaController();
    @Override
    protected boolean doShow() {

        Iterable<Tarefa> tarefas = reivindicarController.tarefaDisponiveisParaReivindicar();
        Iterator<Tarefa> it = tarefas.iterator();
        TarefaManual tarefaManual;
        Tarefa t;
        ArrayList<TarefaManual> tarefasManuais = new ArrayList<>();
        while(it.hasNext()){
            t = it.next();
            tarefaManual = (TarefaManual) t;
            tarefasManuais.add(tarefaManual);
        }

        final SelectWidget<TarefaManual> selectorTarefa = new SelectWidget<>("Selecione a Tarefa a Reivindicar:\n", tarefasManuais, new TarefaManualPrinter());
        selectorTarefa.show();
        TarefaManual tarefaSelecionada = selectorTarefa.selectedElement();
        if(tarefaSelecionada == null){
            return false;
        }


        try{
            reivindicarController.reivindicarTarefa(tarefaSelecionada);
            System.out.println("Tarefa reivindicada com sucesso");
            return true;
        }catch(Exception e){
            System.out.println("Erro ao reivindicar tarefa: "+e.getMessage());
            return false;
        }

    }

    @Override
    public String headline() {
        return "Reivindicar Tarefa";
    }
}
