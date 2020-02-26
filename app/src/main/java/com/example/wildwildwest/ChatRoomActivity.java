package com.example.wildwildwest;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ChatRoomActivity extends AppCompatActivity {
    MediaPlayer music;

    public static String EmailHolder;
    TextView Explaination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        Explaination = findViewById(R.id.Explain);
        Button button_server = findViewById(R.id.ServerRoom);
        button_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToServerRoom();
            }
        });

        Button button_client = findViewById(R.id.ClientRoom);
        button_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClientRoom();
            }
        });


    }

    private void goToServerRoom() {
        Intent intent = new Intent(this, Server.class);
        startActivity(intent);
    }

    private void goToClientRoom() {
        Intent intent = new Intent(this, Client.class);
        startActivity(intent);
    }


    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

}



