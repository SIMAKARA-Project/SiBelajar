package org.simakara.learning_management_system.tools;

import java.time.LocalDate;

public class DateFormater {

    public static String format(LocalDate date) {

        int day = date.getDayOfMonth();
        String month = date.getMonth().name();
        int year = date.getYear();

        return day + " " + month + " " + year;
    }
}
