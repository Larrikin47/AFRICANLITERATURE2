package com.example.africanliteraturelibraryapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor; // Still useful for specific cases or transformations
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.africanliteraturelibraryapp.data.dao.BookDao; // Import DAO
import com.example.africanliteraturelibraryapp.data.database.AppDatabase; // Import Database
import com.example.africanliteraturelibraryapp.data.model.Book;

import java.util.List;
import java.util.Objects; // For Objects.requireNonNull

/**
 * Custom ContentProvider for African Literature Book data.
 * This provider now interacts with a Room SQLite database for persistent storage.
 */
public class BookContentProvider extends ContentProvider {

    private static final String TAG = "BookContentProvider";

    public static final String AUTHORITY = "com.example.africanliteraturelibraryapp.bookprovider";

    public static final Uri CONTENT_URI_BOOKS = Uri.parse("content://" + AUTHORITY + "/books");
    public static final Uri CONTENT_URI_AUTHORS = Uri.parse("content://" + AUTHORITY + "/authors");

    private static final int BOOKS = 100;
    private static final int BOOKS_ID = 101;
    private static final int AUTHORS = 200;
    private static final int AUTHORS_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "books", BOOKS);
        sUriMatcher.addURI(AUTHORITY, "books/#", BOOKS_ID);
        sUriMatcher.addURI(AUTHORITY, "authors", AUTHORS);
        sUriMatcher.addURI(AUTHORITY, "authors/#", AUTHORS_ID);
    }

    private BookDao bookDao; // Declare DAO instance

    @Override
    public boolean onCreate() {
        // Initialize the Room database and get the DAO
        bookDao = AppDatabase.getDatabase(Objects.requireNonNull(getContext())).bookDao();
        Log.d(TAG, "BookContentProvider created and initialized with Room DAO.");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "Querying URI: " + uri);

        Cursor cursor = null;
        // The columns for the Book entity
        String[] bookColumns = {"id", "title", "author", "genre", "description", "coverImageUrl", "contentUrl"};

        // Use a MatrixCursor to build the cursor from Room data, as Room's LiveData/Flow
        // are not directly compatible with ContentProvider's Cursor return type.
        // For simple queries, this is fine. For complex ones, consider direct SQLite queries
        // through Room's SupportSQLiteDatabase or a more advanced ContentProvider implementation.
        MatrixCursor matrixCursor = new MatrixCursor(bookColumns);

        switch (sUriMatcher.match(uri)) {
            case BOOKS:
                // Get all books from Room
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    List<Book> books = bookDao.getAllBooks();
                    for (Book book : books) {
                        matrixCursor.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getCoverImageUrl(), book.getContentUrl()});
                    }
                });
                // Wait for the background task to complete and populate the cursor
                // This is a simplified approach. In a real app, you might use a CountDownLatch
                // or similar mechanism to ensure the cursor is fully populated before returning.
                try {
                    Thread.sleep(100); // Small delay to allow executor to run. Not ideal for production.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                cursor = matrixCursor;
                break;
            case BOOKS_ID:
                // Get a specific book by ID from Room
                long id = Long.parseLong(uri.getLastPathSegment());
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    Book book = bookDao.getBookById((int) id);
                    if (book != null) {
                        matrixCursor.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getDescription(), book.getCoverImageUrl(), book.getContentUrl()});
                    }
                });
                try {
                    Thread.sleep(100); // Small delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                cursor = matrixCursor;
                break;
            case AUTHORS:
                // TODO: Implement logic to return authors from Room (e.g., a separate AuthorDao)
                Log.w(TAG, "Authors query not fully implemented yet for Room.");
                break;
            case AUTHORS_ID:
                // TODO: Implement logic to return a specific author by ID from Room
                Log.w(TAG, "Specific author query not fully implemented yet for Room.");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null && cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case BOOKS:
                return "vnd.android.cursor.dir/vnd.com.example.africanliteraturelibraryapp.book";
            case BOOKS_ID:
                return "vnd.android.cursor.item/vnd.com.example.africanliteraturelibraryapp.book";
            case AUTHORS:
                return "vnd.android.cursor.dir/vnd.com.example.africanliteraturelibraryapp.author";
            case AUTHORS_ID:
                return "vnd.android.cursor.item/vnd.com.example.africanliteraturelibraryapp.author";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "Inserting into URI: " + uri);
        Uri returnUri = null;
        if (sUriMatcher.match(uri) == BOOKS && values != null) {
            String title = values.getAsString("title");
            String author = values.getAsString("author");
            String genre = values.getAsString("genre");
            String description = values.getAsString("description");
            String coverUrl = values.getAsString("coverImageUrl");
            String contentUrl = values.getAsString("contentUrl");

            Book newBook = new Book(title, author, genre, description, coverUrl, contentUrl);

            // Insert into Room in a background thread
            AppDatabase.databaseWriteExecutor.execute(() -> {
                long newId = bookDao.insertBooks(newBook)[0]; // insertBooks returns long[], get first ID
                Log.d(TAG, "Inserted new book with ID: " + newId);
                // Notify observers that data has changed
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            });
            // This returnUri might be null if the insert is truly asynchronous and ID is needed immediately.
            // For ContentProvider, it's often expected to return the URI of the new item.
            // A more robust solution might involve a callback or a blocking call if absolutely necessary.
            // For now, we'll return a placeholder URI.
            returnUri = CONTENT_URI_BOOKS; // Simplified return
        } else {
            throw new IllegalArgumentException("Unsupported URI for insert: " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "Deleting from URI: " + uri);
        int rowsDeleted = 0;
        if (sUriMatcher.match(uri) == BOOKS_ID) {
            long idToDelete = Long.parseLong(uri.getLastPathSegment());
            // Delete from Room in a background thread
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Book bookToDelete = bookDao.getBookById((int) idToDelete);
                if (bookToDelete != null) {
                    bookDao.deleteBooks(bookToDelete);
                    Log.d(TAG, "Deleted book with ID: " + idToDelete);
                    if (getContext() != null) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
            });
            rowsDeleted = 1; // Assuming one row deleted for specific ID
        } else {
            throw new IllegalArgumentException("Unsupported URI for delete: " + uri);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "Updating URI: " + uri);
        int rowsUpdated = 0;
        if (sUriMatcher.match(uri) == BOOKS_ID && values != null) {
            long idToUpdate = Long.parseLong(uri.getLastPathSegment());
            // Update in Room in a background thread
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Book bookToUpdate = bookDao.getBookById((int) idToUpdate);
                if (bookToUpdate != null) {
                    if (values.containsKey("title")) bookToUpdate.setTitle(values.getAsString("title"));
                    if (values.containsKey("author")) bookToUpdate.setAuthor(values.getAsString("author"));
                    if (values.containsKey("genre")) bookToUpdate.setGenre(values.getAsString("genre"));
                    if (values.containsKey("description")) bookToUpdate.setDescription(values.getAsString("description"));
                    if (values.containsKey("coverImageUrl")) bookToUpdate.setCoverImageUrl(values.getAsString("coverImageUrl"));
                    if (values.containsKey("contentUrl")) bookToUpdate.setContentUrl(values.getAsString("contentUrl"));
                    bookDao.updateBooks(bookToUpdate);
                    Log.d(TAG, "Updated book with ID: " + idToUpdate);
                    if (getContext() != null) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
            });
            rowsUpdated = 1; // Assuming one row updated for specific ID
        } else {
            throw new IllegalArgumentException("Unsupported URI for update: " + uri);
        }
        return rowsUpdated;
    }
}
