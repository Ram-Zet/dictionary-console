package ramzet89.dictionaryconsole.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ramzet89.dictionaryconsole.mapper.Mapper;
import ramzet89.dictionaryconsole.security.AuthentificationService;
import ramzet89.dictionaryconsole.sender.HttpSender;

import java.lang.reflect.Method;
import java.util.Set;

public class LearnServiceTest {

    @Test
    public void calculateCorrectAnswersTest() throws Exception {
        LearnService learnService = new LearnService(Mockito.mock(HttpSender.class), Mockito.mock(AuthentificationService.class),
                Mockito.mock(Mapper.class), Mockito.mock(ConsoleHelper.class));
        Method calculateCorrectAnswers = LearnService.class.getDeclaredMethod("calculateCorrectAnswers", Set.class, Set.class);
        calculateCorrectAnswers.setAccessible(true);
        System.out.println(calculateCorrectAnswers.invoke(learnService, Set.of("условие", "оговорка"), Set.of("оговорка", "условие")));

    }
}
