package com.example.africanliteraturelibraryapp.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.africanliteraturelibraryapp.data.AppDatabaseFix; // <-- UPDATED IMPORT
import com.example.africanliteraturelibraryapp.data.dao.BookDao;
import com.example.africanliteraturelibraryapp.data.model.Book;

public class BookContentProvider extends ContentProvider {

    // Define authority for the content provider
    public static final String AUTHORITY = "com.example.africanliteraturelibraryapp.bookprovider";
    // Define content URI for the books table
    public static final Uri CONTENT_URI_BOOKS = Uri.parse("content://" + AUTHORITY + "/books");

    // URI Matcher codes
    private static final int BOOKS = 1;
    private static final int BOOK_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // Add URI patterns to the matcher
        uriMatcher.addURI(AUTHORITY, "books", BOOKS);
        uriMatcher.addURI(AUTHORITY, "books/#", BOOK_ID);
    }

    private BookDao bookDao;

    @Override
    public boolean onCreate() {
        // Initialize the Room database DAO using the new database class
        bookDao = AppDatabaseFix.getDatabase(getContext()).bookDao(); // <-- UPDATED CLASS NAME
        Log.d("BookContentProvider", "BookContentProvider created and initialized with Room DAO.");
        return true; // Return true if the provider was successfully loaded
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                // Query all books
                Log.d("BookContentProvider", "Querying URI: " + uri);
                cursor = bookDao.getAllBooksCursor();
                break;
            case BOOK_ID:
                // Query a single book by ID
                long id = ContentUris.parseId(uri);
                Log.d("BookContentProvider", "Querying URI for ID: " + id);
                cursor = bookDao.getBookByIdCursor(id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        // Set notification URI to allow the cursor to be updated when data changes
        if (getContext() != null && cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".book";
            case BOOK_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + ".book";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = -1;
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                if (values == null) {
                    throw new IllegalArgumentException("ContentValues cannot be null for insert.");
                }
                // Insert new book asynchronously
                AppDatabaseFix.databaseWriteExecutor.execute(() -> { // <-- UPDATED CLASS NAME
                    Book book = new Book(
                            values.getAsString("title"),
                            values.getAsString("author"),
                            values.getAsString("genre"),
                            values.getAsString("description"),
                            values.getAsString("cover_image_url"),
                            values.getAsString("content_url")
                    );
                    bookDao.insert(book);
                });
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return ContentUris.withAppendedId(CONTENT_URI_BOOKS, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_ID:
                long id = ContentUris.parseId(uri);
                // Delete book asynchronously
                AppDatabaseFix.databaseWriteExecutor.execute(() -> { // <-- UPDATED CLASS NAME
                    bookDao.deleteById(id);
                });
                deletedRows = 1;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        if (getContext() != null && deletedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_ID:
                if (values == null) {
                    throw new IllegalArgumentException("ContentValues cannot be null for update.");
                }
                long id = ContentUris.parseId(uri);
                // Update book asynchronously
                AppDatabaseFix.databaseWriteExecutor.execute(() -> { // <-- UPDATED CLASS NAME
                    Book existingBook = bookDao.getBookById(id);
                    if (existingBook != null) {
                        if (values.containsKey("title")) existingBook.setTitle(values.getAsString("title"));
                        if (values.containsKey("author")) existingBook.setAuthor(values.getAsString("author"));
                        if (values.containsKey("genre")) existingBook.setGenre(values.getAsString("genre"));
                        if (values.containsKey("description")) existingBook.setDescription(values.getAsString("description"));
                        if (values.containsKey("cover_image_url")) existingBook.setCoverImageUrl(values.getAsString("cover_image_url"));
                        if (values.containsKey("content_url")) existingBook.setContentUrl(values.getAsString("content_url"));
                        bookDao.update(existingBook);
                    }
                });
                updatedRows = 1;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        if (getContext() != null && updatedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
    }
}
