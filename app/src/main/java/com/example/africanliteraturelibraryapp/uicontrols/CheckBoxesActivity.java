package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

public class CheckBoxesActivity extends AppCompatActivity {

    private CheckBox checkboxFiction;
    private CheckBox checkboxPoetry;
    private CheckBox checkboxHistory;
    private Button showInterestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_boxes); // Correctly references activity_check_boxes.xml

        checkboxFiction = findViewById(R.id.checkboxFiction);
        checkboxPoetry = findViewById(R.id.checkboxPoetry);
        checkboxHistory = findViewById(R.id.checkboxHistory);
        showInterestsButton = findViewById(R.id.showInterestsButton);

        showInterestsButton.setOnClickListener(v -> {
            StringBuilder result = new StringBuilder("Your interests: ");
            if (checkboxFiction.isChecked()) {
                result.append("Fiction, ");
            }
            if (checkboxPoetry.isChecked()) {
                result.append("Poetry, ");
            }
            if (checkboxHistory.isChecked()) {
                result.append("History, ");
            }

            if (result.toString().endsWith(", ")) {
                result.setLength(result.length() - 2); // Remove trailing comma and space
            } else if (result.toString().equals("Your interests: ")) {
                result.append("None selected.");
            }

            Toast.makeText(CheckBoxesActivity.this, result.toString(), Toast.LENGTH_LONG).show();
        });
    }
}
