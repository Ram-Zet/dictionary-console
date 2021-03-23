package ramzet89.dictionaryconsole.pojo;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DictionaryItem {
    private Long id;
    private String english;
    private Set<String> translations;
    private String transcription;
    private int attempts = 0;
    private int failures = 0;
    private int attemptsNoGaps = 0;
}
