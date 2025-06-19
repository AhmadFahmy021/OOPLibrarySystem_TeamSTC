package resource.components.layout.admin;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Index;

public class SideBarAdmin extends VBox {

    private Index app;
    private Button activeSidebarButton;

    public SideBarAdmin(Index app) {
        this.app = app;
        setSpacing(10);
        setStyle("-fx-background-color: #3f48cc;");
        setPadding(new Insets(20));
        setPrefWidth(150);

        Label title = new Label("L I B R A R Y");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnDashboard = new Button("Dashboard");
        Button btnManageBook = new Button("Manage Book");
        Button btnReturnBook = new Button("Return Book");
        Button btnViewReport = new Button("View Report");
        Button btnManageUsers = new Button("Manage Users");

        // Inisialisasi styling untuk semua tombol, dengan Dashboard sebagai default aktif
        setupSidebarButton(btnDashboard);
        setupSidebarButton(btnManageBook);
        setupSidebarButton(btnReturnBook);
        setupSidebarButton(btnViewReport);
        setupSidebarButton(btnManageUsers);

        // Set Dashboard sebagai tombol aktif pertama kali aplikasi dimulai
        setActiveButton(btnDashboard); // Panggil metode ini untuk mengatur gaya awal

        // Action Handlers
        btnDashboard.setOnAction(e -> {
            setActiveButton(btnDashboard); // Set tombol ini sebagai aktif
            app.showDashboardAdmin(); // Panggil metode di Index untuk menampilkan dashboard
        });

        btnManageBook.setOnAction(e -> {
            setActiveButton(btnManageBook); // Set tombol ini sebagai aktif
            app.showManageBook(); // Panggil metode di Index untuk menampilkan halaman Data Book
        });

        btnReturnBook.setOnAction(e -> {
            setActiveButton(btnReturnBook); // Set tombol ini sebagai aktif
            // app.showBorrowBookPage(); // Asumsi Anda punya metode ini
        });

        btnViewReport.setOnAction(e -> {
            setActiveButton(btnViewReport); // Set tombol ini sebagai aktif
            // app.showBorrowBookPage(); // Asumsi Anda punya metode ini
        });

        btnManageUsers.setOnAction(e -> {
            setActiveButton(btnManageUsers); // Set tombol ini sebagai aktif
            // app.showBorrowBookPage(); // Asumsi Anda punya metode ini
        });

        getChildren().addAll(title, btnDashboard, btnManageBook, btnReturnBook, btnViewReport, btnManageUsers);
    }


    // Metode ini sekarang hanya mengatur properti dasar tombol
    private void setupSidebarButton(Button button) {
        button.setPrefWidth(Double.MAX_VALUE);
        button.setPrefHeight(40);
        button.setTextFill(Color.WHITE);
        button.setStyle( "-fx-background-color: transparent; -fx-font-size: 14px;");
        // Mouse hover effect (ini tetap bisa bekerja independen dari status aktif)
        button.setOnMouseEntered(e -> {
            if (button != activeSidebarButton) { // Hanya ubah gaya jika bukan tombol aktif
                button.setStyle(
                "-fx-background-color: #5a66d9; -fx-font-size: 14px; -fx-font-weight: bold;");
            }
        });
        button.setOnMouseExited(e -> {
            if (button != activeSidebarButton) { // Hanya reset gaya jika bukan tombol aktif
                button.setStyle("-fx-background-color: transparent; -fx-font-size: 14px;");
            }
        });
    }


    // Metode baru untuk mengatur tombol yang sedang aktif
    private void setActiveButton(Button newActiveButton) {
        // 1. Reset gaya tombol yang sebelumnya aktif (jika ada)
        if (activeSidebarButton != null && activeSidebarButton != newActiveButton) {
            System.out.println("Resetting previous active button: " + activeSidebarButton.getText());
            activeSidebarButton.setStyle("-fx-background-color: transparent; -fx-font-size: 14px;");
        }

        // 2. Terapkan gaya "aktif" ke tombol yang baru
        System.out.println("Setting new active button: " + newActiveButton.getText());
        newActiveButton.setStyle("-fx-background-color: #5a66d9; -fx-font-size: 14px; -fx-font-weight: bold;");

        // 3. Simpan referensi ke tombol yang baru aktif
        activeSidebarButton = newActiveButton;
        System.out.println("Current active button is now: " + activeSidebarButton.getText());
    }

//    public void setActive(){
//        System.out.println(btnDataBook.isPressed());
//        styleSidebarButton(btnDataBook, true);
//    }
//
//    // You'll need to define this method. It could be in your main App class or a utility class.
//    private void styleSidebarButton(Button button, boolean isActive) {
//
//        button.setPrefWidth(Double.MAX_VALUE);
//        button.setPrefHeight(40);
//        button.setTextFill(Color.WHITE);
//        button.setStyle(isActive ?
//                "-fx-background-color: #5a66d9; -fx-font-size: 14px; -fx-font-weight: bold;" :
//                "-fx-background-color: transparent; -fx-font-size: 14px;");
//        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #5a66d9; -fx-font-size: 14px; -fx-font-weight: bold;"));
//        button.setOnMouseExited(e -> {
//            if (!isActive) {
//                button.setStyle("-fx-background-color: transparent; -fx-font-size: 14px;");
//            }
//        });
//    }
}
