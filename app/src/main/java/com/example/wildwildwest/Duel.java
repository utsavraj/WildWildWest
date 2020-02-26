package com.example.wildwildwest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Duel extends AppCompatActivity implements SensorEventListener {

    String Email_Address = MainActivity.EmailHolder;
    private SensorManager sensorManager;
    Sensor accelerometer;


    MediaPlayer gunshot, fire, ready;
    boolean gun_sound = false;

    //Disable/Enable button when neccesary.
    boolean button_timer = true;

    //Checks if there's false start and reset if necessary.
    boolean false_start, false_start_two = true;

    SQLiteHelper dbHelper;

    //If timer_one is empty
    boolean timer_one_empty = true;

    Button button_START, button_RESET;

    ImageView start_position, end_position;

    Handler timerHandler = new Handler();

    TextView Timer_one, Timer_two, player_one;

    double startTime, timeInMilliseconds = 0.00;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel);
        player_one = findViewById(R.id.Player_one);


        dbHelper = new SQLiteHelper(this);
        final Cursor cursor = dbHelper.getUser(Email_Address);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                player_one.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));}}


        button_START = findViewById(R.id.START);
        button_RESET = findViewById(R.id.RESET);

        Timer_one = findViewById(R.id.Timer_one);
        Timer_two = findViewById(R.id.Timer_two);

        gunshot = MediaPlayer.create(this, R.raw.gunshot);
        fire = MediaPlayer.create(this, R.raw.fire);
        ready = MediaPlayer.create(this, R.raw.ready);

        start_position = findViewById(R.id.startposition);
        end_position = findViewById(R.id.endposition);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( Duel.this, (Sensor) accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        button_START.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer();
            }
        });
        button_RESET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

    }


    private void StartTimer() {
            button_START.setEnabled(false);
            button_START.setClickable(false);

            ready.start();
        if(mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null; }

        mCountDownTimer = new CountDownTimer(6050, 2000) {

                public void onTick(long millisUntilFinished) {
                    button_timer = false;
                    button_START.setText(String.valueOf(millisUntilFinished/2000));
                }

                public void onFinish() {
                    button_START.setText("FIRE!");
                    if (Timer_two.getText().toString().equals("0.00s") && !(Timer_two.getText().toString().equals("0.00s")) ) {
                        false_start_two = false;
                    }
                    startTime = SystemClock.uptimeMillis();
                    timerHandler.postDelayed(updateTimerThread, 0);

                    button_timer = true;
                    gun_sound = true;
                    false_start = false;
                    fire.start();
                }
            }.start();
        }



    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            if (timer_one_empty) {
                Timer_one.setText(String.valueOf(timeInMilliseconds/1000)  + "s");}
            else {
                Timer_two.setText(String.valueOf(timeInMilliseconds/1000)  + "s");
            }
            Log.d("Timer", Timer_one.getText().toString());

            timerHandler.postDelayed(this, 1000);
            button_START.setEnabled(false);
            button_START.setClickable(false);
        }
    };



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }


    public void Reset() {
        button_RESET.setEnabled(false);
        button_RESET.setClickable(false);
        button_RESET.setBackgroundColor(Color.parseColor("#555A5E"));
        Timer_one.setText("0.00s");
        Timer_two.setText("0.00s");
        timer_one_empty =  true;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("Duel", "onSensorChanged: X : " + event.values[0] +  " Y: " + event.values[1] + " Z: "+ event.values[2] );


        if(event.values[1] >= -9.82 && event.values[1] <= -9.80 ) {
         {
             if(event.values[0] >= -0.2 && event.values[0] <= 0.2 && event.values[2] >= -0.2 && event.values[2] <= 0.2) {
                 if(button_timer) {
                 button_START.setEnabled(true);
                 button_START.setClickable(true);
                 button_START.setBackgroundColor(Color.parseColor("#00b300"));}
             }
             else {
                 button_START.setBackgroundColor(Color.parseColor("#c02527"));
             }

            }


        }
        else if (false_start || false_start_two) {
            button_START.setText("START");
            if(mCountDownTimer != null) {
                mCountDownTimer.cancel();
                mCountDownTimer = null; }
            button_timer = true;
            button_START.setBackgroundColor(Color.parseColor("#c02527"));

    }
        else {
            button_START.setBackgroundColor(Color.parseColor("#c02527"));
        }

        if (!Timer_two.getText().toString().equals("0.00s") && !Timer_one.getText().toString().equals("0.00s"))
         {
            button_START.setEnabled(false);
            button_START.setClickable(false);
            button_RESET.setEnabled(true);
            button_RESET.setClickable(true);
            button_RESET.setBackgroundColor(Color.parseColor("#414548"));
        }

        if(event.values[0] >= -9.82 && event.values[0] <= -9.80 ) {
            if(event.values[1] >= -0.2 && event.values[1] <= 0.2 && event.values[2] >= -0.2 && event.values[2] <= 0.2) {
                timerHandler.removeCallbacks(updateTimerThread);

                button_START.setText("START");
                button_START.setEnabled(false);
                button_START.setClickable(false);
                if (gun_sound) {
                gunshot.start();
                    gun_sound = false;
                    if(timer_one_empty){
                    dbHelper.updateHighScore(Email_Address, Timer_one.getText().toString());}
                    timer_one_empty = false;
                }

                button_START.setBackgroundColor(Color.parseColor("#c02527"));

            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        dbHelper.close();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

}

