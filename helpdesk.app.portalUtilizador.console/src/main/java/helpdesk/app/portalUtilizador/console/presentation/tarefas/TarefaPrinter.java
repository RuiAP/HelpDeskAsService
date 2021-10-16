package helpdesk.app.portalUtilizador.console.presentation.tarefas;

import eapli.framework.visitor.Visitor;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;


public class TarefaPrinter implements Visitor<Tarefa> {
    @Override
    public void visit(Tarefa visitee) {
        System.out.println(visitee.infoBasica());
    }


}
