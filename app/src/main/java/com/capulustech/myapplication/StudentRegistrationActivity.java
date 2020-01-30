package com.capulustech.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;

public class StudentRegistrationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        final EditText nameET = findViewById(R.id.nameET);
        final EditText usnET = findViewById(R.id.usnET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);
        final Spinner sectionSpn=findViewById(R.id.sectionSpn);
        final Spinner branchSpn=findViewById(R.id.branchSpn);


        Dexter.withActivity(StudentRegistrationActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        FusedLocation fusedLocation =
                                new FusedLocation(StudentRegistrationActivity.this,
                                        false);
                        fusedLocation.onLocReceived(new MyLocListener()
                        {
                            @Override
                            public void onLocReceived(LatLng latLng)
                            {
                                Toast.makeText(StudentRegistrationActivity.this,
                                        "Location : " + latLng.latitude + "," + latLng.longitude,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token)
                    {

                    }
                })
                .check();




        //     final String selectedSec=sectionSpn.getSelectedItem().toString();
//
//        sectionSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//
//                 selectedSec=sectionSpn.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });



        Button registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameET.getText().toString();
                String branch = branchSpn.getSelectedItem().toString();
                String usn = usnET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
                //String section = sectionET.getText().toString();
                 String section=sectionSpn.getSelectedItem().toString();

                Student student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;


                /*
                Intent intent = new Intent();
                intent.putExtra("student", student);
                startActivity(intent);
                */

                //in StudentDetailsActivity
                //Student student1 = (Student) getIntent().getSerializableExtra("student");


            }
        });

    }


}
