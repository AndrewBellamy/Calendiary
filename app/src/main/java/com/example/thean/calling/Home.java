package com.example.thean.calling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Home extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.thean.helloandroid.MESSAGE";
    DBHelper mydb;
    Date current_date;
    EditText currentDateText;
    String currentDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        current_date = new Date();
        currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(current_date);
        currentDateText = (EditText) findViewById(R.id.currentDateText);

        mydb = new DBHelper(this);
        ArrayList<String> array_list = mydb.getDataByDate(current_date.getTime());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);

        ListView obj = (ListView) findViewById(R.id.noteList);
        if (array_list.isEmpty()) {
            obj.setEmptyView( findViewById(R.id.emptyListView));
        } else {
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

        currentDateText.setText((CharSequence) currentDateString);
        currentDateText.setFocusable(false);
        currentDateText.setClickable(false);
    }

    /*
    //TODO: include the returned results to set the currentDate
    @Override
    protected void onActivityResult() {

    }
    */

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
                Intent intentDate = new Intent(getApplicationContext(),DateSelect.class);
                startActivity(intentDate);
                return true;
            case R.id.add_entry:
                Intent intentAdd = new Intent(getApplicationContext(),AddNote.class);
                intentAdd.putExtra("longDate", current_date.getTime());
                startActivity(intentAdd);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
