package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.services.BookService;
import com.example.africanliteraturelibraryapp.services.BookSyncService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView searchAutoCompleteTextView;
    private Spinner genreSpinner;
    private Button searchButton;
    private Button viewBooksButton;
    private Button viewLayoutsButton;
    private Button startBookServiceButton;
    private Button startSyncServiceButton;
    private Button viewWebViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchAutoCompleteTextView = findViewById(R.id.searchAutoCompleteTextView);
        genreSpinner = findViewById(R.id.genreSpinner);
        searchButton = findViewById(R.id.searchButton);
        viewBooksButton = findViewById(R.id.viewBooksButton);
        viewLayoutsButton = findViewById(R.id.viewLayoutsButton);
        startBookServiceButton = findViewById(R.id.startBookServiceButton);
        startSyncServiceButton = findViewById(R.id.startSyncServiceButton);
        viewWebViewButton = findViewById(R.id.viewWebViewButton);

        // Setup AutoCompleteTextView with some sample suggestions
        String[] bookTitles = {"Things Fall Apart", "Half of a Yellow Sun", "The Joys of Motherhood", "Purple Hibiscus"};
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, bookTitles);
        searchAutoCompleteTextView.setAdapter(searchAdapter);

        // Setup Spinner with genres
        List<String> genres = Arrays.asList("All Genres", "Fiction", "Non-Fiction", "Poetry", "Drama", "Historical");
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);

        // Set up button click listeners
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchAutoCompleteTextView.getText().toString();
                String selectedGenre = genreSpinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, "Searching for: " + query + " in " + selectedGenre, Toast.LENGTH_SHORT).show();
                // TODO: Implement actual search logic, possibly query a ContentProvider
            }
        });

        viewBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Launch BookActivity (you might pass data or have a list view here)
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });

        viewLayoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LayoutGalleryActivity.class);
                startActivity(intent);
            }
        });

        startBookServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start BookService for a background task
                Intent serviceIntent = new Intent(MainActivity.this, BookService.class);
                serviceIntent.putExtra("task_data", "perform_book_processing");
                startService(serviceIntent);
                Toast.makeText(MainActivity.this, "BookService started", Toast.LENGTH_SHORT).show();
            }
        });

        startSyncServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start BookSyncService for data synchronization
                Intent syncServiceIntent = new Intent(MainActivity.this, BookSyncService.class);
                startService(syncServiceIntent);
                Toast.makeText(MainActivity.this, "BookSyncService started", Toast.LENGTH_SHORT).show();
            }
        });

        viewWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch WebViewActivity to show a sample web page
                Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
                webViewIntent.putExtra("url", "https://en.wikipedia.org/wiki/African_literature");
                startActivity(webViewIntent);
            }
        });

        // TODO: Implement other UI controls like DatePicker, MapView (if applicable)
        // For DatePicker, you would typically use a DialogFragment.
    }
}
