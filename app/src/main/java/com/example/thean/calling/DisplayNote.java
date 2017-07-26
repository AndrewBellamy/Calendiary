package com.example.thean.calling;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DisplayNote extends AppCompatActivity {

    private DBControl mydb;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        //set the editText element
        editText = (EditText) findViewById(R.id.editText);

        mydb = new DBControl(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value>0) {
                Cursor response = mydb.getData(Value);
                response.moveToFirst();

                String entry = response.getString(response.getColumnIndex(DBControl.NOTES_ENTRY));

                if (!response.isClosed()) {
                    response.close();
                }

                editText.setText((CharSequence) entry);
                editText.setFocusable(false);
                editText.setClickable(false);
            }
        }
    }

    //onOptionsItemSelected()
}
