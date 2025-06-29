package main.java.UI.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

public class ManageBook {
    private VBox manageBookLayout;

    public ManageBook() {
        manageBookLayout = new VBox(10); // Spacing antar elemen utama VBox
        manageBookLayout.setPadding(new Insets(0));
        manageBookLayout.setAlignment(Pos.TOP_CENTER); // Pusatkan konten secara vertikal

        // --- Top Cards (BORROW, OPEN, ALL) ---
        HBox topCards = new HBox(40); // Spacing 20 antar kartu
        topCards.setAlignment(Pos.CENTER); // Pusatkan HBox ini secara horizontal

        VBox borrowCard = createInfoCard("BORROW", "253", "#6a73ee"); // Biru
        VBox openCard = createInfoCard("OPEN", "1.061", "#58d68d");   // Hijau
        VBox allCard = createInfoCard("ALL", "1.414", "#8e8e8e");     // Abu-abu

        topCards.getChildren().addAll(borrowCard, openCard, allCard);
        manageBookLayout.getChildren().add(topCards);

        // --- Book Data Table Card ---
        VBox bookDataTableCard = createBookDataTableCard("Book data in this E-Library");
        HBox centerTableContainer = new HBox();
        centerTableContainer.setAlignment(Pos.CENTER);
        centerTableContainer.getChildren().add(bookDataTableCard);
        manageBookLayout.getChildren().add(centerTableContainer);

        // --- Data Graph of Book in this E-Library Card ---
        VBox bookGraphCard = createChartCard(
//            "Data graph of Book in this E-Library",
//            "images/BarLineChart-2.png", // <--- Ganti dengan nama file gambar placeholder Anda
            964, // Lebar chart di dalam card
            280  // Tinggi chart di dalam card
        );
        Label titleLabel = new Label("Data Graph of Book in this E-Library");
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22)); // Font judul chart
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.setAlignment(Pos.TOP_LEFT);
        HBox centerGraphContainer = new HBox();
        centerGraphContainer.setAlignment(Pos.CENTER);
        centerGraphContainer.getChildren().add(bookGraphCard);
        manageBookLayout.getChildren().add(titleLabel);
        manageBookLayout.getChildren().add(centerGraphContainer);
    }

    // Metode untuk membuat kartu informasi (BORROW, OPEN, ALL)
    private VBox createInfoCard(String title, String value, String backgroundColor) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10, 20, 10, 20)); // Padding disesuaikan
        card.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 12;");
        card.setPrefSize(180, 100); // Ukuran card lebih kecil

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 16));
        titleLabel.setTextFill(Color.WHITE);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 36)); // Ukuran font nilai
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setPadding(new Insets(5, 0, 0, 0));

        card.getChildren().addAll(titleLabel, valueLabel);
        card.setAlignment(Pos.CENTER_LEFT); // Rata kiri konten dalam kartu

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.6));
        shadow.setRadius(8);
        shadow.setOffsetX(0);
        shadow.setOffsetY(3);
        card.setEffect(shadow);

        return card;
    }

    // Metode untuk membuat kartu tabel data buku
    private VBox createBookDataTableCard(String cardTitle) {
        VBox card = new VBox(0); // Spacing 0
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.15)));
        card.setMaxHeight(250);
        // Header biru untuk judul kartu
        HBox titleHeader = new HBox();
        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;");
        titleHeader.setPadding(new Insets(15, 20, 15, 20));
        titleHeader.setAlignment(Pos.CENTER_LEFT);
        titleHeader.setPrefHeight(60);

        Label titleLabel = new Label(cardTitle);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);
        titleHeader.getChildren().add(titleLabel);

        // Placeholder untuk Tabel
        TableView<BookDataEntry> tableView = new TableView<>();
        tableView.setPrefSize(960, 400); // Ukuran tabel disesuaikan dengan gambar
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Agar kolom menyesuaikan lebar tabel
        tableView.setPadding(new Insets(20)); // Padding di sekitar tabel di dalam kartu
        tableView.setStyle("-fx-background-color: white; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0;"); // Styling tabel

        // Contoh Kolom (Anda perlu menyesuaikan ini dengan data buku Anda)
        TableColumn<BookDataEntry, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleCol.setPrefWidth(250);

        TableColumn<BookDataEntry, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        titleCol.setPrefWidth(200);

        TableColumn<BookDataEntry, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        titleCol.setPrefWidth(100);

        TableColumn<BookDataEntry, String> borrowedByCol = new TableColumn<>("Borrowed By");
        borrowedByCol.setCellValueFactory(cellData -> cellData.getValue().borrowedByProperty());
        titleCol.setPrefWidth(200);

        tableView.getColumns().addAll(titleCol, authorCol, statusCol, borrowedByCol);

        // Data dummy
        ObservableList<BookDataEntry> data = FXCollections.observableArrayList(
                new BookDataEntry("The Great Gatsby", "F. Scott Fitzgerald", "Available", "-"),
                new BookDataEntry("1984", "George Orwell", "Borrowed", "John Doe"),
                new BookDataEntry("To Kill a Mockingbird", "Harper Lee", "Available", "-"),
                new BookDataEntry("Pride and Prejudice", "Jane Austen", "Borrowed", "Jane Smith"),
                new BookDataEntry("The Catcher in the Rye", "J.D. Salinger", "Available", "-")
        );
        tableView.setItems(data);


        card.getChildren().addAll(titleHeader, tableView);
        return card;
    }

    // Metode yang sama dari DashboardPage untuk membuat kartu chart (placeholder)
    private VBox createChartCard(double chartWidth, double chartHeight) {
        VBox card = new VBox(0); // Spacing 0 karena konten akan diatur paddingnya sendiri
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;"); // Background putih, rounded
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.15))); // Efek bayangan
        card.setPrefSize(964, 300);
        card.setMaxSize(964, 300);
        // Header biru untuk judul chart
