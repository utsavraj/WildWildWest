package com.example.wildwildwest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    MediaPlayer music;

    public static String EmailHolder;
    TextView Email, HighScore;
    Button Logout ;
    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(this, R.raw.tumbleweedtown);
        music.start();
        music.setLooping(true);

        Email = (TextView)findViewById(R.id.Email);
        HighScore = (TextView)findViewById(R.id.HighScore);
        Logout = (Button)findViewById(R.id.logout);

        Intent intent = getIntent();

        // Receiving User Email Send By LoginActivity.
        EmailHolder = intent.getStringExtra(LoginActivity.UserEmail);

        dbHelper = new SQLiteHelper(this);
        final Cursor cursor = dbHelper.getUser(EmailHolder);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Email.setText(Email.getText().toString() + cursor.getString(cursor.getColumnIndexOrThrow("name")));
                HighScore.setText(HighScore.getText().toString() + cursor.getString(cursor.getColumnIndexOrThrow("highscore")));

            } }
        Log.d("Creation", DatabaseUtils.dumpCursorToString(cursor ));
        cursor.close();

        dbHelper.close();
        // Adding click listener to Log Out button.
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Finishing current DashBoard activity on button click.
                finish();
                Toast.makeText(MainActivity.this,"Logout Successfully", Toast.LENGTH_LONG).show();
            }
        });


        Button button_duel = findViewById(R.id.Duel);
        button_duel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDuel();
            }
        });

        Button button_practice = findViewById(R.id.Practice);
        button_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPractice();
            }
        });

        Button button_chatroom = findViewById(R.id.ChatRoom);
        button_chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatRoom();
            }
        });

    }

    private void goToChatRoom() {
        music.setLooping(false);
        music.stop();
        Intent intent = new Intent(this, ChatRoomActivity.class);
        startActivity(intent);
    }

    private void goToDuel() {
        music.setLooping(false);
        music.stop();
        Intent intent = new Intent(this, Duel.class);
        startActivity(intent);
    }

    private void goToPractice() {
        music.setLooping(false);
        music.stop();
        Intent intent = new Intent(this, Practice.class);
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

}



