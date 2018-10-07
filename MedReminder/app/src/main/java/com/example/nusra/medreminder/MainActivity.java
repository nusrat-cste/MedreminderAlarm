package com.example.nusra.medreminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.getInstance;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;
    Context context;



    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteDatabaseHandler(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = getInstance();

        this.context = this;

        Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

//        Manually saving alarms
        AnAlarmTask t1 = new AnAlarmTask("Viodin", 1, "Once", "", "10/01/2018", 11, 54);
        AnAlarmTask t2 = new AnAlarmTask("Histamin", 1, "Multiple", "Mon,Fri,Sat", "10/01/2018", 11, 57);
        AnAlarmTask t3 = new AnAlarmTask("Napa", 1, "Once", "Mon,Fri", "10/01/2018", 12, 45);
        AnAlarmTask t4 = new AnAlarmTask("Napa", 1, "Once", "Mon,Fri", "10/01/2018", 12, 50);

//        db.addAnAlarmTask(t1);
//        db.addAnAlarmTask(t2);
//        db.addAnAlarmTask(t3);
//        db.addAnAlarmTask(t4);
//        To get all the alarms stored in the DB

        ArrayList<AnAlarmTask> allAlarmTasks = db.getAllAlarmTasks();

        db.deleteAll();
//checks if the db is not null, and if not, it prints all the records
        if (allAlarmTasks != null) {
            int id = 0;

            for (AnAlarmTask Task:allAlarmTasks) {

                Log.e("DBdata",Task.getAlarmTaskName()+" "+Task.alarmTaskId);

                calendar.set(Calendar.HOUR_OF_DAY, Task.getAlarmSetHour());
                calendar.set(Calendar.MINUTE, Task.getAlarmSetMinutes());
                calendar.set(Calendar.SECOND, Task.getAlarmSetSeconds());
                calendar.set(Calendar.MILLISECOND, Task.getAlarmSetMilliSec());

                long time = calendar.getTimeInMillis();

                Log.e("Alarm", "Time" + String.valueOf(time));

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, id,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //set the alarm manager
                Log.e("Alarm","Starting alarm manager");
                alarm_manager.set(AlarmManager.RTC_WAKEUP, time, pending_intent);
                id++;
            }
        }
        else
            Log.e("DBdata","there is no records there");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_new_alarm);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddAMedicineInfo.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
