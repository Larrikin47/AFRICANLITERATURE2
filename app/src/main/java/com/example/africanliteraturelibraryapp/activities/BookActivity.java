package com.example.africanliteraturelibraryapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.data.model.Book;
// You might need a library like Picasso or Glide for image loading
// import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private TextView bookTitleTextView;
    private TextView bookAuthorTextView;
    private TextView bookGenreTextView;
    private TextView bookDescriptionTextView;
    private ImageView bookCoverImageView;

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

        // Get book data from the Intent
        Book book = (Book) getIntent().getSerializableExtra("book_data");

        if (book != null) {
            // Populate UI with book data
            bookTitleTextView.setText(book.getTitle());
            bookAuthorTextView.setText("Author: " + book.getAuthor());
            bookGenreTextView.setText("Genre: " + book.getGenre());
            bookDescriptionTextView.setText(book.getDescription());

            // Load book cover image (requires an image loading library like Picasso or Glide)
            // Example with Picasso:
            // if (book.getCoverImageUrl() != null && !book.getCoverImageUrl().isEmpty()) {
            //     Picasso.get().load(book.getCoverImageUrl()).into(bookCoverImageView);
            // } else {
            //     bookCoverImageView.setImageResource(R.drawable.placeholder_book_cover); // Placeholder
            // }
            // For now, set a placeholder or hide if no image
            bookCoverImageView.setImageResource(R.drawable.ic_launcher_background); // Placeholder for demonstration
        } else {
            Toast.makeText(this, "Book data not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no data
        }
    }
}
