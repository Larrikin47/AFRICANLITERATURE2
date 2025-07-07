package com.example.africanliteraturelibraryapp.data.model;

import androidx.room.Entity;
import androidx.room.Ignore; // Import the @Ignore annotation
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Data model class representing a Book in the African Literature Library.
 * Implements Serializable to allow passing Book objects directly via Intents.
 *
 * This class is also annotated as a Room database @Entity.
 */
@Entity(tableName = "books")
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String coverImageUrl;
    private String contentUrl;

    // Constructor for creating new Book objects (without ID for auto-generation)
    // This constructor is used when you create a Book object before inserting it into the DB.
    // Room should IGNORE this constructor when it's trying to read data from the DB.
    @Ignore // <--- ADD THIS ANNOTATION
    public Book(String title, String author, String genre, String description, String coverImageUrl, String contentUrl) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.contentUrl = contentUrl;
    }

    // Constructor with ID (used by Room when retrieving from DB and for manual ID assignment if needed)
    // Room will use this constructor when it reads data from the database.
    public Book(int id, String title, String author, String genre, String description, String coverImageUrl, String contentUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.contentUrl = contentUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
