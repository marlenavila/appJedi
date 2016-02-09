package com.example.marlen.appjedi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    TextView userName, name, points, number_points, address, adrs, notification, notif, pass;
    EditText password;
    Button ok;
    ImageView profPic;
    DbHelper baseDades;
    LinearLayout layout;
    List<Address> l;
    LocationManager lm;
    LocationListener lis;

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
        pass = (TextView) findViewById(R.id.lePass);
        password = (EditText) findViewById(R.id.lePassword);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        profPic = (ImageView) findViewById(R.id.profilePic);
        profPic.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.pass_layout);
        baseDades = new DbHelper(this);

        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("userName"));
        Integer points = bundle.getInt("points");
        number_points.setText(points.toString());
        notif.setText(bundle.getString("notif"));

        //GPS
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lis = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                Geocoder gc = new Geocoder(getApplicationContext());
                try {
                    l = gc.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < l.size(); ++i) {
                    Log.v("LOG", l.get(i).getAddressLine(0).toString());
                    if (i == 0) adrs.setText("");
                    adrs.setText(adrs.getText() + "\n" + l.get(i).getAddressLine(0).toString());
                }
                Log.v("LOG", ((Double) location.getLatitude()).toString());
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lis);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lis);
    }

    @Override
    protected void onPause() {
        lm.removeGpsStatusListener((GpsStatus.Listener) lis);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changePassword:
                layout.setVisibility(View.VISIBLE);
                return true;
            case R.id.log_out:
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profilePic:
                Intent pickAnImage = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickAnImage.setType("image/*");

                startActivityForResult(pickAnImage, 2);
                break;
            case R.id.ok:
                String pass = password.getText().toString();
                baseDades.updatePassword(userName.getText().toString(), pass);
                //Cursor c = baseDades.getUser(name.getText().toString());
                //String polla = c.getString(c.getColumnIndex(baseDades.CN_PASS));
                //Toast.makeText(getApplicationContext(), polla , Toast.LENGTH_SHORT).show();
                layout.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Como en este caso los 3 intents hacen lo mismo, si el estado es correcto recogemos el resultado
        //Aún así comprobamos los request code. Hay que tener total control de lo que hace nuestra app.
        if (resultCode == RESULT_OK) {
            data.getData();
            Uri selectedImage = data.getData();
            Log.v("PICK", "Selected image uri" + selectedImage);
            try {
                profPic.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.v("Result", "Something happened");
        }
    }
}
