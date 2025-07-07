package com.example.africanliteraturelibraryapp.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; // Import RecyclerView

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.example.africanliteraturelibraryapp.providers.BookContentProvider;
import com.example.africanliteraturelibraryapp.services.BookService;
import com.example.africanliteraturelibraryapp.services.BookSyncService;
import com.example.africanliteraturelibraryapp.ui.booklist.BookAdapter; // Import BookAdapter

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors; // For background thread

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AutoCompleteTextView searchAutoCompleteTextView;
    private Spinner genreSpinner;
    private Button searchButton;
    // Removed viewBooksButton as RecyclerView now handles book display
    private Button viewLayoutsButton;
    private Button startBookServiceButton;
    private Button startSyncServiceButton;
    private Button viewWebViewButton;

    private RecyclerView booksRecyclerView; // Declare RecyclerView
    private BookAdapter bookAdapter; // Declare Adapter

    // Executor for background database operations
    private final ExecutorService databaseReadExecutor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchAutoCompleteTextView = findViewById(R.id.searchAutoCompleteTextView);
        genreSpinner = findViewById(R.id.genreSpinner);
        searchButton = findViewById(R.id.searchButton);
        viewLayoutsButton = findViewById(R.id.viewLayoutsButton);
        startBookServiceButton = findViewById(R.id.startBookServiceButton);
        startSyncServiceButton = findViewById(R.id.startSyncServiceButton);
        viewWebViewButton = findViewById(R.id.viewWebViewButton);

        // --- RecyclerView Initialization ---
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this);
        booksRecyclerView.setAdapter(bookAdapter);
        // --- End RecyclerView Initialization ---

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

        // The "View All Books" button is removed, the list is now directly visible
        // viewBooksButton.setOnClickListener(...) is removed

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
                webViewIntent.putExtra("url", "https://en.wikipedia.org/wiki/African_literature"); // Default URL
                startActivity(webViewIntent);
            }
        });

        // Load books into the RecyclerView when the activity is created
        loadBooksIntoRecyclerView();
    }

    /**
     * Loads books from the ContentProvider into the RecyclerView.
     * This operation is performed on a background thread.
     */
    private void loadBooksIntoRecyclerView() {
        databaseReadExecutor.execute(() -> {
            List<Book> books = new ArrayList<>();
            ContentResolver resolver = getContentResolver();
            Cursor cursor = null;
            try {
                cursor = resolver.query(BookContentProvider.CONTENT_URI_BOOKS,
                        null, // All columns
                        null, // No selection
                        null, // No selection arguments
                        null); // No sort order

                if (cursor != null) {
                    int idCol = cursor.getColumnIndexOrThrow("id");
                    int titleCol = cursor.getColumnIndexOrThrow("title");
                    int authorCol = cursor.getColumnIndexOrThrow("author");
                    int genreCol = cursor.getColumnIndexOrThrow("genre");
                    int descriptionCol = cursor.getColumnIndexOrThrow("description");
                    int coverImageCol = cursor.getColumnIndexOrThrow("coverImageUrl");
                    int contentUrlCol = cursor.getColumnIndexOrThrow("contentUrl");

                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(idCol);
                        String title = cursor.getString(titleCol);
                        String author = cursor.getString(authorCol);
                        String genre = cursor.getString(genreCol);
                        String description = cursor.getString(descriptionCol);
                        String coverImageUrl = cursor.getString(coverImageCol);
                        String contentUrl = cursor.getString(contentUrlCol);

                        books.add(new Book(id, title, author, genre, description, coverImageUrl, contentUrl));
                    }
                    Log.d(TAG, "Loaded " + books.size() + " books from ContentProvider.");

                    // Update UI on the main thread
                    runOnUiThread(() -> bookAdapter.setBooks(books));
                } else {
                    Log.w(TAG, "ContentProvider returned null cursor for books.");
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "No books found.", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading books from ContentProvider: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error loading books.", Toast.LENGTH_SHORT).show());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shut down the executor service when the activity is destroyed
        databaseReadExecutor.shutdown();
    }
}
