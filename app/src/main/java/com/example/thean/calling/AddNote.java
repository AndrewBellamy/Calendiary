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

    private DBHelper mydb;
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

        mydb = new DBHelper(this);
        //Bundle extras = getIntent().getExtras();
        /*
        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value>0) {
                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();

                String entry = rs.getString(rs.getColumnIndex(DBHelper.NOTES_ENTRY));

                if (!rs.isClosed()) {
                    rs.close();
                }

                addText.setText((CharSequence) entry);
                addText.setFocusable(false);
                addText.setClickable(false);
            }
        }
        */
    }

    //onOptionsItemSelected()
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
