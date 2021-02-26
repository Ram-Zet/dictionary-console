package ramzet89.dictionaryconsole.enums;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Console {
    INTEGER_INPUT("Input number: ", "Введите число: "),
    INTEGER_INPUT_ERROR("This is not number. Try again, please",
            "Введенная строк не является числом, попробуйте еще"),
    GOOD_BYE("Good bye, sir!", "Доброго!"),
    MENU_ITEM_INPUT("Input menu number", "Введите номер меню"),
    WRONG_MENU_NUMBER("Wrong menu number", "Неверныйы номер меню"),
    USERNAME_INPUT("Input username", "Введите имя пользователя"),
    PASSWORD_INPUT("Input password", "Введите пароль");

    private final String en;
    private final String ru;

    @Override
    public String toString() {
        return Locale.getCurrentLocale() == Locale.EN ? en : ru;
    }
}
