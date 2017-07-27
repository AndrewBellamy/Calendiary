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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.thean.helloandroid.MESSAGE";
    private static final int CHANGE_DATE = 1;
    private static final int ADD_NOTE = 2;
    private static final int DISPLAY_NOTE = 3;
    DBControl mydb;
    Date current_date;
    EditText currentDateText;
    String currentDateString;
    ArrayList<String> array_list;
    ArrayList<String> array_identifiers;
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
        Bundle dataBundle = mydb.getDataByDate(current_date.getTime());
        array_list = dataBundle.getStringArrayList("entries");
        array_identifiers = dataBundle.getStringArrayList("ids");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        if (array_list.size() == 0) {
            noteList.setAdapter(arrayAdapter);
            noteList.setEmptyView( findViewById(R.id.emptyListView));
        } else {
            noteList.setAdapter(arrayAdapter);
            noteList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    //method and intent behind selecting a note entry
                    Bundle sendBundle = new Bundle();
                    Integer selectionID = Integer.parseInt(array_identifiers.get(arg2));
                    sendBundle.putInt("id", selectionID);

                    Intent intent = new Intent(getApplicationContext(), DisplayNote.class);
                    intent.putExtras(sendBundle);
                    startActivityForResult(intent, DISPLAY_NOTE);
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
                startActivityForResult(intentAdd, ADD_NOTE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if (requestCode == ADD_NOTE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();
                retrieveNotes();
            }
        }
        if (requestCode == DISPLAY_NOTE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Note Changed", Toast.LENGTH_SHORT).show();
                retrieveNotes();
            }
            if (resultCode == RESULT_FIRST_USER) {
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
                retrieveNotes();

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
