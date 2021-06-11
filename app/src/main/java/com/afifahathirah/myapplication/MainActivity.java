package com.afifahathirah.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText weight;
    EditText height;
    Button btnCalc;
    TextView result;
    Float Weight, Height;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        result = (TextView) findViewById(R.id.result);

        btnCalc.setOnClickListener(this);

        Weight = Float.valueOf(0);
        Height = Float.valueOf(0);

        //save data
        sharedPref = this.getSharedPreferences("W", Context.MODE_PRIVATE);
        sharedPref = this.getSharedPreferences("H", Context.MODE_PRIVATE);

        Weight = sharedPref.getFloat("W", 0);
        Height = sharedPref.getFloat("H", 0);

        weight.setText("" +Weight);
        height.setText("" +Height);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about :

                Intent intent = new Intent(this, AboutActivity.class );
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnCalc:
                try {
                    float w = Float.parseFloat(weight.getText().toString());
                    float h = Float.parseFloat(height.getText().toString());

                    double calculate1 = w / (h * h);
                    //for move decimal point
                    double calculate2 = (calculate1*10000.0);
                    //define format in two decimal point
                    DecimalFormat df = new DecimalFormat("#.##");
                    result.setText("Here is your BMI: " + df.format(calculate2));

                    //display health risk and bmi desc
                    if(calculate2 <= 18.4){
                        Toast.makeText(this, " BMI category: Under weight \n Health Risk: Malnutrition risk",Toast.LENGTH_SHORT).show();
                    }
                    else if (calculate2 >= 18.5 && calculate2 <= 24.9){
                        Toast.makeText(this, " BMI category: Normal weight \n Health Risk: Low risk",Toast.LENGTH_SHORT).show();
                    }
                    else if(calculate2 >= 25.0 && calculate2 <= 29.9){
                        Toast.makeText(this, " BMI category: Over weight \n Health Risk: Enhanced risk",Toast.LENGTH_SHORT).show();
                    }
                    else if(calculate2 >= 30.0 && calculate2 <= 34.9){
                        Toast.makeText(this, " BMI category: Moderately obese \n Health Risk: Medium risk",Toast.LENGTH_SHORT).show();
                    }
                    else if(calculate2 >= 35.0 && calculate2 <= 39.9){
                        Toast.makeText(this, " BMI category: Severely obese \n Health Risk: High risk",Toast.LENGTH_SHORT).show();
                    }
                    else if (calculate2 >= 40.0){
                        Toast.makeText(this, " BMI category: Very severely obese \n Health Risk: Very High risk",Toast.LENGTH_SHORT).show();
                    }

                    // saving/write data
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat("W", w);
                    editor.putFloat("H", h);
                    editor.apply();

                }catch(Exception ex){
                    Toast.makeText(this, "Please fill the box above first",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}