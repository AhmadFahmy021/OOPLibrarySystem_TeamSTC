package app.controller;

import app.model.Book_Model;
import data.Book;
import main.Index;
import resource.view.user.DashboardUser;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    private DashboardUser viewDashboardUser;
    private Book_Model modelBook;
    private Index app;

    public DashboardController(Index app){
        this.app = app;
        this.modelBook = new Book_Model();
        this.viewDashboardUser = new DashboardUser(app);
        tampilkanData();
    }

    public void tampilkanData(){
        List<Book> allBook = modelBook.all();
        int totalBook = modelBook.count("where");

        viewDashboardUser.setTotalBuku(totalBook);
        viewDashboardUser.setTotalReturn(10);
        viewDashboardUser.setTotalBorrow(10);

        viewDashboardUser.setBooks(allBook);
    }

    public DashboardUser getView(){
        return viewDashboardUser;
    }
}
