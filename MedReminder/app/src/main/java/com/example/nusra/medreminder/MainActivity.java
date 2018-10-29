package com.example.nusra.medreminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.getInstance;

public class MainActivity extends AppCompatActivity {

    PendingIntent pending_intent;
    AlarmManager alarm_manager;
    Context context;
    private SQLiteDatabaseHandler db;
    ListView listView;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SQLiteDatabaseHandler.getHelper(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        final Intent intent = new Intent(getApplicationContext(), AddAMedicineInfo.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_new_alarm);

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        this.context = this;

//        db.deleteAll();
        // To get all the alarms stored in the DB
        ArrayList<AnAlarmTask> allAlarmTasks = db.getAllAlarmTasks();

        listView = (ListView)findViewById(R.id.list_of_tasks);
//        listView.setLongClickable(true);

        ArrayAdapter adapter = new AlarmTaskAdapter(this,R.layout.single_alarmtask_list_item, allAlarmTasks);

        listView.setAdapter(adapter); //binding all tasks to listview

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Data1", "position :"+position);
                return false;
                }
                });

        //checks if the db is not null, and if not, it prints all the records
        if (allAlarmTasks != null) {

            for (AnAlarmTask Task:allAlarmTasks) {
                Calendar calendar = getInstance();

                Log.e("Data",Task.getAlarmTaskName()+" "+Task.alarmTaskName);

                calendar.set(Calendar.HOUR_OF_DAY, Task.getAlarmSetHour());
                calendar.set(Calendar.MINUTE, Task.getAlarmSetMinutes());
                calendar.set(Calendar.SECOND, Task.getAlarmSetSeconds());
                calendar.set(Calendar.MILLISECOND, Task.getAlarmSetMilliSec());

                if(calendar.before(Calendar.getInstance())) {
                    continue;
                }

                Intent my_intent = new Intent(getApplicationContext(), Alarm_Receiver.class);

                my_intent.putExtra("UID",  Task.alarmTaskId);
                Log.e("Data", "uid is "+ String.valueOf(Task.alarmTaskId));
                long time = calendar.getTimeInMillis();


                Log.e("Alarm", "Time" + String.valueOf(time));

                    pending_intent = PendingIntent.getBroadcast(getApplicationContext(),Task.alarmTaskId ,
                            my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //set the alarm manager
                    Log.e("Alarm","Starting alarm manager");
                    alarm_manager.setExact(AlarmManager.RTC_WAKEUP, time, pending_intent);
            }
        }
        else
            Log.e("DBdata","there is no records there");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
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
