package com.example.marlen.appjedi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

public class Media_Player extends AppCompatActivity implements View.OnClickListener {

    Button btPlay, btPause, btStop;

    MediaPlayer mp;

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

        mp = MediaPlayer.create(this, R.raw.song);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_Play:
                if(mp == null)
                    mp = MediaPlayer.create(this,R.raw.song);
                mp.start();
                break;
            case R.id.bt_Pause:
                if(mp != null)
                    mp.pause();
                break;
            case R.id.bt_Stop:
                if(mp == null) break;
                mp.stop();
                mp.release();
                mp = null;
                break;
        }

    }
}
