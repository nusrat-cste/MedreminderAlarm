package com.example.nusra.medreminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmTaskAdapter extends ArrayAdapter<AnAlarmTask> {
    private final SQLiteDatabaseHandler db;
    Context con;
    int res;


    public AlarmTaskAdapter(Context context, int resource, ArrayList<AnAlarmTask> alarmData) {
        super(context, resource, alarmData);
        this.con = context;
        this.res = resource;
        db = SQLiteDatabaseHandler.getHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AnAlarmTask alarmData=getItem(position);

        if(convertView==null){
            LayoutInflater inflator=LayoutInflater.from(con);
            convertView=inflator.inflate(res,parent,false);
        }

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_single_list_task_name);
        tvTitle.setText(alarmData.alarmTaskName);
        Log.e("Data","alarm Taskname:" + alarmData.alarmTaskName);

        String time = timeFormatToDisplay(alarmData.alarmSetHour,alarmData.alarmSetMinutes,"AM");
        TextView tvTime = (TextView)convertView.findViewById(R.id.tv_sin_list_alarm_time);
        tvTime.setText(time);

        Log.e("Data", "alarm set to :"+timeFormatToDisplay(alarmData.alarmSetHour,alarmData.alarmSetMinutes,"AM"));

        TextView tvDays = (TextView)convertView.findViewById(R.id.tv_sin_list_alarm_days);

        if(alarmData.alarmDays.equals(""))
            tvDays.setText("Alarm once");
        else
            tvDays.setText(alarmData.alarmDays);
        Log.e("Data", "alarm days :"+alarmData.alarmDays);

        convertView.setLongClickable(true); //needed here because this is a custom adapter
        final LinearLayout _edit = (LinearLayout) convertView.findViewById(R.id.edit_delete);
        final Button btn_edit = (Button) convertView.findViewById(R.id.ib_edit);
        final Button btn_del = (Button) convertView.findViewById(R.id.ib_delete);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_edit.getVisibility()==View.VISIBLE)
                    _edit.setVisibility(View.INVISIBLE);
                else
                    _edit.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(),"clicked on "+alarmData.alarmTaskName,Toast.LENGTH_SHORT).show();
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),EditAnAlarmActivity.class);
                        intent.putExtra("alarmdata", alarmData);
                        getContext().startActivity(intent);
                    }
                });

                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Are you sure you want to delete this reminder?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //perform any action
                                db.deleteOne(alarmData);
                                remove(alarmData); //deleting item from view
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "alarm deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //perform any action
                                Toast.makeText(getContext(), "do nothing", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //creating alert dialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
        });
        return convertView;
    }

    public static String timeFormatToDisplay(int alarmSetHour, int alarmSetMinutes, String amORpm) {
        String hour_string = String.valueOf(alarmSetHour);
        String minute_string= String.valueOf(alarmSetMinutes);
        if (alarmSetHour > 12) {
            hour_string = String.valueOf(alarmSetHour-12);
            amORpm = "PM";
        }
        if (alarmSetMinutes < 10) {
            minute_string = "0" + String.valueOf(alarmSetMinutes);
        }
        return hour_string+":"+minute_string+" "+amORpm;
    }

}
