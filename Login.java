package com.example.marlen.appjedi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btSignIn, btSignUp;
    EditText user, password;
    DbHelper baseDades;
    SharedPreferences culo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.user_text);
        password = (EditText) findViewById(R.id.pass_text);

        btSignIn = (Button) findViewById(R.id.bt_SignIn);
        btSignUp = (Button) findViewById(R.id.bt_SingUp);

        btSignIn.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        baseDades = new DbHelper(this);

        culo = getSharedPreferences("culo", Context.MODE_PRIVATE); //nmes jo puc consultar les preferencies
    }

    public void addUser(View v) {
        ContentValues values = new ContentValues();
        values.put(baseDades.CN_NAME, String.valueOf(user.getText())); //content values used to modify the database
        values.put(baseDades.CN_PASS, String.valueOf(password.getText()));
        SharedPreferences.Editor editor = culo.edit();

        Bundle holis = new Bundle();//bundle to pass info to another activity with an intent (user profile in this case)
        holis.putString("userName", user.getText().toString());
        holis.putInt("points", 0); //new user-->0 points
        holis.putString("notif", "--");

        editor.putString("userName", user.getText().toString()); //afegeixo user al sharedpreference
        editor.commit(); //los subo al culo

        Cursor c = baseDades.getUser(user.getText().toString());
        if (c.moveToFirst())
            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
        else {
            baseDades.createUser(values, "User");
            Toast.makeText(getApplicationContext(), "Insertion done", Toast.LENGTH_SHORT).show();
            user.setText("");
            password.setText("");
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            intent.putExtras(holis);
            startActivity(intent);
        }
    }

    public void login(View v) {
        Cursor c = baseDades.getUser(user.getText().toString());
        if (c.moveToFirst()) {
            int col = c.getColumnIndex(baseDades.CN_PASS);
            Log.v("col", col + "");
            String x = c.getString(col);
            Log.v("CNPASS", x);
            if (!password.getText().toString().equals(x)) {
                Toast.makeText(getApplicationContext(), "incorrect password " + c.getString(c.getColumnIndex(baseDades.CN_PASS)), Toast.LENGTH_SHORT).show();
            } else { //entrar a l'activity del profile
                SharedPreferences.Editor editor = culo.edit();
                editor.putString("userName", user.getText().toString()); //afegeixo user al sharedpreference
                editor.apply(); //los subo al culo
                Bundle holis = new Bundle();//bundle to pass info to another activity with an intent (user profile in this case)
                holis.putString("userName", user.getText().toString());
                holis.putInt("points", c.getInt(c.getColumnIndex(baseDades.CN_POINTS))); //existing user --> x points
                holis.putString("notif", c.getString(c.getColumnIndex(baseDades.CN_NOTIFICATION)));
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.putExtras(holis);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "user not exists", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_SignIn:
                login(v);
                break;
            case R.id.bt_SingUp:
                addUser(v);
                break;
        }

    }
}
