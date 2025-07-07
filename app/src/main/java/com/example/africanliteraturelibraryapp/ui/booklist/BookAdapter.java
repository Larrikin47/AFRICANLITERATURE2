package com.example.africanliteraturelibraryapp.ui.booklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.activities.BookActivity;
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.squareup.picasso.Picasso; // Import Picasso

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of Book objects.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private final LayoutInflater inflater;
    private Context context;

    public BookAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Sets the list of books to be displayed and notifies the adapter to refresh.
     * @param newBooks The new list of books.
     */
    public void setBooks(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout (item_book.xml) for each list item
        View itemView = inflater.inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Bind data from the Book object to the views in the ViewHolder
        if (books != null) {
            Book currentBook = books.get(position);
            holder.bookTitle.setText(currentBook.getTitle());
            holder.bookAuthor.setText(currentBook.getAuthor());
            holder.bookGenre.setText("Genre: " + currentBook.getGenre());
            holder.bookDescription.setText(currentBook.getDescription());

            // --- Use Picasso to load book cover image ---
            if (currentBook.getCoverImageUrl() != null && !currentBook.getCoverImageUrl().isEmpty()) {
                Picasso.get().load(currentBook.getCoverImageUrl())
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder while loading
                        .error(R.drawable.ic_launcher_background) // Error image if loading fails
                        .into(holder.bookCover);
            } else {
                holder.bookCover.setImageResource(R.drawable.ic_launcher_background); // Default placeholder
            }
            // --- End Picasso usage ---

            // Set click listener for the entire item view
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked: " + currentBook.getTitle(), Toast.LENGTH_SHORT).show();
                    // Launch BookActivity with the clicked book's data
                    Intent intent = new Intent(context, BookActivity.class);
                    intent.putExtra("book_data", currentBook); // Pass the entire Book object
                    context.startActivity(intent);
                }
            });

        } else {
            // Handle case where books list is null (e.g., show empty state)
            holder.bookTitle.setText("No Book");
            holder.bookAuthor.setText("");
            holder.bookGenre.setText("");
            holder.bookDescription.setText("");
            holder.bookCover.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return books != null ? books.size() : 0;
    }

    /**
     * ViewHolder class to hold references to the views for each list item.
     */
    static class BookViewHolder extends RecyclerView.ViewHolder {
        final ImageView bookCover;
        final TextView bookTitle;
        final TextView bookAuthor;
        final TextView bookGenre;
        final TextView bookDescription;

        BookViewHolder(View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.itemBookCover);
            bookTitle = itemView.findViewById(R.id.itemBookTitle);
            bookAuthor = itemView.findViewById(R.id.itemBookAuthor);
            bookGenre = itemView.findViewById(R.id.itemBookGenre);
            bookDescription = itemView.findViewById(R.id.itemBookDescription);
        }
    }
}
