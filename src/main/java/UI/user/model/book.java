package main.java.UI.user.model;

public class book {
    private final String id;
    private final String title;
    private final String author;
    private final String ISBN;
   
    private final String coverImagePath; // Path ke gambar cover buku
    
    private int stock;

    // Konstruktor
    public book(String id, String title, String ISBN, String author, String coverImagePath, int stock) {
        this.id = id;
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.coverImagePath = coverImagePath;
        this.stock = stock;
    }

    // Getter methods
    public String getId() {
        return id;
    }
    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public int getStock() {
        return stock;
    }

    // Setter methods (jika diperlukan, untuk modifikasi data)
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}