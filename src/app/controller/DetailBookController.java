package app.controller;

import app.model.Book_Model;
import data.Book;
import main.Index;
import resource.view.user.DetailBook;

import java.util.ArrayList;
import java.util.List;

public class DetailBookController {
    private Index app;
    private DetailBook detailBookView;
    private Book_Model bookModel;

    public DetailBookController(Index app) {
        this.app = app;
        this.bookModel = new Book_Model();
        // Inisialisasi View DetailBook, meneruskan callback dari controller ini
        this.detailBookView = new DetailBook(
                this::handleBorrowBookRequest,       // Callback untuk tombol "Borrow Now"
                this::handleBookDetailsAndBorrowRequest // Callback untuk tombol "Detail Book"
        );
    }

    // Metode yang akan dipanggil dari Index untuk mendapatkan View Detail Buku
    public DetailBook getViewDetailBook() {
        return detailBookView;
    }

    // Metode ini akan dipanggil oleh Index untuk mengatur buku yang akan ditampilkan detailnya
    public void showBookDetails(Book book) {
        detailBookView.setBook(book); // Set buku di View
        loadAndDisplayRelatedBooks(book); // Memuat dan menampilkan buku terkait
        // Setelah setBook, Anda mungkin ingin melakukan navigasi ke halaman DetailBook di Index
        // app.showBookDetailPage(detailBookView); // Contoh, Anda perlu membuat metode ini di Index
    }

    // --- Callback Methods ---
    private void handleBorrowBookRequest(Book book) {
        // Logika untuk menangani permintaan peminjaman buku
        System.out.println("Borrow Now requested for book: " + book.getTitle());
        // Contoh: Buka dialog peminjaman atau navigasi ke halaman peminjaman
        // app.showBorrowBookDialog(book); // Anda perlu membuat metode ini di Index
        app.showDashboardUser(); // Untuk demo, kembali ke dashboard
    }

    private void handleBookDetailsAndBorrowRequest(Book book) {
        // Logika untuk menangani permintaan detail buku lebih lanjut
        System.out.println("Full Details requested for book: " + book.getTitle());
        // Ini mungkin akan memanggil showBookDetails lagi dengan buku yang sama
        // atau membuka dialog baru dengan lebih banyak detail
        // app.showFullBookDetailsDialog(book); // Anda perlu membuat metode ini di Index
    }

    // --- Logika untuk memuat dan menampilkan buku terkait (dipindahkan dari View) ---
    private void loadAndDisplayRelatedBooks(Book currentBook) {
        // --- Ini adalah contoh data dummy yang dipindahkan dari DetailBook.java ---
        // Di aplikasi nyata, Anda akan mengambil data ini dari database/service
        List<Book> allBooksFromDB = bookModel.all();


        List<Book> relatedBooksToDisplay = new ArrayList<>();
        // Filter buku terkait: tidak termasuk buku yang sedang ditampilkan (currentBook)
        for (Book book : allBooksFromDB) {
            if (book.getId_book() != currentBook.getId_book()) { // Asumsi book memiliki getId()
                relatedBooksToDisplay.add(book);
            }
            if (relatedBooksToDisplay.size() >= 4) { // Tampilkan maksimal 4 buku terkait
                break;
            }
        }

        // Panggil metode di DetailBook untuk menampilkan buku terkait
        detailBookView.displayRelatedBooks(relatedBooksToDisplay); // Anda perlu membuat metode ini di DetailBook
    }
}
