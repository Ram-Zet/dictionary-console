package ramzet89.dictionaryconsole.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuItem {
    LEARN("Learn words", "Учить слова"),
    EXIT("Exit", "Выход");

    private final String en;
    private final String ru;

    @Override
    public String toString() {
        return Locale.getCurrentLocale() == Locale.EN ? en : ru;
    }
}
