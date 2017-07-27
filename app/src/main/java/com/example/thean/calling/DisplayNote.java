package com.example.thean.calling;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class DisplayNote extends AppCompatActivity {

    private DBControl mydb;
    EditText editText;
    int identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        //set the editText element
        editText = (EditText) findViewById(R.id.editText);

        mydb = new DBControl(this);
        Bundle extras = getIntent().getExtras();

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
        }
    }

    //Setting the main menu for the home view
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
