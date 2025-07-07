package com.example.africanliteraturelibraryapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore; // Import @Ignore
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Represents a Book entity in the database.
 * Implements Serializable to allow passing Book objects between Activities via Intents.
 */
@Entity(tableName = "books")
public class Book implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "cover_image_url")
    private String coverImageUrl;

    @ColumnInfo(name = "content_url")
    private String contentUrl;

    // Primary constructor for Room to use when reading from the database
    public Book(long id, String title, String author, String genre, String description, String coverImageUrl, String contentUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.contentUrl = contentUrl;
    }

    // Convenience constructor for creating new Book objects before insertion
    // Room will ignore this constructor when reading from the database
    @Ignore // <--- ADDED THIS ANNOTATION
    public Book(String title, String author, String genre, String description, String coverImageUrl, String contentUrl) {
        this(0, title, author, genre, description, coverImageUrl, contentUrl); // ID will be auto-generated
    }

    // Getters
    public long getId() {
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

    // Setters (Room may require setters for some operations, or you can make fields public)
    public void setId(long id) {
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
}
