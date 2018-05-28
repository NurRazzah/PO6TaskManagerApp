package com.example.a16022738.po6taskmanagerapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

class AddTask extends AppCompatActivity{
    EditText etName, etDesc, etTime;
    Button btnAdd, btnCan;
    DBHelper db;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_add_task);

        etName = (EditText)findViewById(R.id.etName);
        etDesc = (EditText)findViewById(R.id.etDesc);
        etTime = (EditText)findViewById(R.id.etTime);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCan = (Button)findViewById(R.id.btnCancel);

        db = new DBHelper(this);
        db.getWritableDatabase();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int time = Integer.parseInt(etTime.getText().toString());

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, time);

                //Create a new PendingIntent and add it to the AlarmManager
                Intent intent = new Intent(AddTask.this,
                        MyReceiver.class);

                int reqCode = 12345;
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(AddTask.this,
                                reqCode, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                // Get AlarmManager instance
                am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);

                // Set the alarm
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                DBHelper db = new DBHelper(AddTask.this);
                db.insertTask(name, desc);
                setResult(RESULT_OK);
                finish();
            }
        });



        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });


    }
}
