//Your task in order to complete this Kata is to write a function which formats a duration, given as a number of seconds, in a human-friendly way.
//
//The function must accept a non-negative integer. If it is zero, it just returns "now". Otherwise, the duration is expressed as a combination of years, days, hours, minutes and seconds.
//
//It is much easier to understand with an example:
//
//TimeFormatter.formatDuration(62)   //returns "1 minute and 2 seconds"
//TimeFormatter.formatDuration(3662) //returns "1 hour, 1 minute and 2 seconds"
//For the purpose of this Kata, a year is 365 days and a day is 24 hours.
//
//Note that spaces are important.
//
//Detailed rules
//The resulting expression is made of components like 4 seconds, 1 year, etc. In general, a positive integer and one of the valid units of time, separated by a space. The unit of time is used in plural if the integer is greater than 1.
//
//The components are separated by a comma and a space (", "). Except the last component, which is separated by " and ", just like it would be written in English.
//
//A more significant units of time will occur before than a least significant one. Therefore, 1 second and 1 year is not correct, but 1 year and 1 second is.
//
//Different components have different unit of times. So there is not repeated units like in 5 seconds and 1 second.
//
//A component will not appear at all if its value happens to be zero. Hence, 1 minute and 0 seconds is not valid, but it should be just 1 minute.
//
//A unit of time must be used "as much as possible". It means that the function should not return 61 seconds, but 1 minute and 1 second instead. Formally, the duration specified by of a component must not be greater than any valid more significant unit of time.


public class TimeFormatter {

    public static String formatDuration(int seconds) {

        if (seconds == 0) {
            return "now";
        }

        StringBuilder sb = new StringBuilder();
        int[] result = new int[5];

        result[0] = seconds / 31536000;
        seconds = seconds % 31536000;
        result[1] = seconds / 86400;
        seconds = seconds % 86400;
        result[2] = seconds / 3600;
        seconds = seconds % 3600;
        result[3] = seconds / 60;
        seconds = seconds % 60;
        result[4] = seconds % 60;

        int year = result[0];
        if (year > 0) {
            sb.append(year + " ");
            sb.append(year > 1 ? "years" : "year");
        }
        int day = result[1];
        if (day > 0) {
            if (isLast(result, 1) && !isFirst(result, 1)) {
                sb.append(" and ");
            } else if (!isFirst(result, 1)) {
                sb.append(", ");
            }
            sb.append(day + " ");
            sb.append(day > 1 ? "days" : "day");
        }
        int hour = result[2];
        if (hour > 0) {
            if (isLast(result, 2) && !isFirst(result, 2)) {
                sb.append(" and ");
            } else if (!isFirst(result, 2)) {
                sb.append(", ");
            }
            sb.append(hour + " ");
            sb.append(hour > 1 ? "hours" : "hour");
        }
        int minute = result[3];
        if (minute > 0) {
            if (isLast(result, 3) && !isFirst(result, 3)) {
                sb.append(" and ");
            } else if (!isFirst(result, 3)) {
                sb.append(", ");
            }
            sb.append(minute + " ");
            sb.append(minute > 1 ? "minutes" : "minute");
        }
        int second = result[4];
        if (second > 0) {
            if (!isFirst(result, 4)) {
                sb.append(" and ");
            }
            sb.append(second + " ");
            sb.append(second > 1 ? "seconds" : "second");
        }

        return sb.toString();

    }

    private static boolean isLast(int[] result, int index) {
        for (int i = index + 1; i < result.length; i++) {
            if (result[i] != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFirst(int[] result, int index) {
        for (int i = index - 1; i >= 0; i--) {
            if (result[i] != 0) {
                return false;
            }
        }
        return true;
    }
}