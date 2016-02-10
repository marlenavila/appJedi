package com.example.marlen.appjedi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    Button btAc, btC, btDel, btAns, bt7, bt8, bt9, btDiv, bt4, bt5, bt6, btMult, bt1, bt2, bt3, btRest, btDec, bt0;
    Button btIgual, btSum;
    TextView result;
    String s, op;
    Double num1, num2, res;

    //TODO La calculadora está como el culo, sólo está lo que hice cuando la empezamos en el curso xd
    //TODO he ido haciendo lo demás (o intentándolo) y he dejado esto para el final.. pero el dialer
    //TODO y el internete si que funcan (cuando pongáis en horizontal la pantalla se va a ver mal, y los
    //TODO resultados no se guardaran pqe no he hecho el saveInstances que me acabo de acordar ahora xd
    //TODO podría intentarlo ya que es un momento, pero como la calculadora está a medias no tiene sentido que guarde nada xD

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.calc_options, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calc);
        setSupportActionBar(toolbar);

        btAc = (Button) findViewById(R.id.ac_bt);
        btC = (Button) findViewById(R.id.c_bt);
        btDel = (Button) findViewById(R.id.del_bt);
        btAns = (Button) findViewById(R.id.ans_bt);
        bt7 = (Button) findViewById(R.id.bt_7);
        bt8 = (Button) findViewById(R.id.bt_8);
        bt9 = (Button) findViewById(R.id.bt_9);
        btDiv = (Button) findViewById(R.id.div);
        bt4 = (Button) findViewById(R.id.bt_4);
        bt5 = (Button) findViewById(R.id.bt_5);
        bt6 = (Button) findViewById(R.id.bt_6);
        btMult = (Button) findViewById(R.id.mult);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        bt3 = (Button) findViewById(R.id.bt_3);
        btRest = (Button) findViewById(R.id.rest);
        btDec = (Button) findViewById(R.id.dec);
        bt0 = (Button) findViewById(R.id.bt_0);
        btIgual = (Button) findViewById(R.id.igual);
        btSum = (Button) findViewById(R.id.sum);
        result = (TextView) findViewById(R.id.result);

        btAc.setOnClickListener(this);
        btC.setOnClickListener(this);
        btDel.setOnClickListener(this);
        btAns.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        btDiv.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        btMult.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        btRest.setOnClickListener(this);
        btDec.setOnClickListener(this);
        bt0.setOnClickListener(this);
        btIgual.setOnClickListener(this);
        btSum.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.chooseToast:
                Toast.makeText(getApplicationContext(), "Toast notifications selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.chooseStatus:
                Toast.makeText(getApplicationContext(), "Status notifications selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.internete:
                intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://www.youtube.com/watch?v=OgIRAjnnJzI";
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            case R.id.dialer:
                intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
                return true;
            case R.id.logOutFromCalc:
                SharedPreferences culo = getSharedPreferences("culo", MODE_PRIVATE);
                SharedPreferences.Editor editor = culo.edit();
                editor.putString("userName", null); //buido el sharepreferences
                editor.apply();
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish(); //para q cuando tire atrás no vuelva al user_profile
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        s = (String)result.getText();
        switch (v.getId()) {
            case R.id.ac_bt:
               // num1 = 0.0;
               // num2 = 0.0;

                break;
            case R.id.c_bt:
                //num2 = 0.0;
                break;
            case R.id.del_bt:
                break;
            case R.id.ans_bt:
                break;
            case R.id.bt_7:
                s += "7";
                result.setText(s);
                break;
            case R.id.bt_8:
                s += "8";
                result.setText(s);
                break;
            case R.id.bt_9:
                s += "9";
                result.setText(s);
                break;
            case R.id.div:
                op = "/";
                num1 = Double.parseDouble((String)result.getText());
                result.setText("");
                result.setHint(num1.toString());
                break;
            case R.id.bt_4:
                s += "4";
                result.setText(s);
                break;
            case R.id.bt_5:
                s += "5";
                result.setText(s);
                break;
            case R.id.bt_6:
                s += "6";
                result.setText(s);
                break;
            case R.id.mult:
                op = "x";
                num1 = Double.parseDouble((String)result.getText());
                result.setText("");
                result.setHint(num1.toString());
                break;
            case R.id.bt_1:
                s += "1";
                result.setText(s);
                break;
            case R.id.bt_2:
                s += "2";
                result.setText(s);
                break;
            case R.id.bt_3:
                s += "3";
                result.setText(s);
                break;
            case R.id.rest:
                op = "-";
                num1 = Double.parseDouble((String)result.getText());
                result.setText("");
                result.setHint(num1.toString());
                break;
            case R.id.dec:
                s += ".";
                result.setText(s);
                break;
            case R.id.bt_0:
                s += "0";
                result.setText(s);
                break;
            case R.id.sum:
                op = "+";
                num1 = Double.parseDouble((String)result.getText());
                result.setText("");
                result.setHint(num1.toString());
                break;
            case R.id.igual:
                num2 = Double.parseDouble((String)result.getText());
                if(op.equals("+")){
                    res = num1+num2;
                }
                else if(op.equals("-")){
                    res = num1-num2;
                }
                else if(op.equals("x")){
                    res = num1*num2;
                }
                else{
                    if(num2 == 0){
                        res = 0.0;
                        Toast.makeText(getApplicationContext(), "Division by zero --> BOOM!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        res = num1/num2;
                }
                num1 = res;
                result.setText("");
                result.setHint(res.toString());
                break;
        }

    }
}
