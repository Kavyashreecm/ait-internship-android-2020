package com.capulustech.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentListActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    StudentListAdapter studentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        List<Student> allStudents = Student.getAllStudents(this);

        studentListAdapter = new StudentListAdapter(this, allStudents);
        recyclerView = findViewById(R.id.studentRCV);
        recyclerView.setAdapter(studentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        int id = item.getItemId();

        if (id == R.id.add)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    StudentRegistrationActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.share)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    ShareActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.progress)
        {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            progressDialog.dismiss();
            return true;
        }

        if (id == R.id.video)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    VideoCaptureActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.tts)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    TextToSpeechActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.location)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    LocationActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.web)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    WebViewActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.aadhaar)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    AadhaarActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.speechRecognition)
        {
            Intent intent = new Intent(StudentListActivity.this,
                    SpeechRecognitionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
