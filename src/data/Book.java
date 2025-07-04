package data;

public class Book {
    private int id_book;
    private String isbn;
    private String title;
    private String author;
    private String quantity;
    private String cover; // path image (jika disimpan di DB atau default)

    public Book(int id_book, String isbn, String title, String author, String quantity, String cover){
        this.id_book = id_book;
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.quantity = quantity;
        this.cover = cover;
    }

    public int getId_book() {
        return id_book;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover ;
    }

    public String getCoverImagePath() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setId(int id) { this.id_book = id; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setQuantity(String quantity) { this.quantity = quantity; } // <--- Menerima String
    public void setCoverImagePath(String coverImagePath) { this.cover = coverImagePath; }
}
