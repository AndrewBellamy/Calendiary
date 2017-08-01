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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrew Bellamy for SIT207 Assignment 1
 * Student ID : 215240036
 * 20/07/2017
 */

public class Home extends AppCompatActivity {

    //Constants
    private static final int CHANGE_DATE = 1;
    private static final int ADD_NOTE = 2;
    private static final int DISPLAY_NOTE = 3;

    //Global Variables
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

        //On entering the single instance, set the date to today
        current_date = new Date();
        currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(current_date);

        //Initialise the listView and DB, dropping in the context
        mydb = new DBControl(this);
        noteList = (ListView) findViewById(R.id.noteList);

        //Initial call for today's notes
        retrieveNotes();

        //Sets the date label to today
        currentDateText = (EditText) findViewById(R.id.currentDateText);
        currentDateText.setText((CharSequence) currentDateString);
        currentDateText.setFocusable(false);
        currentDateText.setClickable(true);
    }

    /**
     * Calls the getDataByDate method of the DBControl class, passing in the set date in milliseconds.
     * Populates the list view with the array list, using the array adapter.
     * No parameters required, uses globals.
     */
    public void retrieveNotes() {
        Bundle dataBundle = mydb.getDataByDate(current_date.getTime());
        array_list = dataBundle.getStringArrayList("entries");
        array_identifiers = dataBundle.getStringArrayList("ids");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        if (array_list.size() == 0) {
            noteList.setAdapter(arrayAdapter);
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

    /**
     * Fires the event handler for pressing the date label. Navigates to the
     * date selection activity and passes in the current date in the intent
     */
    public void onChangeDate(View view) {
        Intent intentDate = new Intent(this, DateSelect.class);
        intentDate.putExtra("current_date", current_date.getTime());
        startActivityForResult(intentDate, CHANGE_DATE);
    }

    //Setting the main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.add_entry:
                Intent intentAdd = new Intent(getApplicationContext(),AddNote.class);
                intentAdd.putExtra("longDate", current_date.getTime());
                startActivityForResult(intentAdd, ADD_NOTE);
                return true;
            case R.id.preferences_open:
                Intent intentPref = new Intent(getApplicationContext(),preferencesActivity.class);
                startActivity(intentPref);
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
                currentDateText.setClickable(true);
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

    /**
     * Cancel arrows for all activities leading off from home.
     *
     * @param keycode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
