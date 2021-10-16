package helpdesk.app.backoffice.console.presentation;

import eapli.framework.actions.ChainedAction;
import eapli.framework.actions.menu.Menu;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import helpdesk.app.common.console.presentation.authz.LoginUI;
import helpdesk.usermanager.domain.UserRoles;

public class FrontMenu extends AbstractUI {



    private static final int OPCAO_LOGIN = 1;
    private static final int OPCAO_REGISTAR = 2;
    private static final int OPCAO_SAIR = 0;


    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final var menu = new Menu();

        menu.addItem(OPCAO_LOGIN, "Login",
                new ChainedAction(new LoginUI()::show, () -> {
                    new MainMenu().mainLoop();
                    return true;
                }));

        //menu.addItem(SIGNUP_OPTION, "Sign up", new SignupRequestAction());
        menu.addItem(OPCAO_SAIR, "Sair", new ExitWithMessageAction("Até à próxima!"));

        final var renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    @Override
    public String headline() {
        return "Serviços e Recursos Humanos";
    }
}
