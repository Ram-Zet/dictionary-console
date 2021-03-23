package ramzet89.dictionaryconsole.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.mapper.Mapper;
import ramzet89.dictionaryconsole.pojo.DictionaryItem;
import ramzet89.dictionaryconsole.pojo.http.request.WordsToLearnRequest;
import ramzet89.dictionaryconsole.pojo.http.response.SaveLearnedResponse;
import ramzet89.dictionaryconsole.pojo.http.response.WordsToLearnResponse;
import ramzet89.dictionaryconsole.security.AuthentificationService;
import ramzet89.dictionaryconsole.sender.HttpSender;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnService {
    private static final Random RANDOM = new Random();
    private final HttpSender sender;
    private final AuthentificationService authentificationService;
    private final Mapper mapper;
    private final ConsoleHelper consoleHelper;

    @Value("#{new Integer('${learn.maxCorrectAttempts}')}")
    private int maxCorrectAttempts = 2;

    public void startLearn() {
        var learningResult = learn(getWordsToLearn());
        consoleHelper.println(sendResult(learningResult).toString());
    }

    private Set<Long> sendResult(List<DictionaryItem> learningResult) {
        Optional<SaveLearnedResponse> saveLearnedResponse = sender.saveLearnedRequest(mapper.toDictionaryRecords(learningResult), authentificationService.getAuthenticatedUser());
        if (saveLearnedResponse.isPresent()) {
            return saveLearnedResponse.get().getSavedWordsIds();
        }
        return Collections.emptySet();
    }

    private List<DictionaryItem> getWordsToLearn() {
        var dictionaryResponse = sender.wordsToLearnRequest(new WordsToLearnRequest(),
                authentificationService.getAuthenticatedUser());

        return dictionaryResponse
                .map(WordsToLearnResponse::getWords)
                .map(mapper::toDictionaryItems)
                .orElse(Collections.emptyList());
    }


    private List<DictionaryItem> learn(List<DictionaryItem> dictionaryItems) {
        List<DictionaryItem> learned = new ArrayList<>();

        while (!dictionaryItems.isEmpty()) {
            var randomItem = getRandomItem(dictionaryItems);
            int attemptsNoGaps = learnItem(randomItem);
            if (attemptsNoGaps == maxCorrectAttempts) {
                dictionaryItems.remove(randomItem);
                learned.add(randomItem);
            }
        }
        return learned;
    }

    private int learnItem(DictionaryItem dictionaryItem) {
        var answer = consoleHelper.inputTranslations(dictionaryItem.getEnglish(), dictionaryItem.getTranslations().size());
        var correctAnswers = calculateCorrectAnswers(dictionaryItem.getTranslations(), answer);

        dictionaryItem.setAttempts(dictionaryItem.getAttempts() + 1);

        if (correctAnswers.isEmpty()) {
            dictionaryItem.setFailures(dictionaryItem.getFailures() + 1);
            dictionaryItem.setAttemptsNoGaps(0);
            consoleHelper.incorrectAnswer(dictionaryItem.getTranslations());
        } else if (correctAnswers.size() < dictionaryItem.getTranslations().size()) {
            dictionaryItem.setAttemptsNoGaps(0);
            consoleHelper.almostCorrectAnswer(dictionaryItem.getTranslations());
        } else {
            dictionaryItem.setAttemptsNoGaps(dictionaryItem.getAttemptsNoGaps() + 1);
            consoleHelper.correctAnswer(dictionaryItem.getTranslations());
        }

        return dictionaryItem.getAttemptsNoGaps();
    }

    private DictionaryItem getRandomItem(List<DictionaryItem> dictionaryItems) {
        int i = RANDOM.nextInt(dictionaryItems.size());
        return dictionaryItems.get(i);
    }

    private Set<String> calculateCorrectAnswers(Set<String> translations, Set<String> answers) {
        Set<String> answersUpper = answers.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        return translations.stream()
                .map(String::toUpperCase)
                .filter(answersUpper::contains)
                .collect(Collectors.toSet());
    }
}
