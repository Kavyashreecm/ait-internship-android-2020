package com.capulustech.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
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
        final Button videoBtn = findViewById(R.id.captureVideoBtn);
        final ImageView profileIV = findViewById(R.id.ivRegLogo);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.demo);
        profileIV.startAnimation(animation);
        shareBtn.startAnimation(animation);
        speakBtn.startAnimation(animation);
        videoBtn.startAnimation(animation);
        registerBtn.startAnimation(animation);


        videoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(videoIntent, 1111);
            }
        });

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

                Student.addStudent(StudentRegistrationActivity.this, student);

                Toast.makeText(StudentRegistrationActivity.this,
                        "Student Added to Database", Toast.LENGTH_SHORT).show();


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

        if (requestCode == 1111 && resultCode == RESULT_OK)
        {
            Uri videoUri = data.getData();
            VideoView videoView = findViewById(R.id.videoView);
            videoView.setVideoURI(videoUri);
            videoView.start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings)
        {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

//        if (id == R.id.alert)
//        {
//            showAlertDialog();
//            return true;
//        }
//
//        if (id == R.id.datePicker)
//        {
//            showDatePicker();
//            return true;
//        }
        if (id == R.id.studentList)
        {
            Intent intent = new Intent(this, StudentListActivity.class);
            startActivity(intent);
            return true;
        }
//
        if (id == R.id.logout)
        {
            Toast.makeText(this, "Logout Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showAlertDialog()
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Title");
        alertBuilder.setMessage("This is a sample Message");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(StudentRegistrationActivity.this, "OK Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(StudentRegistrationActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.create().show();
    }


    public void showDatePicker()
    {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
