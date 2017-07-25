package com.example.thean.calling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

public class DateSelect extends AppCompatActivity {

    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

    }
}
