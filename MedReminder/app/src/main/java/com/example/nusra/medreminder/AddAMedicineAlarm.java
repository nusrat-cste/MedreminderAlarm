package com.example.nusra.medreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddAMedicineAlarm extends AppCompatActivity {

    TimePicker timePicker;
    String result;
    Button settime ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amedicine_alarm);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        settime = (Button) findViewById(R.id.alarm_on);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent;
                returnIntent = new Intent();
                int hours = timePicker.getHour();
                int minutes = timePicker.getMinute();
                int ampm = timePicker.get
                Log.e("Data",hours+" "+minutes);
                result = String.valueOf(hours*60+minutes);
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
