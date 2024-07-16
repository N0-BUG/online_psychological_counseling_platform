package com.pcs.config;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

public class ComputedAge {
    //计算年龄
    public static int getAge(String birth) throws ParseException {
        Calendar now = Calendar.getInstance();
        Calendar birthdate = Calendar.getInstance();

        // 将时间戳字符串转换为Date对象
        Date date = new Date(Long.parseLong(birth));
        birthdate.setTime(date);

        int age = now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);

        if (now.get(Calendar.MONTH) < birthdate.get(Calendar.MONTH) ||
                (now.get(Calendar.MONTH) == birthdate.get(Calendar.MONTH) && now.get(Calendar.DATE) < birthdate.get(Calendar.DATE))) {
            age--;
        }

        // 判断闰年
        if (birthdate.get(Calendar.MONTH) == Calendar.FEBRUARY && birthdate.get(Calendar.DATE) > 28 &&
                isLeapYear(birthdate.get(Calendar.YEAR))) {
            age--;
        }

        return age;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}
