/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.app.backoffice.console.presentation.colaborador;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import helpdesk.estruturaorganica.application.funcao.CriarFuncaoController;

/**
 *
 * @author Asus
 */
public class CriarFuncaoUI extends AbstractUI {
    
    public CriarFuncaoController controller;
    
    public CriarFuncaoUI() {
        this.controller = new CriarFuncaoController();
    }
    
    @Override
    protected boolean doShow() {
        System.out.println("Introduza os dados para criar catálogo:");
        final String designacao = Console.readLine("Designacao");
        
        System.out.println("Funcao criada");
        
        return controller.CriarFuncao(designacao) != null;
    }

    @Override
    public String headline() {
        return "Especificar nova funcao";
    }

}
