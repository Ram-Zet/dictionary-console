package ramzet89.dictionaryconsole.pojo.http.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ramzet89.dictionaryconsole.pojo.http.common.DictionaryRecord;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SaveLearnedRequest {
    private List<DictionaryRecord> learnedWords;
}
