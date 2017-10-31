package com.example.castedemo.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class DayBean {
    private int month;
    private List<Integer> day;

    public DayBean() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public List<Integer> getDay() {
        return day;
    }

    public void setDay(List<Integer> day) {
        this.day = day;
    }
}
