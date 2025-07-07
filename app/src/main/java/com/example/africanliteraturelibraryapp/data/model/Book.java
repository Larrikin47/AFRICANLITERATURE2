package com.example.africanliteraturelibraryapp.data.model;

import java.io.Serializable;

/**
 * Data model class representing a Book in the African Literature Library.
 * Implements Serializable to allow passing Book objects directly via Intents.
 */
public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String coverImageUrl; // URL for book cover image
    private String contentUrl;    // URL for book content (e.g., PDF, EPUB, or HTML)

    // Constructor
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

    // Setters (if you need to modify book properties after creation)
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
