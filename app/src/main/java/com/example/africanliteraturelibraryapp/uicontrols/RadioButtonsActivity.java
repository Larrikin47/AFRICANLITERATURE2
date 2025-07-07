package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

public class RadioButtonsActivity extends AppCompatActivity {

    private RadioGroup readingFormatRadioGroup;
    private TextView selectedFormatTextView;
    private Button submitFormatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_buttons); // Correctly references activity_radio_buttons.xml

        readingFormatRadioGroup = findViewById(R.id.readingFormatRadioGroup);
        selectedFormatTextView = findViewById(R.id.selectedFormatTextView);
        submitFormatButton = findViewById(R.id.submitFormatButton);

        submitFormatButton.setOnClickListener(v -> {
            int selectedId = readingFormatRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedFormat = selectedRadioButton.getText().toString();
                selectedFormatTextView.setText("Selected Format: " + selectedFormat);
                Toast.makeText(RadioButtonsActivity.this, "You chose: " + selectedFormat, Toast.LENGTH_SHORT).show();
            } else {
                selectedFormatTextView.setText("Selected Format: None");
                Toast.makeText(RadioButtonsActivity.this, "Please select a format.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
