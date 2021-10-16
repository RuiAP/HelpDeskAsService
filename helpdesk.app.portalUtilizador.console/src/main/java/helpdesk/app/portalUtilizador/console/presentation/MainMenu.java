/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package helpdesk.app.portalUtilizador.console.presentation;

import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import helpdesk.app.portalUtilizador.console.presentation.servicos.ConsultarCatalogosUI;
import helpdesk.app.portalUtilizador.console.presentation.servicos.ListarTodosServicosUI;
import helpdesk.app.portalUtilizador.console.presentation.servicos.PesquisarServico;
import helpdesk.app.portalUtilizador.console.presentation.tarefas.DashboardTarefasUI;
import helpdesk.app.portalUtilizador.console.presentation.tarefas.RealizarTarefaManualUI;
import helpdesk.app.portalUtilizador.console.presentation.tarefas.ReivindicarTarefaUI;
import helpdesk.app.portalUtilizador.httpServer.StartupServer;

/**
 * @author Paulo Gandra Sousa
 */

class MainMenu extends ClientUserBaseUI {

    private static boolean serverStarted = false;


    public MainMenu() {
        if(!serverStarted){
            new StartupServer();
            serverStarted = true;
        }
    }

    // MAIN MENU
    private static final int OPCAO_SERVICOS = 1;
    private static final int OPCAO_TAREFAS = 2;
    private static final int OPCAO_SAIR = 0;


    // SERVICOS MENU
    private static final int OPCAO_VISUALIZAR_CATALOGOS = 1;
    private static final int OPCAO_TODOS_SERVICOS = 2;
    private static final int OPCAO_PESQUISAR_SERVICOS = 3;


    // MENU DASHBOARD TAREFAS
    private static final int OPCAO_CONSULTAR_TAREFAS = 1;
    private static final int OPCAO_REALIZAR_TAREFA = 2;
    private static final int OPCAO_REIVINDICAR_TAREFA = 3;

    private static final String SEPARADOR = "--------------";




    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final var renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    @Override
    public String headline() {
        return "Aplicação ServiceDesk - Portal do Utilizador";
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu bookingsMenu = buildMenuServicos();
        mainMenu.addSubMenu(OPCAO_SERVICOS, bookingsMenu);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu accountMenu = buildMenuDashboard();
        mainMenu.addSubMenu(OPCAO_TAREFAS, accountMenu);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        mainMenu.addItem(OPCAO_SAIR, "SAIR", new CloseConnectionAndExitAction("Até à próxima!"));

        return mainMenu;
    }

    private Menu buildMenuServicos() {
        final Menu menuServicos = new Menu("Solicitar Serviço");

        menuServicos.addItem(OPCAO_VISUALIZAR_CATALOGOS, "Visualizar por Catálogo",  () -> new ConsultarCatalogosUI().show());
        menuServicos.addItem(OPCAO_TODOS_SERVICOS, "Visualizar Todos os Serviços Disponíveis", () -> new ListarTodosServicosUI().show());
        menuServicos.addItem(OPCAO_PESQUISAR_SERVICOS, "Pesquisar Serviços", () -> new PesquisarServico().show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }

    private Menu buildMenuDashboard() {
        final Menu menuTarefas = new Menu("Opções de Tarefas");

        menuTarefas.addItem(OPCAO_CONSULTAR_TAREFAS, "Consultar Dashboard", () -> new DashboardTarefasUI().show());
        menuTarefas.addItem(OPCAO_REALIZAR_TAREFA, "Realizar uma Tarefa", () -> new RealizarTarefaManualUI().show());
        menuTarefas.addItem(OPCAO_REIVINDICAR_TAREFA, "Reinvidicar uma Tarefa", () -> new ReivindicarTarefaUI().show());
        menuTarefas.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuTarefas;
    }





}
