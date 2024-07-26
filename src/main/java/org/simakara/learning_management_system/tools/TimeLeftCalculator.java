package org.simakara.learning_management_system.tools;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class TimeLeftCalculator {

    public static String timeLeft(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        if (time.isBefore(now)) {
            return "Expired";
        }

        long total = MINUTES.between(now, time);
        long minutes = total % 60;
        long hours = (total / 60) % 24;
        long days = total / (60 * 24);

        StringBuilder timeLeft = new StringBuilder();
        if (days > 0) {
            timeLeft.append(days).append(days == 1 ? " day " : " days ");
        }
        if (hours > 0) {
            timeLeft.append(hours).append(hours == 1 ? " hour " : " hours ");
        }
        if (minutes > 0) {
            timeLeft.append(minutes).append(minutes == 1 ? " minute" : " minutes");
        }

        return timeLeft.toString().trim();
    }
}
