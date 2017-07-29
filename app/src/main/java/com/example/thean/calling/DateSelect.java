package com.example.thean.calling;

import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by Andrew Bellamy for SIT207 Assignment 1
 * Student ID : 215240036
 * 20/07/2017
 */

public class DateSelect extends AppCompatActivity {

    //Global Variables
    Date default_date;
    DatePicker datePicker;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);

        //Current date is passed through intent extras, or defaults to today
        default_date = new Date();
        long current_date = getIntent().getLongExtra("current_date", default_date.getTime());

        //Calendar class used in place of LocalDate, missing from Java 6/7
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(current_date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        //Date picker value is initialised, along with onClick method handler
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                Intent intentCallBack = new Intent();
                intentCallBack.putExtra("callback_date", calendar.getTimeInMillis());
                if(getParent() == null) {
                    setResult(RESULT_OK, intentCallBack);
                } else {
                    getParent().setResult(RESULT_OK, intentCallBack);
                }
                finish();
            }
        });
    }
}
