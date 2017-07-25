package com.example.thean.calling;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DisplayNote extends AppCompatActivity {

    private DBHelper mydb;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        //set the editText element
        editText = (EditText) findViewById(R.id.editText);

        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if(Value>0) {
                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();

                String entry = rs.getString(rs.getColumnIndex(DBHelper.NOTES_ENTRY));

                if (!rs.isClosed()) {
                    rs.close();
                }

                editText.setText((CharSequence) entry);
                editText.setFocusable(false);
                editText.setClickable(false);
            }
        }
    }

    //onOptionsItemSelected()
}
