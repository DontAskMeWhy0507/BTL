package org.example.demo6.Classes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Streak {
    private int streak;
    private int longestStreak;
    private LocalDate lastAccess; // The last date the user logged in
    private LocalDate today;      // Today's date
    private final int[] milestones = {7, 10, 30}; // Milestone streaks

    public Streak(LocalDate lastAccess, int streak, int longestStreak) {
        this.streak = streak;
        this.longestStreak = longestStreak;
        this.lastAccess = lastAccess;
        this.today = LocalDate.now();
        if (lastAccess == null) {
            this.streak = 1; // Khởi tạo streak nếu chưa có lần truy cập trước đó
            this.lastAccess = today;
        }
    }

    public void updateStreak() {
        if (lastAccess != null) {
            long daysBetween = ChronoUnit.DAYS.between(lastAccess, today);
            if (daysBetween == 1) {
                streak++;
            } else if (daysBetween > 1) {
                streak = 1; // Reset streak
            }
        } else {
            streak = 1; // First access
        }
        if (streak > longestStreak) {
            longestStreak = streak; // Update longestStreak
        }
        lastAccess = today; // Update last access date

        // Check milestone
        checkMilestone();
    }

    public boolean checkMilestone() {
        for (int milestone : milestones) {
            if (streak == milestone) {
                System.out.println("Congratulations! You have reached a " + milestone + " days streak!");
                return true; // Milestone đã đạt được
            }
        }
        return false; // Không đạt milestone nào
    }

    public int getNextMilestone() {
        for (int milestone : milestones) {
            if (streak < milestone) {
                return milestone;
            }
        }
        return -1; // Nếu không còn milestone nào
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
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
