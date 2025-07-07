package com.example.africanliteraturelibraryapp.layouts; // <-- ENSURE THIS PACKAGE IS CORRECT

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

/**
 * Activity to demonstrate ConstraintLayout.
 */
public class ConstraintLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Correctly references layout_constraint.xml as per your naming convention
        setContentView(R.layout.layout_constraint);

        Button viewDetailsButton = findViewById(R.id.viewDetailsButton);
        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConstraintLayoutActivity.this, "Viewing details for The Famished Road!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
