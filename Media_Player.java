package com.example.marlen.appjedi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

public class Media_Player extends AppCompatActivity implements View.OnClickListener {

    Button btPlay, btPause, btStop;

    MediaPlayer mp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.player_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOutFromMediaPlayer:
                SharedPreferences culo = getSharedPreferences("culo", MODE_PRIVATE);
                SharedPreferences.Editor editor = culo.edit();
                editor.putString("userName", null); //buido el sharepreferences
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish(); //para q cuando tire atrás no vuelva al user_profile
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        setSupportActionBar(toolbar);

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
