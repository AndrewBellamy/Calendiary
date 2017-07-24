package com.example.thean.calling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllNotes();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.noteList);
        obj.setAdapter(arrayAdapter)
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           public void onItemClick(int arg0) {
               //method and intent behind selecting a note entry
               int id_To_open = arg0;

               Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", id_To_open);

               Intent intent = new Intent(getApplicationContext(), DisplayNote.class);
               intent.putExtras(dataBundle);
               startActivity(intent);
           }
        });
    }


}
