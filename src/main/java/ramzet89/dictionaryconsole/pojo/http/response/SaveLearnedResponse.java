package ramzet89.dictionaryconsole.pojo.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SaveLearnedResponse {
    private Set<Long> savedWordsIds;
}
