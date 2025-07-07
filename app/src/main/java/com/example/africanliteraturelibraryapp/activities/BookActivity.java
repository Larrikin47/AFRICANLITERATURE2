package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.squareup.picasso.Picasso; // Import Picasso

public class BookActivity extends AppCompatActivity {

    private TextView bookTitleTextView;
    private TextView bookAuthorTextView;
    private TextView bookGenreTextView;
    private TextView bookDescriptionTextView;
    private ImageView bookCoverImageView;
    private Button readBookButton;

    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Initialize UI components
        bookTitleTextView = findViewById(R.id.bookTitleTextView);
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
        bookGenreTextView = findViewById(R.id.bookGenreTextView);
        bookDescriptionTextView = findViewById(R.id.bookDescriptionTextView);
        bookCoverImageView = findViewById(R.id.bookCoverImageView);
        readBookButton = findViewById(R.id.readBookButton);

        // Get book data from the Intent
        currentBook = (Book) getIntent().getSerializableExtra("book_data");

        if (currentBook != null) {
            // Populate UI with book data
            bookTitleTextView.setText(currentBook.getTitle());
            bookAuthorTextView.setText("Author: " + currentBook.getAuthor());
            bookGenreTextView.setText("Genre: " + currentBook.getGenre());
            bookDescriptionTextView.setText(currentBook.getDescription());

            // --- Use Picasso to load book cover image ---
            if (currentBook.getCoverImageUrl() != null && !currentBook.getCoverImageUrl().isEmpty()) {
                Picasso.get().load(currentBook.getCoverImageUrl())
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder while loading
                        .error(R.drawable.ic_launcher_background) // Error image if loading fails
                        .into(bookCoverImageView);
            } else {
                bookCoverImageView.setImageResource(R.drawable.ic_launcher_background); // Default placeholder
            }
            // --- End Picasso usage ---

            readBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentBook.getContentUrl() != null && !currentBook.getContentUrl().isEmpty()) {
                        Intent webViewIntent = new Intent(BookActivity.this, WebViewActivity.class);
                        webViewIntent.putExtra("url", currentBook.getContentUrl());
                        startActivity(webViewIntent);
                    } else {
                        Toast.makeText(BookActivity.this, "No content URL available for this book.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "Book data not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no data
        }
    }
}
