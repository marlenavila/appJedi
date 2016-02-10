package com.example.marlen.appjedi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btMem,btRank,btLog,btMed,btCalc;
    DbHelper baseDades;

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

        baseDades = new DbHelper(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.bt_Memory:
                intent = new Intent(getApplicationContext(),Memory.class);
                startActivity(intent);
              /* Cursor c = baseDades.getUser("Marlen");
                if(c.moveToFirst()){
                    Integer aux = c.getInt(c.getColumnIndex(baseDades.CN_POINTS));
                    aux +=2;
                    baseDades.updatePoints("Marlen", aux);
                }*/
                break;
            case R.id.bt_Ranking:
                intent = new Intent(getApplicationContext(),Ranking.class);
                startActivity(intent);
                break;
            case R.id.bt_Login:
                SharedPreferences culo = getSharedPreferences("culo", Context.MODE_PRIVATE); //instancio el culo del login
                String interiorCulo = culo.getString("userName",null);
                if(interiorCulo == null){ //sino hi ha cap user que hagi fet login, me'n vaig a la pantalla de login
                    intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
                else{
                    //si me'n vaig a la pantalla del perfil directe haig d fer un bundle per pasar-li totes les dades (database instance needed)
                    Cursor c1 = baseDades.getUser(interiorCulo);
                    c1.moveToFirst(); //c1 apunta al principi de la taula, moveTOFirst needed pqe apunti a la primera pos on esta el nom i no peti
                    Bundle bundle = new Bundle();
                    bundle.putString("userName",interiorCulo);
                    bundle.putInt("points", c1.getInt(c1.getColumnIndex(baseDades.CN_POINTS)));
                    bundle.putString("pic",c1.getString(c1.getColumnIndex(baseDades.CN_PIC)));
                    intent = new Intent(getApplicationContext(),UserProfile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            case R.id.bt_Music:
                intent = new Intent(getApplicationContext(),Media_Player.class);
                startActivity(intent);
                break;
            case R.id.bt_Calc:
                intent = new Intent(getApplicationContext(),Calculator.class);
                startActivity(intent);
                break;
        }

    }
}
