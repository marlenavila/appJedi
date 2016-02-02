package com.example.marlen.appjedi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btMem,btRank,btLog,btMed,btCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btMem = (Button)findViewById(R.id.bt_Memory);
        btRank = (Button)findViewById(R.id.bt_Ranking);
        btLog = (Button)findViewById(R.id.bt_Login);
        btMed = (Button)findViewById(R.id.bt_Music);
        btCalc = (Button)findViewById(R.id.bt_Calc);

        btMem.setOnClickListener(this);
        btRank.setOnClickListener(this);
        btLog.setOnClickListener(this);
        btMed.setOnClickListener(this);
        btCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_Memory:
                Intent intent = new Intent(getApplicationContext(),Memory.class);
                startActivity(intent);
                break;
            case R.id.bt_Ranking:
                Intent intent1 = new Intent(getApplicationContext(),Ranking.class);
                startActivity(intent1);
                break;
            case R.id.bt_Login:
                Intent intent2 = new Intent(getApplicationContext(),Login.class);
                startActivity(intent2);
                break;
            case R.id.bt_Music:
                Intent intent3 = new Intent(getApplicationContext(),MediaPlayer.class);
                startActivity(intent3);
                break;
            case R.id.bt_Calc:
                Intent intent4 = new Intent(getApplicationContext(),Calculator.class);
                startActivity(intent4);
                break;
        }

    }
}
