package com.example.sampleproject.SupportClasses;

import java.util.ArrayList;

public class JamesUtilities {
    /*method to convert milliseconds into different time format*/
//    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatMilliseconds(long milliseconds)
    {
        final long MILLIS_IN_SECOND = 1000;

        //if less than one minute => return simple format
        if (milliseconds < MILLIS_IN_SECOND * 60)
        {
            double msDouble = milliseconds;
            double finalSeconds = msDouble/1000;
            String result = finalSeconds + " second";
            //if more than 2 seconds => add ending "s"
            if (finalSeconds >= 2)
            {
                result += "s";
            }
            return result;
        }

        //return full format for bigger numbers
        else
        {
            final long SECONDS_IN_MINUTE = 60;
            final long SECONDS_IN_HOUR = 3600;
            final long SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;
            final long SECONDS_IN_YEAR = SECONDS_IN_DAY * 365; //assume a year has 365 days
            long years, days, hours, minutes, seconds = 0;
            String toAdd = "";
            ArrayList<String> resultString = new ArrayList<String>();
            //String[] units = {"year", "day", "hour", "minute"}; //can be utilized to change languages

            seconds = milliseconds / 1000;
            milliseconds = milliseconds % MILLIS_IN_SECOND;

            years = seconds / SECONDS_IN_YEAR;
            seconds = seconds % SECONDS_IN_YEAR;
            if (years != 0)
            {
                toAdd = years + " year";
                if (years > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            days = seconds / SECONDS_IN_DAY;
            seconds = seconds % SECONDS_IN_DAY;
            if (days != 0)
            {
                if (years != 0)
                {
                    resultString.add(", ");
                }
                toAdd = days + " day";
                if (days> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            hours = seconds / SECONDS_IN_HOUR;
            seconds = seconds % SECONDS_IN_HOUR;
            if (hours != 0)
            {
                if (years != 0 || days != 0)
                {
                    resultString.add(", ");
                }
                toAdd = hours + " hour";
                if (hours> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            minutes = seconds / SECONDS_IN_MINUTE;
            seconds = seconds % SECONDS_IN_MINUTE;
            if (minutes != 0)
            {
                if (years != 0 || days != 0 || hours != 0)
                {
                    resultString.add(", ");
                }
                toAdd = minutes + " minute";
                if (minutes> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            if (seconds != 0)
            {
                if (years != 0 || days != 0 || hours != 0 || minutes != 0)
                {
                    resultString.add(", ");
                }
                toAdd = seconds + " second";
                if (seconds > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            if (milliseconds != 0)
            {
                if (years != 0 || days != 0 || hours != 0 || minutes != 0 || seconds != 0)
                {
                    resultString.add(", ");
                }
                toAdd = milliseconds + " millisecond";
                if (milliseconds > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            //nothing printed => just say "no time"
            if (resultString.size() == 0)
            {
                resultString.add("no time");
            }
            //replace last comma with " and "
            else if (resultString.size() > 1 && resultString.get(resultString.size() - 2).equals(", "))
            {
                resultString.set(resultString.size() - 2, " and ");
            }

            //converts arrayList to normal string without using String.join
            String fullResult = "";
            for (String s : resultString)
            {
                fullResult += s;
            }
            return fullResult;
        }
    } //ends formatMilliseconds


    /* Strips everything that's not letters or digits or from string. Turns everything lowercase
    E.g: "It's Raining Men" into "itsrainingmen", "Let's Go!" into "letsgo"
*/
    public static String stripString(String aString)
    {
        //regular expression: only numbers and letters will be kept in string
        String newString = aString.replaceAll("[^a-zA-Z0-9]","");
        return newString.toLowerCase();
    }
}
