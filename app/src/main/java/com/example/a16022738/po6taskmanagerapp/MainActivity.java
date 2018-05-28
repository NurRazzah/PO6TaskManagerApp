package com.example.a16022738.po6taskmanagerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lvTask;
    ArrayList<Task> al;
    ArrayList<String> alString;
    ArrayAdapter aa;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        lvTask = (ListView)findViewById(R.id.lvTask);
        db = new DBHelper(this);
        db.getWritableDatabase();

        al = db.getAllTasks();
        db.close();

        alString = new ArrayList<>();
        for (int i= 0; i < al.size(); i++){
            String currentTaskString = al.get(i).getName();
            currentTaskString += "\n" + al.get(i).getDescription();
            alString.add(currentTaskString);
        }

        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, alString);

        lvTask.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(i, 9);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9){
            lvTask.performClick();
        }
    }
}
