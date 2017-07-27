package com.example.thean.calling;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

public class AddNote extends AppCompatActivity {

    private DBControl mydb;
    EditText addText;
    long noteDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intentAdd = getIntent();
        //set the editText element
        addText = (EditText) findViewById(R.id.addText);
        Date defaultDate = new Date();
        noteDate = intentAdd.getLongExtra("longDate", defaultDate.getTime());

        mydb = new DBControl(this);
    }

    //Setting the main menu for the home view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.publish_action:
                mydb.insertNote(addText.getText(), noteDate);
                if(getParent() == null) {
                    setResult(RESULT_OK);
                } else {
                    getParent().setResult(RESULT_OK);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
