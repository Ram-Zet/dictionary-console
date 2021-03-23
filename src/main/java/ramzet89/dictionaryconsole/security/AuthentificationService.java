package ramzet89.dictionaryconsole.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.sender.HttpSender;
import ramzet89.dictionaryconsole.service.ConsoleHelper;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final ConsoleHelper consoleHelper;
    private final HttpSender httpSender;

    private AuthenticatedUser authenticatedUser = null;

    public AuthenticatedUser getAuthenticatedUser() {
        if (authenticatedUser == null) {
            authentificate();
        }
        return authenticatedUser;
    }

    public boolean userAuth() {
        try {
            authentificate();
        } catch (Exception e) {
            consoleHelper.println("Can't authenticate \n" + e.getMessage());
        }
        return Objects.nonNull(authenticatedUser);
    }


    //TODO
    private void authentificate() {
        Credentials credentials = consoleHelper.inputCredentials();
        httpSender.tokenRequest(credentials)
                .map(tokenResponse -> authenticatedUser = new AuthenticatedUser(tokenResponse.getUsername(), tokenResponse.getToken()))
                .orElseGet(() -> {
                            consoleHelper.println("unauthorized");
                            return null;
                        });
    }
}
