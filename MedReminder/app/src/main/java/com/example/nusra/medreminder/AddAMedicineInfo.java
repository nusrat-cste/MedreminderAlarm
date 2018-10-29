package com.example.nusra.medreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.getInstance;

public class AddAMedicineInfo extends AppCompatActivity {
    EditText tv_taskName ;
    TextView alarm_set_message;
    ImageButton addAlarm;
    Button setAlarmTask;
    LinearLayout l ;
    private String alarmFrequency ="Once";
    private String alarmTaskname;
    private int hours;
    private int minutes;
    List<String> Weekdays = new ArrayList<String>();
    private String alarmdays="";
    private Context mContext = this;
    private SQLiteDatabaseHandler db;
//    private ArrayList<String> Weekdays = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SQLiteDatabaseHandler.getHelper(this);
        setContentView(R.layout.activity_add_amedicine_info);
        Intent intent = getIntent();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        final String formattedDate = df.format(c);

        tv_taskName = (EditText) findViewById(R.id.tv_alarm_taskname);
        alarm_set_message = (TextView) findViewById(R.id.tv_alartime) ;
        addAlarm = (ImageButton) findViewById(R.id.bt_add_alarm);
        l = (LinearLayout) findViewById(R.id.linearLayout3);

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(),AddAMedicineAlarm.class),1);
            }
        });

        setAlarmTask = (Button)findViewById(R.id.bt_savetask);

        setAlarmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmTaskname = tv_taskName.getText().toString();
                Log.e("Data","task"+ alarmTaskname);

                if(alarmFrequency.equals("Once"))
                {
                    AnAlarmTask _task = new AnAlarmTask(alarmTaskname, 1, alarmFrequency, alarmdays , formattedDate, hours, minutes);
                    db.addAnAlarmTask(_task);
                }
                else if(alarmFrequency.equals("Multiple")) {
                    for (String day : Weekdays
                            ) {
                        alarmdays += day + ",";
                    }
                    alarmdays = alarmdays.substring(0, alarmdays.length() - 1);
                    AnAlarmTask _task = new AnAlarmTask(alarmTaskname, 1, alarmFrequency, alarmdays , formattedDate, hours, minutes);
                    db.addAnAlarmTask(_task);
                }


                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.checkbox_repeat:
                if (checked){
                    l.setVisibility(View.VISIBLE);
                    alarmFrequency = "Multiple";
                    Log.e("Data",alarmFrequency);
                }
            else {
                    alarmFrequency = "Once";
                    l.setVisibility(View.INVISIBLE);
                    Log.e("Data",alarmFrequency);
                }
                break;
            case R.id.check_mon:
                if (checked) {
                    if(!Weekdays.contains("Mon")){
                        Weekdays.add("Mon");
                        Log.e("Data","monday added");}
                }
                else{
                    if(Weekdays.contains("Mon")){
                    Weekdays.remove("Mon");
                    Log.e("Data","monday removed");
                    }
                }
                break;

                case R.id.check_tues:
                    if (checked){
                        if(!Weekdays.contains("Tues")){
                            Weekdays.add("Tues");
                            Log.e("Data","Tuesday added");}
                    }
                    else {
                        if(Weekdays.contains("Tues")){
                            Weekdays.remove("Tues");
                            Log.e("Data","Tuesday removed");
                        }
                    }
                    break;

                case R.id.check_wed:
                    if (checked){
                        if(!Weekdays.contains("Wed")){
                            Weekdays.add("Wed");
                            Log.e("Data","Wednesday added");}
                    }
                    else {
                        if(Weekdays.contains("Wed")){
                            Weekdays.remove("Wed");
                            Log.e("Data","Wednesday removed");
                        }
                    }
                    break;

                case R.id.check_thur:
                    if (checked){
                        if(!Weekdays.contains("Thurs")){
                            Weekdays.add("Thurs");
                            Log.e("Data","Thursday added");}
                    }
                    else {
                        if(Weekdays.contains("Thurs")){
                            Weekdays.remove("Thurs");
                            Log.e("Data","Thursday removed");
                        }
                    }
                    break;

                case R.id.check_fri:
                    if (checked){
                        if(!Weekdays.contains("Fri")){
                            Weekdays.add("Fri");
                            Log.e("Data","Friday added");}
                    }
                    else {
                        if(Weekdays.contains("Fri")){
                            Weekdays.remove("Fri");
                            Log.e("Data","Friday removed");
                        }
                    }
                    break;

                case R.id.check_sat:
                    if (checked){
                        if(!Weekdays.contains("Sat")){
                            Weekdays.add("Sat");
                            Log.e("Data","Saturday added");}
                    }
                    else {
                        if(Weekdays.contains("Sat")){
                            Weekdays.remove("Sat");
                            Log.e("Data","Saturday removed");}
                    }
                    break;

                case R.id.check_sun:
                    if (checked){
                        if(!Weekdays.contains("Sun")){
                            Weekdays.add("Sun");
                            Log.e("Data","Sunday added");}
                    }
                    else {
                        if(Weekdays.contains("Sun")){
                            Weekdays.remove("Sun");
                            Log.e("Data","Sunday removed");
                        }
                    }
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
//                Log.e("Data", String.valueOf(result));
                String[] time = result.split(",");
                hours = Integer.parseInt(time[0]);
                minutes = Integer.parseInt(time[1]);
                String hour_string = String.valueOf(hours);
                String minute_string=String.valueOf(minutes);

                String amORpm = "AM";
//                convert 24-hour time to 12-hour time
                if (hours > 12) {
                    hour_string= String.valueOf(hours-12);
                    amORpm = "PM";
                }
                if (minutes < 10) {
                    minute_string = "0" + String.valueOf(minutes);
                }

                alarm_set_message.setText("Alarm set to "+hour_string+":"+minute_string+" "+amORpm);
                alarm_set_message.setVisibility(View.VISIBLE);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.e("Data","No result");
            }
        }
    }//onActivityResult
}
