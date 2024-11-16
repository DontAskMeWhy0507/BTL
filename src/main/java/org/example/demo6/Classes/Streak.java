package org.example.demo6.Classes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Streak {
    private int streak;
    private LocalDate lastAccess; // The last date the user logged in
    private LocalDate today;      // Today's date

    public Streak(LocalDate lastAccess, int streak) {
        this.streak = streak;
        this.lastAccess = lastAccess;
        this.today = LocalDate.now();
    }

    public void updateStreak() {
        if (lastAccess != null) {
            long daysBetween = ChronoUnit.DAYS.between(lastAccess, today);
            if (daysBetween == 1) {
                streak++;
            } else if (daysBetween > 1) {
                streak = 1;
            }
        }
        lastAccess = today;
    }
    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public LocalDate getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}

