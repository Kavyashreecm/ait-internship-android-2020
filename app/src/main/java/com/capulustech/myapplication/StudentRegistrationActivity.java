package com.capulustech.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        final EditText branchET = findViewById(R.id.branchET);
        final EditText sectionET = findViewById(R.id.sectionET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);

        Button registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameET.getText().toString();
                String branch = branchET.getText().toString();
                String usn = usnET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
                String section = sectionET.getText().toString();

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
