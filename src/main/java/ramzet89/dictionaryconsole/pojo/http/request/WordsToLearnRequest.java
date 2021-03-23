package ramzet89.dictionaryconsole.pojo.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(fluent = true, chain = true)
@Data
public class WordsToLearnRequest {

    private Integer newWordsCount = 8;
    private Integer repeatWordsRandomCount = 4;
    private Integer repeatWordsElderCoun = 4;
    private Integer repeatWordsDifficultCount = 4;

    @AllArgsConstructor
    @Getter
    private enum WordsToLearnVariables {
        NEW_WORDS_COUNT("newWordsCount"),
        REPEAT_WORDS_RANDOM_COUNT("repeatWordsRandomCount"),
        REPEAT_WORDS_ELDER_COUNT("repeatWordsElderCount"),
        REPEAT_WORDS_DIFFICULT_COUNT("repeatWordsDifficultCount");

        private final String value;
    }

    public Map<String, String> getPathVariables() {
        return Map.of(
                WordsToLearnVariables.NEW_WORDS_COUNT.value, "8",
                WordsToLearnVariables.REPEAT_WORDS_RANDOM_COUNT.value, "4",
                WordsToLearnVariables.REPEAT_WORDS_ELDER_COUNT.value, "4",
                WordsToLearnVariables.REPEAT_WORDS_DIFFICULT_COUNT.value(), "4");
    }
}
