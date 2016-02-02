package com.example.marlen.appjedi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MediaPlayer extends AppCompatActivity implements View.OnClickListener {

    Button btPlay, btPause, btStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        btPlay = (Button)findViewById(R.id.bt_Play);
        btPause = (Button)findViewById(R.id.bt_Pause);
        btStop = (Button)findViewById(R.id.bt_Stop);

        btPlay.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_Play:
                break;
            case R.id.bt_Pause:
                break;
            case R.id.bt_Stop:
                break;
        }

    }
}
