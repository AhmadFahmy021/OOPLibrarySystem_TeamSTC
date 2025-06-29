package main.java.UI.admin;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ManageAdmin {
    private VBox manageAdminLayout;

    public ManageAdmin() {
        manageAdminLayout = new VBox(20);
        manageAdminLayout.setPadding(new Insets(20));
        VBox bookDataTableCard = createBookDataTableCard("Manage Admin");
        HBox centerTableContainer = new HBox();
        centerTableContainer.setAlignment(Pos.CENTER);
        centerTableContainer.getChildren().add(bookDataTableCard);
        manageAdminLayout.getChildren().add(centerTableContainer);
    }
    private VBox createBookDataTableCard(String cardTitle) {
        VBox card = new VBox(0); // Spacing 0
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.15)));
        card.setMaxHeight(500);
        // Header biru untuk judul kartu
        HBox titleHeader = new HBox();
        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;");
        titleHeader.setPadding(new Insets(15, 20, 15, 20));
        titleHeader.setAlignment(Pos.CENTER_LEFT);
        titleHeader.setPrefHeight(60);
        VBox returnButton = new VBox(10);
        returnButton.setAlignment(Pos.TOP_RIGHT);
        Label returnlabel = new Label("Add Admin");
        returnlabel.setFont(Font.font("Montserrat",FontWeight.BOLD,22));
        returnlabel.setTextFill(Color.BLACK );
        returnlabel.setStyle("-fx-background-color: #d9cf19;-fx-border-radius: 5;-fx-background-radius:3;-fx-padding: 3 8");
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
    
    public VBox getView() {
        return manageAdminLayout;
    }
}