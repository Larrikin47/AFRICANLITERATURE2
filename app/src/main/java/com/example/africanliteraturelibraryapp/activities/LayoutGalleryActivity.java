package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.activities.layoutviews.FrameLayoutActivity;
import com.example.africanliteraturelibraryapp.activities.layoutviews.LinearLayoutActivity;
import com.example.africanliteraturelibraryapp.activities.layoutviews.RelativeLayoutActivity;
import com.example.africanliteraturelibraryapp.activities.layoutviews.TableLayoutActivity;
import com.example.africanliteraturelibraryapp.activities.layoutviews.TabLayoutActivity;

public class LayoutGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_gallery);

        // Set up click listeners for each layout demonstration button
        findViewById(R.id.btnLinearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, LinearLayoutActivity.class));
            }
        });

        findViewById(R.id.btnFrameLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, FrameLayoutActivity.class));
            }
        });

        findViewById(R.id.btnRelativeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, RelativeLayoutActivity.class));
            }
        });

        findViewById(R.id.btnTableLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, TableLayoutActivity.class));
            }
        });

        findViewById(R.id.btnTabLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, TabLayoutActivity.class));
            }
        });
    }
}
