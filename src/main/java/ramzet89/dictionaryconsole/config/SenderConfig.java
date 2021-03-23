package ramzet89.dictionaryconsole.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sender")
@Setter
public class SenderConfig {
    private static final String AUTH_REQUEST_TEMPLATE = "http://%s:%s/api/v1/auth/login";
    private static final String WORDS_TO_LEARN_TEMPLATE = "http://%s:%s/api/v1/learn/words";
    private static final String SAVE_LEARNED_TEMPLATE = "http://%s:%s/api/v1/learn/savelearned";

    private String url = "localhost";
    private String port = "8080";

    public String getAuthRequestUrl() {
        return String.format(AUTH_REQUEST_TEMPLATE, url, port);
    }

    public String getWordsToLearnUrl() {
        return String.format(WORDS_TO_LEARN_TEMPLATE, url, port);
    }

    public String getSaveLearnedUrl() {
        return String.format(SAVE_LEARNED_TEMPLATE, url, port);
    }

    //TODO - вынести урлы в конфиги
}
