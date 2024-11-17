import org.example.demo6.Classes.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainTest {

    @Test
    public void testStreak1() {
        Streak streak = new Streak(LocalDate.parse("2024-11-15"), 10, 10);
        streak.updateStreak();
        assert streak.getStreak() == 11;
    }

    @Test
    public void testStreak2() {
        Streak streak = new Streak(LocalDate.parse("2024-11-16"), 19, 19);
        streak.updateStreak();
        assert streak.getStreak() == 20;
        assert streak.getLongestStreak() == 20;
    }



}