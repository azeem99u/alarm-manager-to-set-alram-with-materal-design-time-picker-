package com.example.android.setalarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.setalarmapp.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MaterialTimePicker picker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btnSelectTime.setOnClickListener(view -> {
            showTimePicker();
        });
        binding.btnSetAlarm.setOnClickListener(view -> {
            if (picker != null){
                setAlarm();
            }

        });
        binding.btnCancelAlarm.setOnClickListener(view -> {
            cancelAlarm();
        });

    }


    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this,MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        if(calendar.before(Calendar.getInstance())){
            //nextDay
            calendar.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        Toast.makeText(this, "Alarm Set Successfully !", Toast.LENGTH_SHORT).show();

    }

    private void cancelAlarm(){
        Intent intent = new Intent(this,AlarmManager.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        if (alarmManager ==null){
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        picker = null;
        binding.selectTime.setText("Select Time");
        Toast.makeText(this, "Alarm Cancelled !", Toast.LENGTH_SHORT).show();

    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(),"MY_Picker");
        picker.addOnPositiveButtonClickListener(view -> {

            if (picker.getHour()>12){
                binding.selectTime.setText( String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM" );
            }
            else {
                binding.selectTime.setText(picker.getHour()+" : "+picker.getMinute() +" AM");
            }

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
            calendar.set(Calendar.MINUTE,picker.getMinute());
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (alarmManager != null){
            binding.selectTime.setText("already time selected");
        }else {
            binding.selectTime.setText("Select Time");
        }

    }
}