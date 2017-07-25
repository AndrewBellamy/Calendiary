package com.example.thean.calling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    DBHelper mydb;
    java.util.Date current_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        current_date = new Date();

        mydb = new DBHelper(this);
        ArrayList<String> array_list = mydb.getDataByDate((java.sql.Date) current_date);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);

        ListView obj = (ListView) findViewById(R.id.noteList);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               //method and intent behind selecting a note entry
               Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", arg2);

               Intent intent = new Intent(getApplicationContext(), DisplayNote.class);
               intent.putExtras(dataBundle);
               startActivity(intent);
           }
        });
    }

    //Setting the main menu for the home view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.change_date:
                Intent intent = new Intent(getApplicationContext(),DateSelect.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
