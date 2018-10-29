package com.example.nusra.medreminder;

import android.app.Activity;
import android.app.AlarmManager;
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

    AlarmManager alarm_manager;
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
        final Calendar calendar = Calendar.getInstance();

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent;
                returnIntent = new Intent();

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                // get the int values of the hour and minute
                int hours = timePicker.getHour();
                int minutes = timePicker.getMinute();

                // convert the int values to strings
                String hour_string = String.valueOf(hours);
                String minute_string = String.valueOf(minutes);

                result = hour_string+","+minute_string;

                Log.e("Data",hours+" "+minutes);
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
