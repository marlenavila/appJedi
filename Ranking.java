package com.example.marlen.appjedi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;

    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

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

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter();
        recView.setAdapter(myCustomAdapter);
        myCustomAdapter.setData(users);
    }
}
