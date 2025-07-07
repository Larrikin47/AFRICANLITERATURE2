package com.example.africanliteraturelibraryapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.data.BookRepository;
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.google.android.material.textfield.TextInputEditText;

public class AddBookActivity extends AppCompatActivity {

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextAuthor;
    private TextInputEditText editTextGenre;
    private TextInputEditText editTextDescription;
    private TextInputEditText editTextCoverUrl;
    private TextInputEditText editTextContentUrl;
    private Button saveBookButton;

    private BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Initialize UI components
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextCoverUrl = findViewById(R.id.editTextCoverUrl);
        editTextContentUrl = findViewById(R.id.editTextContentUrl);
        saveBookButton = findViewById(R.id.saveBookButton);

        // Initialize BookRepository
        bookRepository = new BookRepository(getApplicationContext());

        saveBookButton.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String genre = editTextGenre.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String coverUrl = editTextCoverUrl.getText().toString().trim();
        String contentUrl = editTextContentUrl.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Title and Author cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        Book newBook = new Book(title, author, genre, description, coverUrl, contentUrl);
        bookRepository.insertBook(newBook);

        Toast.makeText(this, "Book added successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Close activity after saving
    }
}
