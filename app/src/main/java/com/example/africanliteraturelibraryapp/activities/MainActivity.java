package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.data.BookRepository;
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.example.africanliteraturelibraryapp.ui.booklist.BookAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button addBookButton;
    private Button layoutGalleryButton;
    private Button uiControlsGalleryButton;
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText);
        addBookButton = findViewById(R.id.addBookButton);
        layoutGalleryButton = findViewById(R.id.layoutGalleryButton);
        uiControlsGalleryButton = findViewById(R.id.uiControlsGalleryButton);
        bookRecyclerView = findViewById(R.id.bookRecyclerView);

        // Initialize BookRepository
        bookRepository = new BookRepository(getApplicationContext());

        // Setup RecyclerView
        bookAdapter = new BookAdapter(this);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookRecyclerView.setAdapter(bookAdapter);

        // Load all books initially
        loadAllBooks();

        // Set up listeners
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        layoutGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LayoutGalleryActivity.class);
                startActivity(intent);
            }
        });

        uiControlsGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UiControlsGalleryActivity.class);
                startActivity(intent);
            }
        });

        // Search functionality (simplified for now)
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            Toast.makeText(MainActivity.this, "Search for: " + searchEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllBooks();
    }

    private void loadAllBooks() {
        bookRepository.getAllBooks(new BookRepository.OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                if (books != null && !books.isEmpty()) {
                    bookAdapter.setBooks(books);
                    Log.d("MainActivity", "Loaded " + books.size() + " books from ContentProvider.");
                } else {
                    bookAdapter.setBooks(null);
                    Log.d("MainActivity", "No books found in ContentProvider.");
                }
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                Toast.makeText(MainActivity.this, "Error loading books: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e("MainActivity", "Error loading books: " + errorMessage);
            }
        });
    }
}
