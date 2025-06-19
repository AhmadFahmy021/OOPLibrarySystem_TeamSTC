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

    private DashboardLayout dashboardLayout;
    private DashboardLayoutAdmin dashboardLayoutAdmin;
    private Scene dashboardScene; // Menyimpan referensi Scene dashboard
    private DetailBookController detailBookController;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle(titleApp); // Set judul awal aplikasi
        // Tidak perlu setMaximized(true) di sini dulu, karena akan diatur di showLoginPage()
        // Atau hanya panggil sekali setelah scene pertama di showLoginPage()

        // Langsung tampilkan halaman login sebagai Scene pertama
        showLoginPage();
    }

    public void showLoginPage(){
        Login login = new Login(this);
        Scene scene = new Scene(login, 1550, 820); // Ukuran default untuk Scene login
        scene.getStylesheets().add(getClass().getResource("/resource/css/style_login.css").toExternalForm());

        primaryStage.setTitle(titleApp + " | Sign In & Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show(); // Penting: show() setelah setScene untuk pertama kali
        primaryStage.setMaximized(true); // <--- Panggil di sini setelah Scene diatur dan sebelum show
        // Ini memastikan jendela maximized saat pertama kali muncul
    }
    private void ensureDashboardSceneActive() {
        if (primaryStage.getScene() != dashboardScene) {
            System.out.println("Switching to Dashboard Scene...");
            primaryStage.setScene(dashboardScene);
            primaryStage.setMaximized(true); // Pastikan Stage tetap maksimal
        }
    }

    public void showDashboardUser(){
        if (dashboardLayout == null) {
            dashboardLayout = new DashboardLayout(this);
            dashboardScene = new Scene(dashboardLayout, 1550, 820); // Ukuran default untuk Scene dashboard
            // Anda bisa tambahkan stylesheet global untuk dashboard di sini jika ada
            // dashboardScene.getStylesheets().add(getClass().getResource("/resource/css/style_dashboard.css").toExternalForm());
        }

        // Pastikan primary stage diatur ke dashboardScene ini
        // Ini akan terjadi hanya saat transisi dari login ke dashboard
        if (primaryStage.getScene() != dashboardScene) {
            primaryStage.setScene(dashboardScene);
            // Re-apply maximized state when changing scenes if it might reset
            primaryStage.setMaximized(true); // <--- PENTING: Panggil lagi di sini
        }

        DashboardController dashboardController = new DashboardController(this);
        dashboardLayout.setContent(dashboardController.getView()); // Mengatur konten spesifik untuk dashboard
        primaryStage.setTitle(titleApp + " | Dashboard User"); // Mengatur judul jendela
        System.out.println("Navigated to Dashboard User");
    }
    public void showDashboardAdmin(){
        if (dashboardLayoutAdmin == null) {
            dashboardLayoutAdmin = new DashboardLayoutAdmin(this);
            dashboardScene = new Scene(dashboardLayoutAdmin, 1550, 820); // Ukuran default untuk Scene dashboard
            // Anda bisa tambahkan stylesheet global untuk dashboard di sini jika ada
            // dashboardScene.getStylesheets().add(getClass().getResource("/resource/css/style_dashboard.css").toExternalForm());
        }

        // Pastikan primary stage diatur ke dashboardScene ini
        // Ini akan terjadi hanya saat transisi dari login ke dashboard
        if (primaryStage.getScene() != dashboardScene) {
            primaryStage.setScene(dashboardScene);
            // Re-apply maximized state when changing scenes if it might reset
            primaryStage.setMaximized(true); // <--- PENTING: Panggil lagi di sini
        }

        DashboardAdminController dashboardAdminController = new DashboardAdminController(this);
        dashboardLayoutAdmin.setContent(dashboardAdminController.getView()); // Mengatur konten spesifik untuk dashboard
        primaryStage.setTitle(titleApp + " | Dashboard Admin"); // Mengatur judul jendela
        System.out.println("Navigated to Dashboard Admin");
    }

    public void showManageBook(){
        if (dashboardLayoutAdmin == null) {
            dashboardLayoutAdmin = new DashboardLayoutAdmin(this);
            dashboardScene = new Scene(dashboardLayoutAdmin, 1550, 820); // Ukuran default untuk Scene dashboard
            // Anda bisa tambahkan stylesheet global untuk dashboard di sini jika ada
            // dashboardScene.getStylesheets().add(getClass().getResource("/resource/css/style_dashboard.css").toExternalForm());
        }

        // Pastikan primary stage diatur ke dashboardScene ini
        // Ini akan terjadi hanya saat transisi dari login ke dashboard
        if (primaryStage.getScene() != dashboardScene) {
            primaryStage.setScene(dashboardScene);
            // Re-apply maximized state when changing scenes if it might reset
            primaryStage.setMaximized(true); // <--- PENTING: Panggil lagi di sini
        }

        ManageBookController manageBookController = new ManageBookController(this);
        dashboardLayoutAdmin.setContent(manageBookController.getView());
    }


    public void showDataBookPage(){
        // Safety check, memastikan kita di dashboard scene
        if (dashboardLayout == null || primaryStage.getScene() != dashboardScene) {
            System.err.println("Warning: Attempted to show Data Book page when DashboardLayout is not the current scene. Navigating to Dashboard first.");
            showDashboardUser(); // Pastikan dashboard layout aktif dan scene sudah diatur
        }

        BookController controller = new BookController(this); // Pertimbangkan scope dari controller
        dashboardLayout.setContent(controller.getView());
        primaryStage.setTitle(titleApp + " | Data Book");
        System.out.println("Navigated to Data Book Page");
    }

    public void showBookDetailPage(Book book) {
        // Pastikan kita berada di dashboard layout sebelum menampilkan detail
        if (primaryStage.getScene() != dashboardScene) {
            System.err.println("Warning: Attempted to show Book Detail page when DashboardLayout is not the current scene. Navigating to Dashboard first.");
            showDashboardUser(); // Beralih ke dashboard jika belum
        }

        // Panggil controller detail buku untuk mengatur dan menampilkan buku
        detailBookController = new DetailBookController(this);
        detailBookController.showBookDetails(book);

        // Set konten dashboardLayout ke view detail buku
        dashboardLayout.setContent(detailBookController.getViewDetailBook());
        primaryStage.setTitle(titleApp + " | Book Details: " + book.getTitle());
        System.out.println("Navigated to Book Detail Page for: " + book.getTitle());
    }

    public void showAddBookPage(){
            ensureDashboardSceneActive();
//            AddBook addBookView = new AddBook(); // Buat instance AddBook View
            ManageBookController manageBookController = new ManageBookController(this);
            dashboardLayoutAdmin.setContent(manageBookController.getViewAddBook()); // Set sebagai konten
            setTitle("Add New Book");
            System.out.println("Navigated to Add New Book Page");
    }

    private void setTitle(String title){ // Tidak perlu titleApp di sini, sudah dihandle pemanggil
        primaryStage.setTitle(titleApp + " | " + title);
    }

    public static void main(String[] args) {
        new Koneksi(); // Inisialisasi koneksi database
        launch(args);
    }
}
