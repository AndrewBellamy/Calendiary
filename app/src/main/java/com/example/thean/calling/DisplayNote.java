package com.example.thean.calling;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Andrew Bellamy for SIT207 Assignment 1
 * Student ID : 215240036
 * 20/07/2017
 */

public class DisplayNote extends AppCompatActivity {

    //Global Variables
    private DBControl mydb;
    EditText editText;
    int identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        //Initialise editText and DB, dropping in the context
        editText = (EditText) findViewById(R.id.editText);
        mydb = new DBControl(this);

        Bundle extras = getIntent().getExtras();

        //If a note id is passed through the intent extras, proceed, otherwise throw us out
        if (extras != null) {
            identifier = extras.getInt("id");
            if(identifier > 0) {
                Cursor response = mydb.getData(identifier);
                response.moveToFirst();

                String entry = response.getString(response.getColumnIndex(DBControl.NOTES_ENTRY));

                if (!response.isClosed()) {
                    response.close();
                }

                editText.setText((CharSequence) entry);
            }
        } else {
            if(getParent() == null) {
                setResult(RESULT_CANCELED);
            } else {
                getParent().setResult(RESULT_CANCELED);
            }
            finish();
        }
    }

    //Setting the edit menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.publish_action:
                mydb.updateNote(identifier, editText.getText());
                if(getParent() == null) {
                    setResult(RESULT_OK);
                } else {
                    getParent().setResult(RESULT_OK);
                }
                finish();
                return true;
            case R.id.delete_action:
                mydb.deleteNote(identifier);
                if(getParent() == null) {
                    setResult(RESULT_FIRST_USER);
                } else {
                    getParent().setResult(RESULT_FIRST_USER);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
