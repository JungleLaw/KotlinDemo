package com.law.kotlindemo;

import java.util.Calendar;

/**
 * Created by Jungle on 2018/3/25.
 */

public class Day {
    private int year;
    private int month;
    private int day;
    private long millies;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        millies = calendar.getTimeInMillis();
        return millies;
    }
}
