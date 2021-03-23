package ramzet89.dictionaryconsole.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Credentials {
    private final String username;
    private final String password;
}
