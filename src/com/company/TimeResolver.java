package com.company;

public final class TimeResolver {

    public TimeResolver() {
    }

    //method changes String hours to minutes in formats X, XX:YY(3, 7, 11; 09:30, 10:00, 17:24)
    public static int hoursToMinutes(String hour){
        //case time is passed as hour only (1, 12, 19, 23)
        if (hour.matches("\\d") || hour.matches("1\\d") || hour.matches("2[0-4]")) {
            int intHours = Integer.parseInt(hour);
            return intHours * 60;
        }
        //case time is passed as hours and minutes(10:00, 09:11, 00:00, 19:23)
        else if (hour.matches("\\d{2}:\\d{2}")) {
            //hours and minutes are cut from passed String and parsed to int
            int hours = Integer.parseInt(hour.substring(0,2));
            int minutes = Integer.parseInt(hour.substring(3, 5));
            //check if hours and minutes are in valid range to be hours and minutes
            if (hours >= 0 && hours <= 24 && minutes >= 0 && minutes <= 59) {
                return (hours * 60) + minutes;
            }
        }
        return -1;
    }

    //method changes int minutes to String hours in format XX:YY (200 -> 03:20)
    public static String minutesToHours(int minutes) {
        StringBuilder stringBuilder = new StringBuilder();
        int hours = minutes / 60;
        minutes = minutes % 60;

        if (hours < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hours);
        stringBuilder.append(":");
        if (minutes < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(minutes);

        return stringBuilder.toString();
    }
}
