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

    @Test
    public void testUpdateUserInDataBase() {
        DBUltis dbUltis = new DBUltis();
        User user = dbUltis.logIn("admin", "123");
        user.setEmail("trung0705@gmail.com");
        user.setDateOfBirth(LocalDate.parse("2000-07-05"));
        user.getStreak().setLastAccess(LocalDate.parse("2024-11-15"));
        dbUltis.updateUserInDatabase(user);
        assert user.getDateOfBirth().equals(LocalDate.parse("2000-07-05"));
        assert user.getUsername().equals("admin");
        assert user.getPassword().equals("123");
    }

    @Test
    public void testLoadQuery() {
        DBUltis dbUltis = new DBUltis();
        User user = dbUltis.logIn("admin", "123");
        dbUltis.loadQuery("UPDATE users SET LAST_ACCESS = '" + LocalDate.now()
                + "', streak = " + user.getStreak().getStreak()
                + " WHERE id = " + user.getId());
        assert user.getStreak().getStreak() == 1;

    }




}