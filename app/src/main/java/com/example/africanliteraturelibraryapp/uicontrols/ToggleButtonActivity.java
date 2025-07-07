package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

public class ToggleButtonActivity extends AppCompatActivity {

    private ToggleButton notificationToggleButton;
    private TextView toggleStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_button); // Correctly references activity_toggle_button.xml

        notificationToggleButton = findViewById(R.id.notificationToggleButton);
        toggleStatusTextView = findViewById(R.id.toggleStatusTextView);

        // Set initial status text
        if (notificationToggleButton.isChecked()) {
            toggleStatusTextView.setText("Notifications are ON");
        } else {
            toggleStatusTextView.setText("Notifications are OFF");
        }

        notificationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleStatusTextView.setText("Notifications are ON");
                    Toast.makeText(ToggleButtonActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    toggleStatusTextView.setText("Notifications are OFF");
                    Toast.makeText(ToggleButtonActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
