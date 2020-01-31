package com.capulustech.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;
import java.util.Locale;

public class StudentRegistrationActivity extends AppCompatActivity
{
    Student student;
    TextToSpeech textToSpeech;
    boolean isSpeaking;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        final EditText nameET = findViewById(R.id.nameET);
        final EditText usnET = findViewById(R.id.usnET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);
        final Spinner sectionSpn = findViewById(R.id.sectionSpn);
        final Spinner branchSpn = findViewById(R.id.branchSpn);
        final TextView locationTV = findViewById(R.id.locationTV);
        Button registerBtn = findViewById(R.id.registerBtn);
        final Button shareBtn = findViewById(R.id.shareBtn);
        final Button speakBtn = findViewById(R.id.speakBtn);
        final ImageView profileIV = findViewById(R.id.ivRegLogo);

        speakBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameET.getText().toString();
                String branch = branchSpn.getSelectedItem().toString();
                String usn = usnET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
                //String section = sectionET.getText().toString();
                String section = sectionSpn.getSelectedItem().toString();

                student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;

                String message = "Name: " + student.name + "\n"
                        + "USN: " + student.usn + "\n"
                        + "Branch: " + student.branch + "\n"
                        + "Section: " + student.section + "\n"
                        + "Mobile Number: " + student.mobileNumber;

                speak(message);



            }
        });

        profileIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(StudentRegistrationActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(intent, 6789);
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

            }
        });

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
                String section = sectionSpn.getSelectedItem().toString();

                student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;



            }
        });



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
                            public void onLocReceived(final LatLng latLng)
                            {
                                /*Toast.makeText(StudentRegistrationActivity.this,
                                        "Location : " + latLng.latitude + "," + latLng.longitude,
                                        Toast.LENGTH_LONG).show();*/

                                locationTV.setText("Location : " + latLng.latitude + "," + latLng.longitude);




                                locationTV.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        Uri uri = Uri.parse("http://maps.google.com/maps?q=" +
                                                latLng.latitude + "," + latLng.longitude);

                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);
                                    }
                                });


                                shareBtn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {

                                        String name = nameET.getText().toString();
                                        String branch = branchSpn.getSelectedItem().toString();
                                        String usn = usnET.getText().toString();
                                        String mobileNumber = mobileNumberET.getText().toString();
                                        //String section = sectionET.getText().toString();
                                        String section = sectionSpn.getSelectedItem().toString();

                                        student = new Student();
                                        student.name = name;
                                        student.branch = branch;
                                        student.usn = usn;
                                        student.mobileNumber = mobileNumber;
                                        student.section = section;

                                        String message = "Name: " + student.name + "\n"
                                                + "USN: " + student.usn + "\n"
                                                + "Branch: " + student.branch + "\n"
                                                + "Section: " + student.section + "\n"
                                                + "Mobile Number: " + student.mobileNumber + "\n"
                                                + "This is my Location: \n" + "http://maps.google.com/maps?q="
                                                + latLng.latitude + "," + latLng.longitude;

                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                                        sendIntent.setType("text/plain");
                                        startActivity(sendIntent);


                                    }
                                });


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




        final ImageView photoIV = findViewById(R.id.ivRegLogo);
        photoIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(StudentRegistrationActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                                startActivityForResult(intent, 6789);
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
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6789 && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView iv = findViewById(R.id.ivRegLogo);
            iv.setImageBitmap(bitmap);
        }
    }

    public void speak(final String message)
    {

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate(1f);
                    textToSpeech.setPitch(1f);
                    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);

                }
            }
        });

    }
}
