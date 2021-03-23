package ramzet89.dictionaryconsole.service.menu.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.enums.MenuItem;
import ramzet89.dictionaryconsole.security.AuthentificationService;
import ramzet89.dictionaryconsole.service.ConsoleHelper;
import ramzet89.dictionaryconsole.service.LearnService;
import ramzet89.dictionaryconsole.service.menu.Menu;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ramzet89.dictionaryconsole.enums.Console.GOOD_BYE;

@Service("mainMenu")
@RequiredArgsConstructor
public class MainMenu implements Menu {

    private final ConsoleHelper consoleHelper;
    private final AuthentificationService authentificationService;
    private final LearnService learnService;

    private Map<MenuItem, Runnable> menuItemsMap;
    private List<MenuItem> menuItems;

    @PostConstruct
    private void initMenuItems() {
        menuItemsMap = Map.of(
                MenuItem.LEARN, learnService::startLearn,
                MenuItem.EXIT, this::exit);


        menuItems = List.of(MenuItem.LEARN, MenuItem.EXIT);
    }

    @Override
    public void showMenu() {

        if (!authentificationService.userAuth()) {
            exit();
        }

        while (true) {
            MenuItem seletedItem = consoleHelper.userSelectMenuItem(menuItems);
            menuItemsMap.getOrDefault(seletedItem, () -> {
                throw new RuntimeException("WrongMenuItem");
            }).run();
        }
    }

    private void exit() {
        consoleHelper.println(GOOD_BYE);
        System.exit(0);
    }
}
