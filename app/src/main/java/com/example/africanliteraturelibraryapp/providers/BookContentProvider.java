package com.example.africanliteraturelibraryapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.africanliteraturelibraryapp.data.model.Book; // Make sure this import is correct

import java.util.ArrayList;
import java.util.List;

/**
 * Custom ContentProvider for African Literature Book data.
 * This example uses in-memory mock data for simplicity. In a real app,
 * this would interact with a SQLite database (e.g., using Room) or a backend.
 *
 * It provides a structured interface to query, insert, update, and delete book data.
 */
public class BookContentProvider extends ContentProvider {

    private static final String TAG = "BookContentProvider";

    // Authority for the content provider. This MUST match the authority in AndroidManifest.xml
    public static final String AUTHORITY = "com.example.africanliteraturelibraryapp.bookprovider";

    // Content URIs that this provider can handle
    public static final Uri CONTENT_URI_BOOKS = Uri.parse("content://" + AUTHORITY + "/books");
    public static final Uri CONTENT_URI_AUTHORS = Uri.parse("content://" + AUTHORITY + "/authors");
    // TODO: Add more URIs for categories, etc., if needed.

    // URI Matcher codes for different URI patterns
    private static final int BOOKS = 100;       // Matches content://AUTHORITY/books
    private static final int BOOKS_ID = 101;    // Matches content://AUTHORITY/books/# (specific book by ID)
    private static final int AUTHORS = 200;     // Matches content://AUTHORITY/authors
    private static final int AUTHORS_ID = 201;  // Matches content://AUTHORITY/authors/# (specific author by ID)

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer to add URI patterns to the matcher
    static {
        sUriMatcher.addURI(AUTHORITY, "books", BOOKS);
        sUriMatcher.addURI(AUTHORITY, "books/#", BOOKS_ID); // '#' is a wildcard for a numeric ID
        sUriMatcher.addURI(AUTHORITY, "authors", AUTHORS);
        sUriMatcher.addURI(AUTHORITY, "authors/#", AUTHORS_ID);
    }

    // Mock data for demonstration purposes. In a real application, this would be backed by a database.
    private static List<Book> mockBooks;

