package ramzet89.dictionaryconsole.pojo.http.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryRecord {
    private Long id;
    private String english;
    private String russian;
    private String russian2;
    private String russian3;
    private String transcription;
    private Integer attempts;
    private Integer failures;
}
