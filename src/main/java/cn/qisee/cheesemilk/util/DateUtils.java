package cn.qisee.cheesemilk.util;

import java.time.LocalDateTime;

public class DateUtils {

    public static String getNowDateAsNumber(){
        LocalDateTime dateTime = LocalDateTime.now();
        return String.valueOf(dateTime.getYear()) +
                dateTime.getMonthValue() +
                dateTime.getDayOfMonth() +
                dateTime.getHour() +
                dateTime.getMinute();
    }
}
