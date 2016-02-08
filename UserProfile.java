package com.example.marlen.appjedi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    TextView userName, name, points, number_points, address, adrs, notification, notif, pass;
    EditText password;
    Button ok;
    ImageView profPic;
    DbHelper baseDades;
    LinearLayout layout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (TextView) findViewById(R.id.user_txt);
        name = (TextView) findViewById(R.id.userName_txt);
        points = (TextView) findViewById(R.id.attempts_txt);
        number_points = (TextView) findViewById(R.id.points_txt);
        address = (TextView) findViewById(R.id.address);
        adrs = (TextView) findViewById(R.id.address_txt);
        notification = (TextView) findViewById(R.id.notif);
        notif = (TextView) findViewById(R.id.notif_txt);
        pass = (TextView)findViewById(R.id.lePass);
        password = (EditText)findViewById(R.id.lePassword);
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(this);
        profPic = (ImageView) findViewById(R.id.profilePic);
        profPic.setOnClickListener(this);
        layout = (LinearLayout)findViewById(R.id.pass_layout);
        baseDades = new DbHelper(this);

        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("userName"));
        Integer points = bundle.getInt("points");
        number_points.setText(points.toString());
        notif.setText(bundle.getString("notif"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changePassword:
                layout.setVisibility(View.VISIBLE);
                return true;
            case R.id.log_out:
                SharedPreferences culo = getSharedPreferences("culo",MODE_PRIVATE);
                SharedPreferences.Editor editor = culo.edit();
                editor.putString("userName", null); //buido el sharepreferences
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish(); //para q cuando tire atrás no vuelva al user_profile
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profilePic:

                break;
            case R.id.ok:
                String pass = password.getText().toString();
                baseDades.updatePassword(userName.getText().toString(),pass);
                //Cursor c = baseDades.getUser(name.getText().toString());
                //String polla = c.getString(c.getColumnIndex(baseDades.CN_PASS));
                //Toast.makeText(getApplicationContext(), polla , Toast.LENGTH_SHORT).show();
                layout.setVisibility(View.GONE);
                break;
        }

    }
}
