package com.example.castedemo.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.castedemo.user.bean.DayBean;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BirthdayActivity extends Activity {
    private static final String TAG = "BirthdayActivity";

    @BindView(R.id.wheel_date_picker_year)
    WheelPicker wheelDatePickerYear;
    @BindView(R.id.wheel_date_picker_month)
    WheelPicker wheelDatePickerMonth;
    @BindView(R.id.wheel_date_picker_day)
    WheelPicker wheelDatePickerDay;
    List<Integer> yearList = new ArrayList<Integer>();
    List<Integer> monthList = new ArrayList<Integer>();
    int[] dayArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] runDayArr = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    List<DayBean> dayBeans = new ArrayList<DayBean>();
    List<DayBean> runDayBeans = new ArrayList<DayBean>();
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        ButterKnife.bind(this);
        initWheelDate();
        wheelDatePickerYear.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                updateYearValue(i + 1900);
            }

            @Override
            public void onWheelScrollStateChanged(int i) {

            }
        });
        wheelDatePickerMonth.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                int year = wheelDatePickerYear.getCurrentItemPosition() + 1900;
                Log.e(TAG, "month i=" + i);
                updateDayValue(year, i);
            }

            @Override
            public void onWheelScrollStateChanged(int i) {

            }
        });
    }

    public void initWheelDate() {
        Calendar calendar = Calendar.getInstance();
        Log.e(TAG, "calendar = " + calendar.toString());
        //年
        for (int i = 1900; i < 2018; i++) {
            yearList.add(i);
        }
        wheelDatePickerYear.setData(yearList);
        //月
        for (int i = 0; i < 12; i++) {
            monthList.add(i + 1);
        }
        wheelDatePickerMonth.setData(monthList);
        wheelDatePickerYear.setSelectedItemPosition(calendar.get(Calendar.YEAR));
        wheelDatePickerMonth.setSelectedItemPosition(calendar.get(Calendar.MONTH));
        //日
        updateYearValue(wheelDatePickerYear.getCurrentItemPosition() + 1900);
        wheelDatePickerDay.setSelectedItemPosition(calendar.get(Calendar.DAY_OF_MONTH) - 1);
//        int year = wheelDatePickerYear.getCurrentItemPosition()+1900;
//        int month = wheelDatePickerMonth.getCurrentItemPosition();
//        Log.e(TAG,"year = "+year);
//        if(isRunYear(year)){
//            for(int i=0;i<12;i++){
//                DayBean dayBean = new DayBean();
//                dayBean.setMonth(i+1);
//                List<Integer> rundayList = new ArrayList<Integer>();
//                for(int j=1;j<=runDayArr[i];j++){
//                    rundayList.add(j);
//                    dayBean.setDay(rundayList);
//                }
//                runDayBeans.add(dayBean);
//                Log.e(TAG,"rundaybeans="+runDayBeans.get(i).getMonth()+",days="+runDayBeans.get(i).getDay().toArray());
//                if(month == i){
//                    wheelDatePickerDay.setData(runDayBeans.get(i).getDay());
//                }
//            }
//        }else{
//            for(int i=0;i<12;i++){
//                DayBean dayBean = new DayBean();
//                dayBean.setMonth(i+1);
//                List<Integer> dayList = new ArrayList<Integer>();
//                for(int j=1;j<=dayArr[i];j++){
//                    dayList.add(j);
//                    dayBean.setDay(dayList);
//                }
//                dayBeans.add(dayBean);
//                Log.e(TAG,"daybeans="+dayBeans.get(i).getMonth()+",day="+dayBeans.get(i).getDay());
//                if(month == i){
//                    wheelDatePickerDay.setData(dayBeans.get(i).getDay());
//                }
//            }
//        }

    }

    /*
    * 根据年份判断每月有几天
    * */
    public void updateYearValue(int year) {
        int month = wheelDatePickerMonth.getCurrentItemPosition();
        if (isRunYear(year)) {
            for (int i = 0; i < 12; i++) {
                DayBean dayBean = new DayBean();
                dayBean.setMonth(i + 1);
                List<Integer> rundayList = new ArrayList<Integer>();
                for (int j = 1; j <= runDayArr[i]; j++) {
                    rundayList.add(j);
                    dayBean.setDay(rundayList);
                }
                runDayBeans.add(dayBean);
//                Log.e(TAG,"rundaybeans="+runDayBeans.get(i).getMonth()+",days="+runDayBeans.get(i).getDay().toArray());
                if (month == i) {
                    wheelDatePickerDay.setData(runDayBeans.get(month).getDay());
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                DayBean dayBean = new DayBean();
                dayBean.setMonth(i + 1);
                List<Integer> dayList = new ArrayList<Integer>();
                for (int j = 1; j <= dayArr[i]; j++) {
                    dayList.add(j);
                    dayBean.setDay(dayList);
                }
                dayBeans.add(dayBean);
//                Log.e(TAG,"daybeans="+dayBeans.get(i).getMonth()+",day="+dayBeans.get(i).getDay());
                if (month == i) {
                    wheelDatePickerDay.setData(dayBeans.get(month).getDay());
                }
            }
        }
    }

    /*
    * 根据年份和月份判断该月有几天
    * */
    public void updateDayValue(int year, int month) {
        if (isRunYear(year)) {
            for (int i = 0; i < runDayBeans.size(); i++) {
                if (month == i) {
                    wheelDatePickerDay.setData(runDayBeans.get(i).getDay());
                }
            }
        } else {
            for (int i = 0; i < dayBeans.size(); i++) {
                if (month == i) {
                    wheelDatePickerDay.setData(dayBeans.get(i).getDay());
                }
            }
        }
    }

    public boolean isRunYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            System.out.println(year + "是闰年");
            return true;
        } else {
            System.out.println(year + "不是闰年!");
            return false;
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_save:
                Intent intent = new Intent(BirthdayActivity.this, UserInfo1Activity.class);
                int year = wheelDatePickerYear.getCurrentItemPosition()+1900;
                int month = wheelDatePickerMonth.getCurrentItemPosition()+1;
                int day = wheelDatePickerDay.getCurrentItemPosition()+1;
                intent.putExtra("birthday",year+"-"+month+"-"+day);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
