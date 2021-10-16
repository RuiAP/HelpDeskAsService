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
package helpdesk.app.backoffice.console.presentation;


import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import helpdesk.app.backoffice.console.presentation.catalogo.CriarCatalogoUI;
import helpdesk.app.backoffice.console.presentation.colaborador.CriarFuncaoUI;
import helpdesk.app.backoffice.console.presentation.colaborador.EspecificarColaboradorUI;
import helpdesk.app.backoffice.console.presentation.equipa.*;
import helpdesk.app.backoffice.console.presentation.nivelCriticidade.DefinirNivelCriticidadeUI;
import helpdesk.app.backoffice.console.presentation.pedidos.ConsultarPedidosUI;
import helpdesk.app.backoffice.console.presentation.servico.EspecificarServicoUI;


public class MainMenu extends AbstractUI {

    // MAIN MENU
    private static final int OPCAO_COLABORADORES = 1;
    private static final int OPCAO_EQUIPAS = 2;
    private static final int OPCAO_SERVICOS = 3;
    private static final int OPCAO_CATALOGOS = 4;
    private static final int OPCAO_PEDIDOS = 5;
    private static final int OPCAO_NIVEL_CRITICIDADE = 6;
    private static final int OPCAO_FAZER_LOGOUT = 7;
    private static final int OPCAO_SAIR = 0;

    //temporario - comunicar com o MotorFluxo
    private static final int OPCAO_COMUNICAR_MOTOR_FLUXO=7;


    // MENU COLABORADORES
    private static final int OPCAO_ESPECIFICAR_COLABORADOR = 1;
    private static final int OPCAO_CRIAR_FUNCAO = 2;

    // MENU EQUIPAS
    private static final int OPCAO_CRIAR_EQUIPA = 1;
    private static final int OPCAO_REGISTAR_TIPO_EQUIPA = 2;
    private static final int OPCAO_ASSOCIAR_COLABORADOR_A_EQUIPA = 3;
    private static final int OPCAO_REMOVER_COLABORADOR_DA_EQUIPA = 4;

    // MENU SERVICOS
    private static final int OPCAO_ESPECIFICAR_NOVO_SERVICO = 1;
    private static final int OPCAO_CONTINUAR_ESPECIFICACAO_SERVICO = 2;

    // MENU CATALOGOS
    private static final int OPCAO_CRIAR_CATALOGO = 1;

    //MENU Pedidos
    private static final int OPCAO_CONSULTAR_TODOS_PEDIDOS = 1;
    private static final int OPCAO_CONSULTAR_PEDIDOS_EM_CURSO = 2;
    private static final int OPCAO_CONSULTAR_PEDIDOS_CONCLUIDOS = 3;


    //MENU NIVEL CRITICIDADE
    private static final int OPCAO_DEFINIR_NIVEL_CRITICIDADE= 1;






    private static final String SEPARADOR = "--------------";




    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        try{
            final Menu menu = buildMainMenu();
            final var renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
            return renderer.render();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public String headline() {
        return "Aplicação ServiceDesk - Serviços e RH";
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();


        final Menu menuColaboradores = buildMenuColaboradores();
        mainMenu.addSubMenu(OPCAO_COLABORADORES, menuColaboradores);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu menuEquipas = buildMenuEquipas();
        mainMenu.addSubMenu(OPCAO_EQUIPAS, menuEquipas);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu menuServicos = buildMenuServicos();
        mainMenu.addSubMenu(OPCAO_SERVICOS, menuServicos);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu menuCatalogos = buildMenuCatalogos();
        mainMenu.addSubMenu(OPCAO_CATALOGOS, menuCatalogos);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu menuPedidos = buildMenuPedidos();
        mainMenu.addSubMenu(OPCAO_PEDIDOS, menuPedidos);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));

        final Menu menuNivelCriticidade = buildMenuNivelCriticidade();
        mainMenu.addSubMenu(OPCAO_NIVEL_CRITICIDADE, menuNivelCriticidade);
        mainMenu.addItem(MenuItem.separator(SEPARADOR));


        mainMenu.addItem(OPCAO_FAZER_LOGOUT, "Logout", new LogoutActionHelpDesk("Logout executado."));


        mainMenu.addItem(OPCAO_SAIR, "SAIR", new ExitWithMessageAction("Até à próxima|"));

        return mainMenu;
    }



    private Menu buildMenuColaboradores() {
        final Menu menuServicos = new Menu("Opções de Colaboradores");

        menuServicos.addItem(OPCAO_ESPECIFICAR_COLABORADOR, "Especificar Novo Colaborador", () -> new EspecificarColaboradorUI().show());
        menuServicos.addItem(OPCAO_CRIAR_FUNCAO, "Criar Nova Funcao", () -> new CriarFuncaoUI().show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }
    private Menu buildMenuEquipas() {
        final Menu menuServicos = new Menu("Opções de Equipas");

        menuServicos.addItem(OPCAO_CRIAR_EQUIPA, "Criar Nova Equipa", () -> new CriarEquipaUI().show());
        menuServicos.addItem(OPCAO_REGISTAR_TIPO_EQUIPA, "Registar Novo Tipo de Equipa", () -> new RegistarTipoEquipaUI().show());
        menuServicos.addItem(OPCAO_ASSOCIAR_COLABORADOR_A_EQUIPA, "Associar Colaborador a uma Equipa",()-> new AssociarColaboradorEquipaUI().show());
        menuServicos.addItem(OPCAO_REMOVER_COLABORADOR_DA_EQUIPA, "Remover o Colaborador de uma Equipa", ()-> new RemoverColaboradorEquipaUI().show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }
    private Menu buildMenuServicos() {
        final Menu menuServicos = new Menu("Opções de Serviços");

        menuServicos.addItem(OPCAO_ESPECIFICAR_NOVO_SERVICO, "Especificar Novo Serviço", () -> new EspecificarServicoUI(false).show());
        menuServicos.addItem(OPCAO_CONTINUAR_ESPECIFICACAO_SERVICO, "Continuar especificação de um Serviço", () -> new EspecificarServicoUI(true).show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }
    private Menu buildMenuCatalogos() {
        final Menu menuServicos = new Menu("Opções de Catálogos");

        menuServicos.addItem(OPCAO_CRIAR_CATALOGO, "Criar Novo Catálogo", () -> new CriarCatalogoUI().show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }
    private Menu buildMenuNivelCriticidade() {
        final Menu menuServicos = new Menu("Opções de Nível de Criticidade");

        menuServicos.addItem(OPCAO_DEFINIR_NIVEL_CRITICIDADE, "Definir Niveis de Criticidade", () -> new DefinirNivelCriticidadeUI().show());
        menuServicos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuServicos;
    }

    private Menu buildMenuPedidos() {
        final Menu menuPedidos = new Menu("Opções de Pedidos");

        menuPedidos.addItem(OPCAO_CONSULTAR_TODOS_PEDIDOS, "Consultar Histórico de Pedidos", () -> new ConsultarPedidosUI(1).show());
        menuPedidos.addItem(OPCAO_CONSULTAR_PEDIDOS_EM_CURSO, "Consultar Pedidos em Curso", () -> new ConsultarPedidosUI(2).show());
        menuPedidos.addItem(OPCAO_CONSULTAR_PEDIDOS_CONCLUIDOS, "Consultar Pedidos Concluídos", () -> new ConsultarPedidosUI(3).show());
        menuPedidos.addItem(OPCAO_SAIR, "Voltar", Actions.SUCCESS);
        return menuPedidos;
    }


}