//        HBox titleHeader = new HBox();
//        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;"); // Biru, rounded atas saja
//        titleHeader.setPadding(new Insets(15, 20, 15, 20)); // Padding judul
//        titleHeader.setAlignment(Pos.CENTER_LEFT);
//        titleHeader.setPrefHeight(60); // Tinggi header
//
//        
        HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.TOP_CENTER);
        // ImageView placeholder chart
//        ImageView chartImageView = createChartPlaceholder(imagePath, chartWidth, chartHeight);
        imageContainer.setPadding(new Insets(15, 5, 5, 5));  // Margin di sekitar gambar chart di dalam card
//        imageContainer.getChildren().add(chartImageView);
//        card.getChildren().addAll(titleHeader, imageContainer);

        card.getChildren().add(imageContainer);
        return card;
    }

    // Metode untuk membuat ImageView sebagai placeholder chart
    private ImageView createChartPlaceholder(String imagePath, double fitWidth, double fitHeight) {
        
        ImageView imageView;
        Image image = loadImage(imagePath);
       
        imageView = new ImageView(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");
        return imageView;
    }
    private static Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new Image(ManageBook.class.getResourceAsStream("images/placeholder_book.png"));
        }
        try {
            Image img = new Image(ManageBook.class.getResourceAsStream(imagePath));
            if (img.isError() && !imagePath.startsWith("/")) {
                File file = new File(imagePath);
                if (file.exists()) {
                    return new Image(new FileInputStream(file));
                } else {
                    System.err.println("Failed to load image from resource or file system: " + imagePath);
                    return new Image(ManageBook.class.getResourceAsStream("/images/placeholder_book.png"));
                }
            } else if (img.isError()) {
                 System.err.println("Failed to load image from classpath resource: " + imagePath);
                 return new Image(ManageBook.class.getResourceAsStream("/images/placeholder_book.png"));
            }
            return img;
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Failed to load image " + imagePath + ": " + e.getMessage());
            return new Image(ManageBook.class.getResourceAsStream("images/B001.png"));
        }
    }

    public VBox getView() {
        return manageBookLayout;
    }

    // Kelas model untuk data Tabel Buku
    public static class BookDataEntry {
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty status;
        private final SimpleStringProperty borrowedBy;

        public BookDataEntry(String title, String author, String status, String borrowedBy) {
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.status = new SimpleStringProperty(status);
            this.borrowedBy = new SimpleStringProperty(borrowedBy);
        }

        public String getTitle() { return title.get(); }
        public SimpleStringProperty titleProperty() { return title; }
        public String getAuthor() { return author.get(); }
        public SimpleStringProperty authorProperty() { return author; }
        public String getStatus() { return status.get(); }
        public SimpleStringProperty statusProperty() { return status; }
        public String getBorrowedBy() { return borrowedBy.get(); }
        public SimpleStringProperty borrowedByProperty() { return borrowedBy; }
    }
}