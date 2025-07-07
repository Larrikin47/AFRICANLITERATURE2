package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

public class SpinnerActivity extends AppCompatActivity {

    private Spinner genreSpinner;
    private TextView selectedGenreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        genreSpinner = findViewById(R.id.genreSpinner);
        selectedGenreTextView = findViewById(R.id.selectedGenreTextView);

        // Sample data for the Spinner
        String[] genres = new String[] {
                "Select a genre", "Fiction", "Non-Fiction", "Poetry", "Drama", "History", "Biography"
        };

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genres);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        genreSpinner.setAdapter(adapter);

        // Set a listener for when an item is selected
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = parent.getItemAtPosition(position).toString();
                if (!selectedGenre.equals("Select a genre")) {
                    selectedGenreTextView.setText("Selected Genre: " + selectedGenre);
                    Toast.makeText(SpinnerActivity.this, "Selected: " + selectedGenre, Toast.LENGTH_SHORT).show();
                } else {
                    selectedGenreTextView.setText("Selected Genre: None");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGenreTextView.setText("Selected Genre: None");
            }
        });
    }
}