    @Override
    public boolean onCreate() {
        // This method is called when the provider is initialized.
        // Initialize your database or data source here.
        mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1, "Things Fall Apart", "Chinua Achebe", "Fiction", "A classic novel about pre-colonial Igbo life and the impact of colonialism.", "https://placehold.co/200x300/4CAF50/FFFFFF?text=TFA", "https://en.wikipedia.org/wiki/Things_Fall_Apart"));
        mockBooks.add(new Book(2, "Half of a Yellow Sun", "Chimamanda Ngozi Adichie", "Historical Fiction", "Set during the Nigerian Civil War, exploring its effects on the lives of ordinary people.", "https://placehold.co/200x300/FFC107/000000?text=HOAYS", "https://en.wikipedia.org/wiki/Half_of_a_Yellow_Sun"));
        mockBooks.add(new Book(3, "The Joys of Motherhood", "Buchi Emecheta", "Fiction", "Explores the challenges faced by women in patriarchal Nigerian society.", "https://placehold.co/200x300/388E3C/FFFFFF?text=JOM", "https://en.wikipedia.org/wiki/The_Joys_of_Motherhood"));
        Log.d(TAG, "BookContentProvider created and initialized with mock data.");
        return true; // Return true if the provider was successfully loaded
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // This method performs queries on the data.
        Log.d(TAG, "Querying URI: " + uri);

        MatrixCursor cursor = null; // MatrixCursor is used for in-memory data
        String[] bookColumns = {"_id", "title", "author", "genre", "description", "coverImageUrl", "contentUrl"};

        switch (sUriMatcher.match(uri)) {
            case BOOKS:
                // Query for all books
                cursor = new MatrixCursor(bookColumns);
                for (Book book : mockBooks) {
                    cursor.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getCoverImageUrl(), book.getContentUrl()});
                }
                break;
            case BOOKS_ID:
                // Query for a specific book by ID
                long id = Long.parseLong(uri.getLastPathSegment()); // Get the ID from the URI
                cursor = new MatrixCursor(bookColumns);
                for (Book book : mockBooks) {
                    if (book.getId() == id) {
                        cursor.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getCoverImageUrl(), book.getContentUrl()});
                        break; // Found the book, no need to continue loop
                    }
                }
                break;
            case AUTHORS:
                // TODO: Implement logic to return authors (e.g., unique authors from mockBooks)
                Log.w(TAG, "Authors query not fully implemented yet for this example.");
                // Example for authors:
                // Set<String> uniqueAuthors = new HashSet<>();
                // for (Book book : mockBooks) { uniqueAuthors.add(book.getAuthor()); }
                // String[] authorColumns = {"_id", "name"}; // Example columns
                // cursor = new MatrixCursor(authorColumns);
                // int authorId = 0;
                // for (String authorName : uniqueAuthors) {
                //     cursor.addRow(new Object[]{++authorId, authorName});
                // }
                break;
            case AUTHORS_ID:
                // TODO: Implement logic to return a specific author by ID
                Log.w(TAG, "Specific author query not fully implemented yet for this example.");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Notify registered observers that the data has changed (important for UI updates)
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Returns the MIME type for the given URI, indicating the type of data returned.
        switch (sUriMatcher.match(uri)) {
            case BOOKS:
                return "vnd.android.cursor.dir/vnd.com.example.africanliteraturelibraryapp.book"; // Directory of books
            case BOOKS_ID:
                return "vnd.android.cursor.item/vnd.com.example.africanliteraturelibraryapp.book"; // Single book item
            case AUTHORS:
                return "vnd.android.cursor.dir/vnd.com.example.africanliteraturelibraryapp.author"; // Directory of authors
            case AUTHORS_ID:
                return "vnd.android.cursor.item/vnd.com.example.africanliteraturelibraryapp.author"; // Single author item
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // This method inserts new data into the provider.
        Log.d(TAG, "Inserting into URI: " + uri);
        switch (sUriMatcher.match(uri)) {
            case BOOKS:
                if (values != null) {
                    // Simulate adding a new book to our mock list
                    int newId = mockBooks.size() + 1; // Simple ID generation
                    String title = values.getAsString("title");
                    String author = values.getAsString("author");
                    String genre = values.getAsString("genre");
                    String description = values.getAsString("description");
                    String coverUrl = values.getAsString("coverImageUrl");
                    String contentUrl = values.getAsString("contentUrl");

                    Book newBook = new Book(newId, title, author, genre, description, coverUrl, contentUrl);
                    mockBooks.add(newBook);
                    Log.d(TAG, "Inserted new book: " + newBook.getTitle());

                    // Notify observers that data has changed for this URI
                    if (getContext() != null) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                    // Return the URI of the newly inserted row
                    return Uri.withAppendedPath(CONTENT_URI_BOOKS, String.valueOf(newId));
                }
                break;
            // TODO: Implement insert for AUTHORS
            default:
                throw new IllegalArgumentException("Unsupported URI for insert: " + uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // This method deletes data from the provider.
        Log.d(TAG, "Deleting from URI: " + uri);
        int rowsDeleted = 0;
        switch (sUriMatcher.match(uri)) {
            case BOOKS_ID:
                long idToDelete = Long.parseLong(uri.getLastPathSegment());
                for (int i = 0; i < mockBooks.size(); i++) {
                    if (mockBooks.get(i).getId() == idToDelete) {
                        mockBooks.remove(i);
                        rowsDeleted = 1;
                        Log.d(TAG, "Deleted book with ID: " + idToDelete);
                        break;
                    }
                }
                break;
            // TODO: Implement delete for BOOKS (multiple), AUTHORS, AUTHORS_ID
            default:
                throw new IllegalArgumentException("Unsupported URI for delete: " + uri);
        }
        // Notify observers if any rows were deleted
        if (rowsDeleted > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // This method updates existing data in the provider.
        Log.d(TAG, "Updating URI: " + uri);
        int rowsUpdated = 0;
        switch (sUriMatcher.match(uri)) {
            case BOOKS_ID:
                long idToUpdate = Long.parseLong(uri.getLastPathSegment());
                if (values != null) {
                    for (Book book : mockBooks) {
                        if (book.getId() == idToUpdate) {
                            if (values.containsKey("title")) book.setTitle(values.getAsString("title"));
                            if (values.containsKey("author")) book.setAuthor(values.getAsString("author"));
                            if (values.containsKey("genre")) book.setGenre(values.getAsString("genre"));
                            if (values.containsKey("description")) book.setDescription(values.getAsString("description"));
                            if (values.containsKey("coverImageUrl")) book.setCoverImageUrl(values.getAsString("coverImageUrl"));
                            if (values.containsKey("contentUrl")) book.setContentUrl(values.getAsString("contentUrl"));
                            rowsUpdated = 1;
                            Log.d(TAG, "Updated book with ID: " + idToUpdate);
                            break;
                        }
                    }
                }
                break;
            // TODO: Implement update for BOOKS (multiple), AUTHORS, AUTHORS_ID
            default:
                throw new IllegalArgumentException("Unsupported URI for update: " + uri);
        }
        // Notify observers if any rows were updated
        if (rowsUpdated > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
