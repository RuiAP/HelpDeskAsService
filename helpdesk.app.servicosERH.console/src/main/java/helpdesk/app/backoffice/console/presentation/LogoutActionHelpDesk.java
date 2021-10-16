package helpdesk.app.backoffice.console.presentation;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class LogoutActionHelpDesk extends helpdesk.app.common.console.presentation.authz.LogoutAction {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private String message;

    public LogoutActionHelpDesk(String message){
        this.message = message;
    }

    @Override
    public boolean execute() {
       authz.clearSession();
       System.out.println(message+"\n");
       new FrontMenu().show();
       return true;
    }




}
