package com.example.thean.calling;

import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Date;

public class DateSelect extends AppCompatActivity {

    Date default_date;
    DatePicker datePicker;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);

        default_date = new Date();
        long current_date = getIntent().getLongExtra("current_date", default_date.getTime());

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(current_date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


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
