package helpdesk.app.portalUtilizador.console.presentation.tarefas;

import eapli.framework.visitor.Visitor;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;

public class TarefaManualPrinter implements Visitor<TarefaManual> {
    @Override
    public void visit(TarefaManual visitee) {
        System.out.println(visitee.infoBasica());
    }
}
