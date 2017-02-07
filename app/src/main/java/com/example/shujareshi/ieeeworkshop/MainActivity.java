package com.example.shujareshi.ieeeworkshop;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

//import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    EditText etKm, etMin;

    Button btnCalc, btnSecAct , btnSensAct;

    TextView tvFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etKm = (EditText) findViewById(R.id.etKm);
        etMin = (EditText) findViewById(R.id.etMin);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        tvFare = (TextView) findViewById(R.id.tvFare);

        //retrive saved data
        SharedPreferences savedPrefs = getPreferences(MODE_PRIVATE);
        float savedkm = savedPrefs.getFloat("km" , 0.0f); // 0 is default value
        int savedmin = savedPrefs.getInt("min" , 0); // 0 is default value

        etKm.setText(String.valueOf(savedkm));
        etMin.setText(String.valueOf(savedmin));

        btnSecAct = (Button) findViewById(R.id.btnSecAct);
        btnSensAct = (Button) findViewById(R.id.btnSensAct);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float km = Float.valueOf(etKm.getText().toString());

                int min = Integer.valueOf(etMin.getText().toString());

                float fare = calculateFare(km , min);

                //SAVING THE DATA TO SHAREDPREFERENCES..only primitive data type scan be stored
                SharedPreferences sPref = getPreferences(MODE_PRIVATE);

                SharedPreferences.Editor prefEditor = sPref.edit();

                prefEditor.putFloat("km", km );
                prefEditor.putInt("min", min);
                prefEditor.putFloat("fare", fare );
                prefEditor.apply();// now it gets write to file

                String dataTosave = "km =" + km
                        +"\n"
                        +"min = " + min
                        +"\n"
                        +"fare = " + fare ;

               try {
                   saveTofile("myFile.txt", "dataTosave");
               }catch ()

                tvFare.setText("Rs. " + String.valueOf(fare));

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Fare")
                        .setMessage("Fare is Rs. " + String.valueOf(fare))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Paid", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Did not pay", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

                new Student().increaseAge().increaseAge().increaseAge();
            }
        });

        btnSecAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondAcitivity.class);
                startActivity(i);
            }
        });

        btnSensAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SensorActivity.class);
                startActivity(i);
            }
        });


    }



    float calculateFare(float km, int min) {
        float fare = 25;

        if (km > 2) {
            fare += (km * 9);
        }

        if (min > 15) {
            fare += (min - 15);
        }

        return fare;
    }

    void saveTofile(String fileName , String data) throws IOException {
       // Log.d(TAG,"save to file " + getFilesDir().getAbsolutePath());
        //                         + getExternalFilesDir(environment.DIRECTORY_MUSIC).getabs....)
        Log.d(TAG,"SAVE TO FILE " + Environment.getExternalStorageDirectory().getAbsolutePath());
        File sdcardDir = Environment.getExternalStorageDirectory();

        File myFile =  new File(sdcardDir, fileName);

        if(sdcardDir.isDirectory()) {
            if (myFile.exists()) {
                myFile.delete();
            }
            myFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();

        }

            FileInputStream fileInputStream = new FileInputStream(myFile);

            byte[] buffer  = new byte[8];
            while(buffer!= null) {
                fileInputStream.read(buffer);
            }
            return null ;

        }
    }


    public class Student {
        String name;
        int year;
        int age = 10;

        Student increaseAge() {
            age++;
            return this;
        }
        Student decreaseAge() {
            age--;
            return this;
        }
    }

    public interface OnAttendanceListener {
        void onAttendance(boolean present);
    }


    public class CollegeStudent implements OnAttendanceListener {

        @Override
        public void onAttendance(boolean present) {

        }
    }


