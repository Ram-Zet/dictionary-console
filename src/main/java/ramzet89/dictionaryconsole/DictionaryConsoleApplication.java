package ramzet89.dictionaryconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ramzet89.dictionaryconsole.service.menu.impl.MainMenu;

@SpringBootApplication
public class DictionaryConsoleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DictionaryConsoleApplication.class, args);
        context.getBean(MainMenu.class).showMenu();
    }

}
