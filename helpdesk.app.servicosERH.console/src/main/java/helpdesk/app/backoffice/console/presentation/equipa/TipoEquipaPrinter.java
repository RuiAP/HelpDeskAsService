package helpdesk.app.backoffice.console.presentation.equipa;

import eapli.framework.visitor.Visitor;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;

public class TipoEquipaPrinter implements Visitor<TipoEquipa>{


        @Override
        public void visit(final TipoEquipa visitee) {
            System.out.println(visitee);
        }

    }
