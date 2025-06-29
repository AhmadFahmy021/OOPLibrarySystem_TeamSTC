package main.java.UI.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ReturnBook {
    private VBox returnBookLayout;

    public ReturnBook() {
        returnBookLayout = new VBox(50); // Spacing antar elemen utama VBox
        returnBookLayout.setPadding(new Insets(20));
        returnBookLayout.setAlignment(Pos.TOP_CENTER); // Pusatkan konten secara vertikal

        // --- Book Data Table Card ---
        VBox bookDataTableCard = createBookDataTableCard("Book data in this E-Library");
        HBox centerTableContainer = new HBox();
        centerTableContainer.setAlignment(Pos.CENTER);
        centerTableContainer.getChildren().add(bookDataTableCard);
        returnBookLayout.getChildren().add(centerTableContainer);

        // --- Data Graph of Book in this E-Library Card ---
        VBox bookGraphCard = createChartCard(
            "Book Graph Returned",
//            "images/BarLineChart-2.png", // <--- Ganti dengan nama file gambar placeholder Anda
            964, // Lebar chart di dalam card
            450  // Tinggi chart di dalam card
        );
        
        HBox centerGraphContainer = new HBox();
        centerGraphContainer.setAlignment(Pos.CENTER);
        centerGraphContainer.getChildren().add(bookGraphCard);
        returnBookLayout.getChildren().add(centerGraphContainer);

    }

    // Kelas model untuk data BorrowedBook
    public static class BorrowedBook {
        private final SimpleStringProperty borrowId;
        private final SimpleStringProperty memberName;
        private final SimpleStringProperty bookTitle;
        private final SimpleStringProperty borrowDate;
        private final SimpleStringProperty returnDate;
        private final SimpleStringProperty status;

        public BorrowedBook(String borrowId, String memberName, String bookTitle, String borrowDate, String returnDate, String status) {
            this.borrowId = new SimpleStringProperty(borrowId);
            this.memberName = new SimpleStringProperty(memberName);
            this.bookTitle = new SimpleStringProperty(bookTitle);
            this.borrowDate = new SimpleStringProperty(borrowDate);
            this.returnDate = new SimpleStringProperty(returnDate);
            this.status = new SimpleStringProperty(status);
        }

        public String getBorrowId() { return borrowId.get(); }
        public String getMemberName() { return memberName.get(); }
        public String getBookTitle() { return bookTitle.get(); }
        public String getBorrowDate() { return borrowDate.get(); }
        public String getReturnDate() { return returnDate.get(); }
        public String getStatus() { return status.get(); }
    }
    
    
    private VBox createBookDataTableCard(String cardTitle) {
        VBox card = new VBox(0); // Spacing 0
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.15)));
        card.setMaxHeight(300);
        // Header biru untuk judul kartu
        HBox titleHeader = new HBox();
        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;");
        titleHeader.setPadding(new Insets(15, 20, 15, 20));
        titleHeader.setAlignment(Pos.CENTER_LEFT);
        titleHeader.setPrefHeight(60);
        VBox returnButton = new VBox(10);
        returnButton.setAlignment(Pos.TOP_RIGHT);
        Label returnlabel = new Label("Add Return Book");
        returnlabel.setFont(Font.font("Montserrat",FontWeight.BOLD,22));
        returnlabel.setTextFill(Color.WHITE);
        returnlabel.setStyle("-fx-background-color: #1b8d4a;-fx-border-radius: 5;-fx-background-radius:3;-fx-padding: 3 8");
        returnButton.setStyle("-fx-border-radius: 5; -fx-background-radius:3;-fx-padding: 3 8");
        returnlabel.setAlignment(Pos.CENTER); 
        returnButton.getChildren().add(returnlabel);
        Region spacerTitle = new Region();
        HBox.setHgrow(spacerTitle, Priority.ALWAYS); 
                Label titleLabel = new Label(cardTitle);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);
        
        titleHeader.getChildren().add(titleLabel);
        titleHeader.getChildren().add(spacerTitle);
        titleHeader.getChildren().add(returnButton);

        // Placeholder untuk Tabel
        TableView<ManageBook.BookDataEntry> tableView = new TableView<>();
        tableView.setPrefSize(960, 400); // Ukuran tabel disesuaikan dengan gambar
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Agar kolom menyesuaikan lebar tabel
        tableView.setPadding(new Insets(20)); // Padding di sekitar tabel di dalam kartu
        tableView.setStyle("-fx-background-color: white; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0;"); // Styling tabel

        // Contoh Kolom (Anda perlu menyesuaikan ini dengan data buku Anda)
        TableColumn<ManageBook.BookDataEntry, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleCol.setPrefWidth(250);

        TableColumn<ManageBook.BookDataEntry, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        titleCol.setPrefWidth(200);

        TableColumn<ManageBook.BookDataEntry, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        titleCol.setPrefWidth(100);

        TableColumn<ManageBook.BookDataEntry, String> borrowedByCol = new TableColumn<>("Borrowed By");
        borrowedByCol.setCellValueFactory(cellData -> cellData.getValue().borrowedByProperty());
        titleCol.setPrefWidth(200);

        tableView.getColumns().addAll(titleCol, authorCol, statusCol, borrowedByCol);

        // Data dummy
        ObservableList<ManageBook.BookDataEntry> data = FXCollections.observableArrayList(
                new ManageBook.BookDataEntry("The Great Gatsby", "F. Scott Fitzgerald", "Available", "-"),
                new ManageBook.BookDataEntry("1984", "George Orwell", "Borrowed", "John Doe"),
                new ManageBook.BookDataEntry("To Kill a Mockingbird", "Harper Lee", "Available", "-"),
                new ManageBook.BookDataEntry("Pride and Prejudice", "Jane Austen", "Borrowed", "Jane Smith"),
                new ManageBook.BookDataEntry("The Catcher in the Rye", "J.D. Salinger", "Available", "-")
        );
        tableView.setItems(data);


        card.getChildren().addAll(titleHeader, tableView);
        return card;
    }
    
    private VBox createChartCard(String chartTitle, double chartWidth, double chartHeight) {
        VBox card = new VBox(0); // Spacing 0 karena konten akan diatur paddingnya sendiri
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;"); // Background putih, rounded
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.60))); // Efek bayangan
        card.setPrefSize(964, 300);
        card.setMaxSize(964, 300);
        // Header biru untuk judul chart
        HBox titleHeader = new HBox();
        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;"); // Biru, rounded atas saja
        titleHeader.setPadding(new Insets(15, 20, 15, 20)); // Padding judul
        titleHeader.setAlignment(Pos.CENTER_LEFT);
        titleHeader.setPrefHeight(60); // Tinggi header

        Label titleLabel = new Label(chartTitle);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22)); // Font judul chart
        titleLabel.setTextFill(Color.WHITE);
        titleHeader.getChildren().add(titleLabel);
        HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.TOP_CENTER);
        // ImageView placeholder chart
