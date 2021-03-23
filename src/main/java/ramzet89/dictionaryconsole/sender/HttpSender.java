package ramzet89.dictionaryconsole.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ramzet89.dictionaryconsole.config.SenderConfig;
import ramzet89.dictionaryconsole.pojo.http.common.DictionaryRecord;
import ramzet89.dictionaryconsole.pojo.http.request.SaveLearnedRequest;
import ramzet89.dictionaryconsole.pojo.http.request.WordsToLearnRequest;
import ramzet89.dictionaryconsole.pojo.http.response.SaveLearnedResponse;
import ramzet89.dictionaryconsole.pojo.http.response.TokenResponse;
import ramzet89.dictionaryconsole.pojo.http.response.WordsToLearnResponse;
import ramzet89.dictionaryconsole.security.AuthenticatedUser;
import ramzet89.dictionaryconsole.security.Credentials;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSender {
    private final SenderConfig senderConfig;
    private RestTemplate restTemplate;

    @PostConstruct
    public void initTemplate() {
        restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    public Optional<TokenResponse> tokenRequest(Credentials credentials) {
        HttpEntity<Credentials> request = new HttpEntity<>(credentials);
        ResponseEntity<TokenResponse> tokenEntity = restTemplate
                .postForEntity(senderConfig.getAuthRequestUrl(), request, TokenResponse.class);
        switch (tokenEntity.getStatusCode()) {
            case UNAUTHORIZED:
                throw new RuntimeException("Wrong credentials");
            case FORBIDDEN:
                throw new RuntimeException("Resource forbidden");
            case NOT_FOUND:
                throw new RuntimeException("Resource not found");
            default:
                return Optional.ofNullable(tokenEntity.getBody());
        }
    }

    public Optional<WordsToLearnResponse> wordsToLearnRequest(WordsToLearnRequest wordsToLearnRequest, AuthenticatedUser authenticatedUser) {
        var httpHeaders = prepareTokenHeaders(authenticatedUser.getToken());
        var wordsToLearn = restTemplate.exchange(senderConfig.getWordsToLearnUrl(), HttpMethod.GET,
                new HttpEntity<>(httpHeaders), WordsToLearnResponse.class, wordsToLearnRequest.getPathVariables());
        if (wordsToLearn.getStatusCode() != HttpStatus.OK) {
            return Optional.empty();
        }
        return Optional.of(wordsToLearn.getBody());
    }

    public Optional<SaveLearnedResponse> saveLearnedRequest(List<DictionaryRecord> learnedWords, AuthenticatedUser authenticatedUser) {
        var httpHeaders = prepareTokenHeaders(authenticatedUser.getToken());
        var saveLearnedRequest = new SaveLearnedRequest().setLearnedWords(learnedWords);
        var saveLearnedRequestHttpEntity = new HttpEntity<>(saveLearnedRequest, new HttpHeaders(httpHeaders));
        var responseEntity = restTemplate.exchange(
                senderConfig.getSaveLearnedUrl(), HttpMethod.POST, saveLearnedRequestHttpEntity, SaveLearnedResponse.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return Optional.empty();
        }
        return Optional.of(responseEntity.getBody());
    }

    private HttpHeaders prepareTokenHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", token);
        return httpHeaders;
    }
}
