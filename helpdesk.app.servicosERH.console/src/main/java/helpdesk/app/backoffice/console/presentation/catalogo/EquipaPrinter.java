package helpdesk.app.backoffice.console.presentation.catalogo;

import eapli.framework.visitor.Visitor;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public class EquipaPrinter implements Visitor<Equipa> {
    @Override
    public void visit(Equipa visitee) {
        System.out.println(visitee);
    }
}
