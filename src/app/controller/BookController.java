package app.controller;

import app.model.Book_Model;
import data.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Index;
import resource.view.user.DashboardUser;
import resource.view.user.DataBook;
import resource.view.user.DataBookPage;
import resource.view.user.DetailBook;


import java.util.List;

public class BookController {
    private Book_Model bookModel;
    private DataBookPage viewBook;
    private DetailBook viewBookDetailBook;
    private Index app;

    public BookController(Index app){
        this.app = app;
        this.bookModel = new Book_Model();
        this.viewBook = new DataBookPage(this::handleBookDetail);
//        this.viewBookDetailBook = new DetailBook(Book);
        tampilkanData();
    }

    public void tampilkanData() {
        List<Book> allBook = bookModel.all();

        viewBook.setBooks(allBook);
    }

    private void handleBookDetail(Book b) {
        System.out.println("Klik buku: " + b.getTitle());
        // Tampilkan detail buku misalnya di alert atau halaman lain
        app.showBookDetailPage(b);
    }

    public DataBookPage getView() {
        return viewBook;
    }

    public DetailBook getViewDetailBook(){
        return viewBookDetailBook;
    }
}
