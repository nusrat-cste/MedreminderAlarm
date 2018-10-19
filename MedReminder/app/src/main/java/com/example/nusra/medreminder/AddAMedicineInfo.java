package com.example.nusra.medreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class AddAMedicineInfo extends AppCompatActivity {

    PendingIntent pending_intent;
    AlarmManager alarm_manager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amedicine_info);

//        db.deleteAll();

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = getInstance();
        this.context = this;
        Intent my_intent = new Intent(this.context, Alarm_Receiver.class);
//        sendng hardcoded alarm paprameters to store inside DB.
          hardCodedAlarmDataPush();

//        To get all the alarms stored in the DB
        ArrayList<AnAlarmTask> allAlarmTasks = db.getAllAlarmTasks();

//checks if the db is not null, and if not, it prints all the records
        /*if (allAlarmTasks != null) {
            int id = 0;

            for (AnAlarmTask Task:allAlarmTasks) {

                Log.e("DBdata",Task.getAlarmTaskName()+" "+Task.alarmTaskId);

                calendar.set(Calendar.HOUR_OF_DAY, Task.getAlarmSetHour());
                calendar.set(Calendar.MINUTE, Task.getAlarmSetMinutes());
                calendar.set(Calendar.SECOND, Task.getAlarmSetSeconds());
                calendar.set(Calendar.MILLISECOND, Task.getAlarmSetMilliSec());

                long time = calendar.getTimeInMillis();

                Log.e("Alarm", "Time" + String.valueOf(time));

                pending_intent = PendingIntent.getBroadcast(getApplicationContext(), id,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //set the alarm manager
                Log.e("Alarm","Starting alarm manager");
                alarm_manager.set(AlarmManager.RTC_WAKEUP, time, pending_intent);
                id++;
            }
        }
        else
            Log.e("DBdata","there is no records there");*/
    }

    private void hardCodedAlarmDataPush() {
        //        Manually saving alarms
        AnAlarmTask t1 = new AnAlarmTask("Viodin", 1, "Once", "", "10/01/2018", 11, 54);
        AnAlarmTask t2 = new AnAlarmTask("Histamin", 1, "Multiple", "Mon,Fri,Sat", "10/01/2018", 11, 57);
        AnAlarmTask t3 = new AnAlarmTask("Napa", 1, "Once", "Mon,Fri", "10/01/2018", 12, 45);
        AnAlarmTask t4 = new AnAlarmTask("Napa", 1, "Once", "Mon,Fri", "10/01/2018", 12, 50);
        db.addAnAlarmTask(t1);
        db.addAnAlarmTask(t2);
        db.addAnAlarmTask(t3);
        db.addAnAlarmTask(t4);
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_meat:
//                if (checked)
//                // Put some meat on the sandwich
//            else
//                // Remove the meat
//                break;
//            case R.id.checkbox_cheese:
//                if (checked)
//                // Cheese me
//            else
//                // I'm lactose intolerant
//                break;
//
    }
}
