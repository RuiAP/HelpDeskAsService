/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package helpdesk.infrastructure.bootstrapers;

import java.util.HashSet;
import java.util.Set;

import helpdesk.usermanager.domain.UserRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class UsersBootstrapper extends UsersBootstrapperBase implements Action {

    @SuppressWarnings("squid:S2068")
    private static final String PASSWORD1 = "Password1";

    @Override
    public boolean execute() {
        registerRRH("rrh", PASSWORD1, "Joaquim", "Dias", "quim@gmail.com");
        registerUtilizador("utilizador", PASSWORD1, "Maria", "Genoveva", "maria@gmail.com");
        registerGSH("gsh", PASSWORD1, "Jose", "Oliveira", "ze@gmail.com");
        return true;
    }

    private void registerRRH(final String username, final String password,
                                    final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(UserRoles.USER_RRH);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerUtilizador(final String username, final String password,
                                    final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(UserRoles.USER_UTILIZADOR);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerGSH(final String username, final String password,
                             final String firstName, final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(UserRoles.USER_GSH);

        registerUser(username, password, firstName, lastName, email, roles);
    }

}
