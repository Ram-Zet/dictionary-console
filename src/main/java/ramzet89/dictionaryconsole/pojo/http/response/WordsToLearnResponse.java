package ramzet89.dictionaryconsole.pojo.http.response;

import lombok.Getter;
import lombok.Setter;
import ramzet89.dictionaryconsole.pojo.http.common.DictionaryRecord;

import java.util.List;

@Getter
@Setter
public class WordsToLearnResponse {
    private List<DictionaryRecord> words;
}