//        ImageView chartImageView = createChartPlaceholder(imagePath, chartWidth, chartHeight);
        imageContainer.setPadding(new Insets(15, 5, 5, 5));  // Margin di sekitar gambar chart di dalam card
//        imageContainer.getChildren().add(chartImageView);
        card.getChildren().addAll(titleHeader, imageContainer);
        return card;
    }

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
            return new Image(ReturnBook.class.getResourceAsStream("images/placeholder_book.png"));
        }
        try {
            Image img = new Image(ReturnBook.class.getResourceAsStream(imagePath));
            if (img.isError() && !imagePath.startsWith("/")) {
                File file = new File(imagePath);
                if (file.exists()) {
                    return new Image(new FileInputStream(file));
                } else {
                    System.err.println("Failed to load image from resource or file system: " + imagePath);
                    return new Image(ReturnBook.class.getResourceAsStream("images/BarLineChart.png"));
                }
            } else if (img.isError()) {
                 System.err.println("Failed to load image from classpath resource: " + imagePath);
                 return new Image(ReturnBook.class.getResourceAsStream("images/BarLineChart.png"));
            }
            return img;
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Failed to load image " + imagePath + ": " + e.getMessage());
            return new Image(ReturnBook.class.getResourceAsStream("images/BarLineChart.png"));
        }
    }
    public VBox getView() {
        return returnBookLayout;
    }
}