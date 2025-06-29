package main;

import app.controller.BookController;
import app.controller.DashboardAdminController;
import app.controller.DashboardController;
import app.controller.DetailBookController;
import app.controller.admin.ManageBookController;
import data.Book;
import database.Koneksi;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resource.components.layout.admin.DashboardLayoutAdmin;
import resource.components.layout.user.DashboardLayout;
import resource.view.Login;
import resource.view.admin.book.AddBook;
import resource.view.user.DashboardUser;

public class Index extends Application {
    private Stage primaryStage;
    private String titleApp = "Library App";

    // --- Deklarasi Layouts dan Scenes ---
    private DashboardLayout dashboardLayoutUser; // Untuk layout user
    private DashboardLayoutAdmin dashboardLayoutAdmin; // Untuk layout admin
    private Scene userDashboardScene; // Scene untuk user dashboard
    private Scene adminDashboardScene; // Scene untuk admin dashboard
    private Scene loginScene; // Scene untuk login

    // --- Deklarasi Controllers ---
    private DashboardController userDashboardController;
    private DashboardAdminController adminDashboardController;
    private ManageBookController manageBookController;
    private BookController bookControllerUser; // Controller untuk DataBookPage user
    private DetailBookController detailBookController;


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle(titleApp);

        // --- PENTING: INISIALISASI SEMUA LAYOUT DAN SCENES DI AWAL ---

        // Inisialisasi DashboardLayout (User) dan Scene-nya
        dashboardLayoutUser = new DashboardLayout(this);
        userDashboardScene = new Scene(dashboardLayoutUser, 1550, 820);

        // Inisialisasi DashboardLayoutAdmin (Admin) dan Scene-nya
        dashboardLayoutAdmin = new DashboardLayoutAdmin(this);
        adminDashboardScene = new Scene(dashboardLayoutAdmin, 1550, 820);

        // Inisialisasi Scene Login
        Login loginRoot = new Login(this);
        loginScene = new Scene(loginRoot, 1550, 820); // Ukuran login Scene
        loginScene.getStylesheets().add(getClass().getResource("/resource/css/style_login.css").toExternalForm());

        // --- INISIALISASI SEMUA CONTROLLER DI AWAL ---
        // (Ini penting agar mereka bisa diakses dan tidak null)
        userDashboardController = new DashboardController(this);
        adminDashboardController = new DashboardAdminController(this);
        manageBookController = new ManageBookController(this);
        bookControllerUser = new BookController(this); // Untuk DataBookPage user
        detailBookController = new DetailBookController(this); // Untuk DetailBookPage

        // Set Scene awal ke Login dan tampilkan Stage
        primaryStage.setScene(loginScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // --- Metode navigasi ---

    public void showLoginPage(){
        primaryStage.setScene(loginScene);
        primaryStage.setTitle(titleApp + " | Sign In & Sign Up");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal
    }

    public void showDashboardUser(){
        // Langsung atur Scene ke userDashboardScene
        primaryStage.setScene(userDashboardScene);
        primaryStage.setTitle(titleApp + " | Dashboard User");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        // Set konten ke layout user
        dashboardLayoutUser.setContent(userDashboardController.getView());
        System.out.println("Navigated to Dashboard User");
    }

    public void showDashboardAdmin(){
        // Langsung atur Scene ke adminDashboardScene
        primaryStage.setScene(adminDashboardScene);
        primaryStage.setTitle(titleApp + " | Dashboard Admin");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        // Set konten ke layout admin
        dashboardLayoutAdmin.setContent(adminDashboardController.getView());
        System.out.println("Navigated to Dashboard Admin");
    }

    public void showManageBook(){
        // Pastikan kita ada di Scene Admin Dashboard untuk mengelola buku admin
        primaryStage.setScene(adminDashboardScene);
        primaryStage.setTitle(titleApp + " | Manage Book (Admin)");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        dashboardLayoutAdmin.setContent(manageBookController.getView());
        System.out.println("Navigated to Manage Book Page (Admin)");
    }

    public void showDataBookPage(){
        // Pastikan kita ada di Scene User Dashboard untuk melihat Data Book
        primaryStage.setScene(userDashboardScene);
        primaryStage.setTitle(titleApp + " | Data Book");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        dashboardLayoutUser.setContent(bookControllerUser.getView());
        System.out.println("Navigated to Data Book Page (User)");
    }

    public void showBookDetailPage(Book book) {
        // Asumsi ini adalah halaman user, jadi aktifkan userDashboardScene
        primaryStage.setScene(userDashboardScene);
        primaryStage.setTitle(titleApp + " | Book Details: " + book.getTitle());
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        detailBookController.showBookDetails(book);
        dashboardLayoutUser.setContent(detailBookController.getViewDetailBook());
        System.out.println("Navigated to Book Detail Page for: " + book.getTitle());
    }

    public void showAddBookPage(){
        // Ini adalah form untuk admin, jadi aktifkan adminDashboardScene
        primaryStage.setScene(adminDashboardScene);
        primaryStage.setTitle(titleApp + " | Add New Book");
        primaryStage.setMaximized(true); // Pastikan tetap maksimal

        dashboardLayoutAdmin.setContent(manageBookController.getViewAddBook());
        System.out.println("Navigated to Add New Book Page");
    }

    // --- Hapus metode setTitle yang sudah tidak relevan karena sudah diset di setiap show...Page() ---
    // private void setTitle(String title){
    //     primaryStage.setTitle(titleApp + " | " + title);
    // }

    public static void main(String[] args) {
        new Koneksi();
        launch(args);
    }
}
