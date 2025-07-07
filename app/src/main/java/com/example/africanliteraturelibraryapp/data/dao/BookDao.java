package com.example.africanliteraturelibraryapp.data.dao;

import android.database.Cursor; // Import Cursor
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update; // Import Update
import androidx.room.Delete; // Import Delete

import com.example.africanliteraturelibraryapp.data.model.Book;

import java.util.List;

/**
 * Data Access Object (DAO) for the Book entity.
 * Provides methods for interacting with the 'books' table in the Room database.
 */
@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Query("SELECT * FROM books ORDER BY title ASC")
    List<Book> getAllBooks();

    @Query("SELECT * FROM books ORDER BY title ASC")
    Cursor getAllBooksCursor(); // Method for ContentProvider to get all books as a Cursor

    @Query("SELECT * FROM books WHERE id = :bookId")
    Book getBookById(long bookId); // Method to get a single book by ID

    @Query("SELECT * FROM books WHERE id = :bookId")
    Cursor getBookByIdCursor(long bookId); // Method for ContentProvider to get a single book as a Cursor

    @Update
    void update(Book book); // Method to update an existing book

    @Query("DELETE FROM books WHERE id = :bookId")
    void deleteById(long bookId); // Method to delete a book by ID

    @Query("DELETE FROM books")
    void deleteAll(); // Method to delete all books (useful for testing/resetting)
}
