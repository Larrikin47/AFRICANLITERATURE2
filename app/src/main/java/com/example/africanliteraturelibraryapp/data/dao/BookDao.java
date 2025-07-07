package com.example.africanliteraturelibraryapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.africanliteraturelibraryapp.data.model.Book;

import java.util.List;

/**
 * Data Access Object (DAO) for the Book entity.
 * This interface defines the methods for interacting with the 'books' table in the database.
 */
@Dao
public interface BookDao {

    /**
     * Inserts one or more books into the database.
     * If a conflict occurs (e.g., trying to insert a book with an existing primary key),
     * it will replace the old entry.
     * @param books The book(s) to insert.
     * @return The row ID(s) of the inserted book(s).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertBooks(Book... books);

    /**
     * Updates one or more existing books in the database.
     * @param books The book(s) to update.
     * @return The number of rows updated.
     */
    @Update
    int updateBooks(Book... books);

    /**
     * Deletes one or more books from the database.
     * @param books The book(s) to delete.
     * @return The number of rows deleted.
     */
    @Delete
    int deleteBooks(Book... books);

    /**
     * Retrieves all books from the database.
     * @return A list of all Book objects.
     */
    @Query("SELECT * FROM books ORDER BY title ASC")
    List<Book> getAllBooks();

    /**
     * Retrieves a specific book by its ID.
     * @param bookId The ID of the book to retrieve.
     * @return The Book object if found, null otherwise.
     */
    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    Book getBookById(int bookId);

    /**
     * Deletes all books from the database.
     * @return The number of rows deleted.
     */
    @Query("DELETE FROM books")
    int deleteAllBooks();
}
