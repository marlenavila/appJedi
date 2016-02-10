package com.example.marlen.appjedi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;
    MyCustomAdapter myCustomAdapter;

    ArrayList<User> users = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.ranking_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.resetDataRanking:
                users = null;
                myCustomAdapter.setData(users);

                return true;
            case R.id.logOutFromRanking:
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
        setContentView(R.layout.activity_ranking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rank);
        setSupportActionBar(toolbar);

        baseDades = new DbHelper(this);
        recView = (RecyclerView)findViewById(R.id.mRecyclerView);
        manager = new LinearLayoutManager(this);

        recView.setLayoutManager(manager);

        Cursor c = baseDades.getAllUsers();
        if(c.moveToFirst()){
            do{
                String userName = c.getString(c.getColumnIndex(baseDades.CN_NAME));
                Integer userPoints = c.getInt(c.getColumnIndex(baseDades.CN_POINTS));
                User newUser = new User();
                newUser.setName(userName);
                newUser.setPoints(userPoints);
                if(newUser.getPoints() != 0){ //no mostra en el ranking els usuaris que encara no han fet cap partida
                    users.add(newUser);
                }
            }while(c.moveToNext());
        }

        myCustomAdapter = new MyCustomAdapter();
        recView.setAdapter(myCustomAdapter);
        myCustomAdapter.setData(users);



    }
}
