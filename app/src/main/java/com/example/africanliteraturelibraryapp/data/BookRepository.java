package com.example.africanliteraturelibraryapp.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.africanliteraturelibraryapp.data.AppDatabaseFix; // <-- UPDATED IMPORT
import com.example.africanliteraturelibraryapp.data.model.Book;
import com.example.africanliteraturelibraryapp.providers.BookContentProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class to abstract data access from the rest of the application.
 * It uses the BookContentProvider to interact with the Room database.
 */
public class BookRepository {

    private final ContentResolver contentResolver;
    private final ExecutorService executorService;
    private final Context context;
    private final Handler mainHandler;

    // Define the content URI for the books table
    private static final Uri CONTENT_URI = BookContentProvider.CONTENT_URI_BOOKS;

    public BookRepository(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.executorService = Executors.newFixedThreadPool(4);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Listener interface for asynchronous book loading.
     */
    public interface OnBooksLoadedListener {
        void onBooksLoaded(List<Book> books);
        void onBooksLoadFailed(String errorMessage);
    }

    /**
     * Inserts a new book into the database.
     * @param book The Book object to insert.
     */
    public void insertBook(Book book) {
        executorService.execute(() -> {
            ContentValues values = new ContentValues();
            values.put("title", book.getTitle());
            values.put("author", book.getAuthor());
            values.put("genre", book.getGenre());
            values.put("description", book.getDescription());
            values.put("cover_image_url", book.getCoverImageUrl());
            values.put("content_url", book.getContentUrl());

            try {
                Uri uri = contentResolver.insert(CONTENT_URI, values);
                if (uri != null) {
                    Log.d("BookRepository", "Book inserted at: " + uri);
                } else {
                    Log.e("BookRepository", "Failed to insert book.");
                    mainHandler.post(() -> Toast.makeText(context, "Failed to insert book.", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("BookRepository", "Error inserting book: " + e.getMessage(), e);
                mainHandler.post(() -> Toast.makeText(context, "Error inserting book: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    /**
     * Retrieves all books from the database.
     * @param listener Callback to deliver the results.
     */
    public void getAllBooks(OnBooksLoadedListener listener) {
        executorService.execute(() -> {
            List<Book> books = new ArrayList<>();
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int authorIndex = cursor.getColumnIndex("author");
                    int genreIndex = cursor.getColumnIndex("genre");
                    int descriptionIndex = cursor.getColumnIndex("description");
                    int coverUrlIndex = cursor.getColumnIndex("cover_image_url");
                    int contentUrlIndex = cursor.getColumnIndex("content_url");

                    do {
                        long id = (idIndex != -1) ? cursor.getLong(idIndex) : 0;
                        String title = (titleIndex != -1) ? cursor.getString(titleIndex) : "";
                        String author = (authorIndex != -1) ? cursor.getString(authorIndex) : "";
                        String genre = (genreIndex != -1) ? cursor.getString(genreIndex) : "";
                        String description = (descriptionIndex != -1) ? cursor.getString(descriptionIndex) : "";
                        String coverImageUrl = (coverUrlIndex != -1) ? cursor.getString(coverUrlIndex) : "";
                        String contentUrl = (contentUrlIndex != -1) ? cursor.getString(contentUrlIndex) : "";

                        books.add(new Book(id, title, author, genre, description, coverImageUrl, contentUrl));
                    } while (cursor.moveToNext());
                    mainHandler.post(() -> listener.onBooksLoaded(books));
                } else {
                    mainHandler.post(() -> listener.onBooksLoaded(new ArrayList<>()));
                }
            } catch (Exception e) {
                Log.e("BookRepository", "Error loading books: " + e.getMessage(), e);
                mainHandler.post(() -> listener.onBooksLoadFailed(e.getMessage()));
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    /**
     * Deletes a book from the database by its ID.
     * @param bookId The ID of the book to delete.
     */
    public void deleteBook(long bookId) {
        executorService.execute(() -> {
            Uri uri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(bookId));
            try {
                int deletedRows = contentResolver.delete(uri, null, null);
                if (deletedRows > 0) {
                    Log.d("BookRepository", "Book with ID " + bookId + " deleted successfully.");
                    mainHandler.post(() -> Toast.makeText(context, "Book deleted successfully!", Toast.LENGTH_SHORT).show());
                } else {
                    Log.e("BookRepository", "Failed to delete book with ID " + bookId + ".");
                    mainHandler.post(() -> Toast.makeText(context, "Failed to delete book.", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("BookRepository", "Error deleting book: " + e.getMessage(), e);
                mainHandler.post(() -> Toast.makeText(context, "Error deleting book: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}
