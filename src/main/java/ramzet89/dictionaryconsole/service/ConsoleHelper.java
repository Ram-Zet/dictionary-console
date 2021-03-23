package ramzet89.dictionaryconsole.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.enums.MenuItem;
import ramzet89.dictionaryconsole.security.Credentials;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;

import static ramzet89.dictionaryconsole.enums.Console.*;

@Service
@Slf4j
public class ConsoleHelper {
    private static final String EMPTY = "";
    private static final String MENU_PATTERN = "%d. %s";

    private BufferedReader reader;
    private PrintStream writer = System.out;

    @PostConstruct
    private void init() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @PreDestroy
    private void preDestroy() {
        try {
            reader.close();
        } catch (IOException e) {
            log.error("Error in closing reader, {}", e.getStackTrace());
        }
    }

    public void println(Object o) {
        print(o);
        print("\n");
    }


    public String readln() {
        return readline();
    }

    public MenuItem userSelectMenuItem(List<MenuItem> menuItemList) {
        while (true) {
            for (int i = 0; i < menuItemList.size(); i++) {
                println(String.format(MENU_PATTERN, i + 1, menuItemList.get(i)));
            }
            println(MENU_ITEM_INPUT);
            Integer menuNumber = readInt();
            if (menuNumber != null && menuNumber > 0 && menuNumber <= menuItemList.size()) {
                return menuItemList.get(menuNumber - 1);
            }
            println(WRONG_MENU_NUMBER);
        }

    }

    private String readline() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            log.error("Error reading line, {}", e.getStackTrace());
            return EMPTY;
        }
    }

    @Nullable
    private Integer readInt() {
        while (true) {
            try {
                String input = reader.readLine();
                if (Strings.isEmpty(input)) {
                    return null;
                }
                return Integer.parseInt(input);
            } catch (IOException e) {
                log.error("Error reading line, {}", e.getStackTrace());
                return null;
            } catch (NumberFormatException e) {
                println(INTEGER_INPUT_ERROR);
            }
        }
    }

    private void print(Object o) {
        writer.print(o);
    }

    public Credentials inputCredentials() {
        String username;
        while (true) {
            println(USERNAME_INPUT);
            String s = readline();
            if (s.isEmpty()) continue;
            username = s;
            break;
        }
        String password;
        while (true) {
            println(PASSWORD_INPUT);
            String s = readline();
            if (s.isEmpty()) continue;
            password = s;
            break;
        }
        return new Credentials(username, password);
    }

    public Set<String> inputTranslations(String english, int translationOptionSize) {
        println("___________________________________________________");
        println("");
        print(english);
        println(String.format(" : %d options", translationOptionSize));
        Set<String> result = new HashSet<>();
        for (int i = 0; i < translationOptionSize; i++){
            String userInput = readline();
            if (userInput.isBlank()) {
                break;
            }
            result.add(userInput);
        }
        return result;
    }

    public void correctAnswer(Set<String> translations) {
        println(CORRECT);
        printTranslations(translations);
    }

    public void almostCorrectAnswer(Set<String> translations) {
        println(ALMOST_CORRECT);
        printTranslations(translations);
    }

    public void incorrectAnswer(Set<String> translations) {
        println(INCORRECT);
        printTranslations(translations);
    }

    private void printTranslations(Set<String> translations) {
        println(String.format("%s: %s", CORRECT_TRANSLATIONS, Strings.join(translations, ',')));
    }



}
