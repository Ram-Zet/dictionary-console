package ramzet89.dictionaryconsole.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.pojo.AuthenticatedUser;
import ramzet89.dictionaryconsole.pojo.Credentials;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final ConsoleHelper consoleHelper;

    private AuthenticatedUser authenticatedUser = null;

    public AuthenticatedUser getAuthenticatedUser() {
        if (authenticatedUser == null) {
            authentificate();
        }
        return authenticatedUser;
    }

    public boolean userAuth(){
        authentificate();
        return Objects.nonNull(authenticatedUser);
    }


    //TODO
    private void authentificate() {
        Credentials credentials = consoleHelper.inputCredentials();
        authenticatedUser = new AuthenticatedUser(credentials.getUsername(), "blablatoken");
    }

}
