package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

public class AutoCompleteActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private TextView selectedItemTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        selectedItemTextView = findViewById(R.id.selectedItemTextView);

        // Sample data for auto-completion
        String[] bookTitles = new String[] {
                "Things Fall Apart", "Half of a Yellow Sun", "Purple Hibiscus",
                "Weep Not, Child", "The River Between", "A Grain of Wheat",
                "Petals of Blood", "Devil on the Cross", "The Famished Road",
                "So Long a Letter", "God's Bits of Wood", "Nervous Conditions",
                "House of Stone", "Americanah", "Homegoing"
        };

        // Create an ArrayAdapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, bookTitles);
        autoCompleteTextView.setAdapter(adapter);

        // Set a listener for when an item is selected
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedBook = (String) parent.getItemAtPosition(position);
            selectedItemTextView.setText("Selected Book: " + selectedBook);
            Toast.makeText(AutoCompleteActivity.this, "Selected: " + selectedBook, Toast.LENGTH_SHORT).show();
        });
    }
}
