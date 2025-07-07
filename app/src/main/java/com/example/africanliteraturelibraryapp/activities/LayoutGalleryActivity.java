package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.layouts.ConstraintLayoutActivity;
import com.example.africanliteraturelibraryapp.layouts.FrameLayoutActivity;
import com.example.africanliteraturelibraryapp.layouts.LinearLayoutActivity;
import com.example.africanliteraturelibraryapp.layouts.RelativeLayoutActivity;
import com.example.africanliteraturelibraryapp.layouts.TableLayoutActivity;
import com.example.africanliteraturelibraryapp.layouts.TabLayoutActivity;


public class LayoutGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_gallery);

        // Initialize buttons
        Button linearLayoutButton = findViewById(R.id.linearLayoutButton);
        Button frameLayoutButton = findViewById(R.id.frameLayoutButton);
        Button relativeLayoutButton = findViewById(R.id.relativeLayoutButton);
        Button tableLayoutButton = findViewById(R.id.tableLayoutButton);
        Button tabLayoutButton = findViewById(R.id.tabLayoutButton);
        Button constraintLayoutButton = findViewById(R.id.constraintLayoutButton);

        // Set click listeners
        linearLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, LinearLayoutActivity.class));
            }
        });

        frameLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, FrameLayoutActivity.class));
            }
        });

        relativeLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, RelativeLayoutActivity.class));
            }
        });

        tableLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, TableLayoutActivity.class));
            }
        });

        tabLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, TabLayoutActivity.class));
            }
        });

        constraintLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutGalleryActivity.this, ConstraintLayoutActivity.class));
            }
        });
    }
}
