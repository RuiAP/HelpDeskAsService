package helpdesk.app.backoffice.console.presentation.catalogo;

import eapli.framework.visitor.Visitor;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;

public class ColaboradorPrinter implements Visitor<Colaborador> {
    @Override
    public void visit(Colaborador visitee) {
        System.out.println(visitee.toString());
    }
}
