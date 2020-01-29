package com.capulustech.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class StudentRegistration extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        final EditText nameEt = findViewById(R.id.nameET);
        final EditText usnEt = findViewById(R.id.usnET);
        final EditText branchEt = findViewById(R.id.branchET);
        final EditText sectionEt = findViewById(R.id.sectionET);
        final EditText mobileNumberEt = findViewById(R.id.phoneET);
        Button registerBtn = findViewById(R.id.submitBtn);
        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameEt.getText().toString();
                String branch = branchEt.getText().toString();
                String usn = usnEt.getText().toString();
                String mobilenumber = mobileNumberEt.getText().toString();
                String section = sectionEt.getText().toString();

                Student student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobilenumber;
                student.section = section;


                Intent intent = new Intent();
                intent.putExtra("student", student);
                startActivity(intent);

                //in StudentDetailsActivity
                Student student1 = (Student) getIntent().getSerializableExtra("student");


            }
        });


    }
}
