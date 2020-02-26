package com.example.wildwildwest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;


import androidx.appcompat.app.AppCompatActivity;


public class Practice extends AppCompatActivity implements SensorEventListener {

    //TextView oren_test;
    private static final String TAG = "Practice";
    private SensorManager sensorManager;
    Sensor accelerometer;

    String temp;
    String Email_Address = MainActivity.EmailHolder;


    MediaPlayer gunshot, fire, ready;
    boolean gun_sound = false;
    boolean button_timer = true;
    boolean false_start = true;

    Button button_START;

    ImageView start_position, end_position;

    Handler timerHandler = new Handler();

    TextView Timer;
    SQLiteHelper dbHelper;

    double startTime, timeInMilliseconds = 0.00;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);


        button_START = findViewById(R.id.START);
        Timer = findViewById(R.id.Timer);

        gunshot = MediaPlayer.create(this, R.raw.gunshot);
        fire = MediaPlayer.create(this, R.raw.fire);
        ready = MediaPlayer.create(this, R.raw.ready);

        start_position = findViewById(R.id.startposition);
        end_position = findViewById(R.id.endposition);

        //Checks to see if your phone is in landscape or portrait
//        oren_test = findViewById(R.id.testID);
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            oren_test.setText("LANDSCAPE");
//            oren_test.setTextColor(Color.argb(100, 124, 252, 0));
//        }
//        else {
//            oren_test.setText("PORTRAIT");
//        }

        Log.d(TAG, "onCreate: Initializing Sensor Services");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( Practice.this, (Sensor) accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Accelerometer has been registered");

        button_START.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer();
            }
        });

        dbHelper = new SQLiteHelper(this);

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
                    Timer.setText("0.00s");
                }

                public void onFinish() {
                    button_START.setText("FIRE!");
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
            Timer.setText(String.valueOf(timeInMilliseconds/1000)  + "s");
            timerHandler.postDelayed(this, 1000);
            button_START.setEnabled(false);
            button_START.setClickable(false);
        }
    };



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: X : " + event.values[0] +  " Y: " + event.values[1] + " Z: "+ event.values[2] );


        if(event.values[1] >= -9.82 && event.values[1] <= -9.80 ) {
         {
             if(event.values[0] >= -0.2 && event.values[0] <= 0.2 && event.values[2] >= -0.2 && event.values[2] <= 0.2) {
                 if(button_timer) {
                 button_START.setEnabled(true);
                 button_START.setClickable(true);
                 button_START.setBackgroundColor(Color.parseColor("#00b300"));}
             }

            }



        }
        else if (false_start) {
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

        if(event.values[0] >= -9.82 && event.values[0] <= -9.80 ) {
            if(event.values[1] >= -0.2 && event.values[1] <= 0.2 && event.values[2] >= -0.2 && event.values[2] <= 0.2) {
                timerHandler.removeCallbacks(updateTimerThread);
                Log.d("Timer", Timer.getText().toString());
                button_START.setText("START");
                button_START.setEnabled(false);
                button_START.setClickable(false);
                if (gun_sound) {
                gunshot.start();
                    dbHelper.updateHighScore(Email_Address, Timer.getText().toString());
                    gun_sound = false;
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

