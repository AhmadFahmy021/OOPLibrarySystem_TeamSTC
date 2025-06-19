package app.controller.admin;

import app.model.Book_Model;
import data.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import main.Index;
import resource.view.admin.book.AddBook;
import resource.view.admin.book.ManageBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ManageBookController {
    private ManageBook viewManageBook;
    private AddBook viewAddBook;
    private Index app;
    private Book_Model modelBook;

    public ManageBookController(Index app){
        this.viewManageBook = new ManageBook(app, this::handleAddBookClicked);
        this.app = app;
        this.modelBook = new Book_Model();
        this.viewAddBook = new AddBook(this::handleSubmitBook);

        tampilkanData();
    }

    private void tampilkanData(){
        List<Book> books = modelBook.all();
        viewManageBook.setTotalBook(0);
        viewManageBook.setTotalBorrow(0);
        viewManageBook.setTotalReturn(01);
        viewManageBook.setBooks(books);
    }

    private void handleSubmitBook(ActionEvent event) {
        System.out.println("Tombol 'Save Book' diklik!");

        // 1. Ambil data dari View (input fields dan file gambar)
        String isbn = viewAddBook.getIsbnInput();
        String title = viewAddBook.getTitleInput();
        String author = viewAddBook.getAuthorInput();
        String quantityStr = viewAddBook.getQuantityInput();
        File coverFile = viewAddBook.getSelectedCoverFile();

        // 2. Validasi input
        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || quantityStr.isEmpty() || coverFile == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua kolom harus diisi dan gambar sampul harus diunggah.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                showAlert(Alert.AlertType.WARNING, "Kuantitas Tidak Valid", "Kuantitas harus angka positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Kuantitas Tidak Valid", "Kuantitas harus berupa angka.");
            return;
        }

        // 3. Tangani penyimpanan gambar
        String coverImagePathForDB = ""; // Path yang akan disimpan di database
        if (coverFile != null) {
            // Asumsi: Anda memiliki folder 'uploads/covers/' di root proyek Anda
            // Atau Anda bisa menyimpannya di src/main/resources/images/ (jika itu cara Anda mengelola aset)
            String uploadDir = "uploads/covers/"; // Folder tempat gambar akan disimpan
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs(); // Buat folder jika belum ada
            }

            // Buat nama file unik untuk mencegah konflik
            String fileName = System.currentTimeMillis() + "_" + coverFile.getName();
            Path destinationPath = Paths.get(uploadDir, fileName);

            try {
                Files.copy(coverFile.toPath(), destinationPath);
                coverImagePathForDB = destinationPath.toString(); // Path relatif untuk DB
                System.out.println("Gambar berhasil diunggah ke: " + coverImagePathForDB);
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Gagal Unggah Gambar", "Tidak dapat menyimpan gambar sampul: " + e.getMessage());
                System.err.println("Error copying file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        // 4. Buat objek Book
        // Asumsi Book ID akan di-generate oleh database (auto-increment) atau Anda punya logika lain
        // Jika ID perlu di-generate di sini, Anda harus menambahkannya
        Book newBook = new Book(0, isbn, title, author, quantityStr, coverImagePathForDB); // ID 0 jika auto-increment

        // 5. Panggil Model untuk menyimpan data
        try {
            // Asumsi modelBook.create() mengembalikan true jika sukses
            boolean success = modelBook.create(newBook); // Asumsi Anda punya metode 'create' di Book_Model

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Buku berhasil ditambahkan!");
                // Setelah sukses, arahkan kembali ke halaman manage book
                tampilkanData();
                app.showManageBook();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Buku gagal ditambahkan. Terjadi kesalahan.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error Database", "Terjadi kesalahan saat menyimpan buku ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleAddBookClicked(ActionEvent event) {
        app.showAddBookPage(); // <-- Ini akan mengarahkan ke halaman AddBook
    }

    public ManageBook getView(){
        return viewManageBook;
    }

    public AddBook getViewAddBook(){
        tampilkanData();
        return viewAddBook;
    }
}
