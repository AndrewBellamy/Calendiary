package com.example.thean.calling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private static final int CHANGE_DATE = 1;
    DBControl mydb;
    Date current_date;
    EditText currentDateText;
    String currentDateString;
    ArrayList<String> array_list;
    ArrayAdapter<String> arrayAdapter;
    ListView noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        current_date = new Date();
        currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(current_date);
        currentDateText = (EditText) findViewById(R.id.currentDateText);

        mydb = new DBControl(this);
        noteList = (ListView) findViewById(R.id.noteList);

        retrieveNotes();
        currentDateText.setText((CharSequence) currentDateString);
        currentDateText.setFocusable(false);
        currentDateText.setClickable(false);
    }

    //Database call and list population
    public void retrieveNotes() {
        array_list = mydb.getDataByDate(current_date.getTime());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        Log.i("List count", String.valueOf(array_list.size()));
        if (array_list.size() == 0) {
            noteList.setAdapter(arrayAdapter);
            noteList.setEmptyView( findViewById(R.id.emptyListView));
        } else {
            noteList.setAdapter(arrayAdapter);
            noteList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intentDate = new Intent(getApplicationContext(),DateSelect.class);
                intentDate.putExtra("current_date", current_date.getTime());
                startActivityForResult(intentDate, CHANGE_DATE);
                return true;
            case R.id.add_entry:
                Intent intentAdd = new Intent(getApplicationContext(),AddNote.class);
                intentAdd.putExtra("longDate", current_date.getTime());
                startActivityForResult(intentAdd, CHANGE_DATE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("request:", String.valueOf(requestCode));
        Log.i("result:", String.valueOf(resultCode));
        if (requestCode == CHANGE_DATE) {
            if (resultCode == RESULT_OK) {
                Date defaultDate = new Date();
                long new_date = data.getLongExtra("callback_date", defaultDate.getTime());
                current_date = new Date(new_date);

                retrieveNotes();
                currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(current_date);

                currentDateText.setText((CharSequence) currentDateString);
                currentDateText.setFocusable(false);
                currentDateText.setClickable(false);
            }
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
