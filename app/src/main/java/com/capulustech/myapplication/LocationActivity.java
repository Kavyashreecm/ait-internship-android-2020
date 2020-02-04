package com.capulustech.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

public class LocationActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TextView locationTV = findViewById(R.id.locationTV);
        final Button mapBtn = findViewById(R.id.mapBtn);
        final Button locationBtn = findViewById(R.id.locationBtn);

        locationBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dexter.withActivity(LocationActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                FusedLocation fusedLocation =
                                        new FusedLocation(LocationActivity.this,
                                                false);
                                fusedLocation.onLocReceived(new MyLocListener()
                                {
                                    @Override
                                    public void onLocReceived(final LatLng latLng)
                                    {

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

                                        mapBtn.setOnClickListener(new View.OnClickListener()
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
            }
        });
    }
}
