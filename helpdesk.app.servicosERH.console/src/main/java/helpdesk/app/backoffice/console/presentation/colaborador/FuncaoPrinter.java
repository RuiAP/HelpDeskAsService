/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.app.backoffice.console.presentation.colaborador;

import eapli.framework.visitor.Visitor;
import helpdesk.estruturaorganica.domain.funcao.Funcao;


public class FuncaoPrinter implements Visitor<Funcao> {
    @Override
    public void visit(Funcao visitee) {
        System.out.println(visitee);
    }
    
}
