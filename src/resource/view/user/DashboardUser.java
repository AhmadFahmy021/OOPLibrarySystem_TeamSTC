package resource.view.user;

import data.Book;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures; // <--- PASTIKAN BARIS INI ADA!
import main.Index;

import java.util.List;

public class DashboardUser extends VBox {

    private List<Book> books;
    private TableView<Book> table;

    private Label totalBookValueLabel;
    private Label totalBorrowValueLabel;
    private Label totalReturnValueLabel;

    public DashboardUser(Index app){

        this.setStyle("-fx-background-color: #f5f5f5;");
        this.setSpacing(10);
        this.setPadding(new Insets(20));

        HBox statCards = new HBox(20);
        statCards.setAlignment(Pos.CENTER);

        VBox totalBookCard = createUpdatableCard("Total Book", "#3f48cc");
        totalBookValueLabel = (Label) totalBookCard.getChildren().get(1); // Dapatkan Label nilai

        VBox returnBookCard = createUpdatableCard("Return Book", "#e60000");
        totalReturnValueLabel = (Label) returnBookCard.getChildren().get(1); // Dapatkan Label nilai

        VBox borrowBookCard = createUpdatableCard("Borrow Book", "#00cc66");
        totalBorrowValueLabel = (Label) borrowBookCard.getChildren().get(1);

        statCards.getChildren().addAll(totalBookCard, returnBookCard, borrowBookCard);

        Label dataBookLabel = new Label("Data Book");
        dataBookLabel.setStyle("-fx-background-color: #3f48cc; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10 10 0 0;");
        dataBookLabel.setPadding(new Insets(10));
        dataBookLabel.setMaxWidth(Double.MAX_VALUE);

        table = new TableView<>();
        table.setPrefHeight(500);

        TableColumn<Book, String> colNo = new TableColumn<>("No");
        colNo.setCellValueFactory(cellData -> {
            int index = table.getItems().indexOf(cellData.getValue()) + 1;
            return new ReadOnlyStringWrapper(String.valueOf(index));
        });
        colNo.setPrefWidth(50);

        TableColumn<Book, String> colISBN = new TableColumn<>("ISBN");
        colISBN.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();
            String value;
            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                value = book.getIsbn();
            } else {
                value = (book.getId_book() != 0) ? String.valueOf(book.getId_book()) : "N/A";
            }
            return new ReadOnlyStringWrapper(value);
        });
        colISBN.setPrefWidth(120);

        TableColumn<Book, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();
            return new ReadOnlyStringWrapper(book.getTitle());
        });
        colTitle.setPrefWidth(200);

        TableColumn<Book, String> colAuthor = new TableColumn<>("Author");
        colAuthor.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();
            return new ReadOnlyStringWrapper(book.getAuthor());
        });
        colAuthor.setPrefWidth(150);

        TableColumn<Book, String> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();
            return new ReadOnlyStringWrapper(String.valueOf(book.getQuantity()));
        });
        colQuantity.setPrefWidth(80);

        table.getColumns().addAll(colNo, colISBN, colTitle, colAuthor, colQuantity);

        VBox tableBox = new VBox(dataBookLabel, table);
//        dataBookLabel.setStyle("-fx-background-radius: 10 10 0 0;");
//        tableBox.setStyle("-fx-background-color: #ffffff;");
        tableBox.setPadding(new Insets(10));
//        table.setOnMouseClicked(e-);

        table.setStyle("-fx-background-radius: 0 0 10 10; -fx-border: #000000 1px; -fx-border-radius: 0 0 10 10;");

        this.getChildren().addAll(statCards, tableBox);

        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private VBox createUpdatableCard(String title, String color) {
        Label titleLabel = new Label(title.toUpperCase());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Label nilai ini akan diisi dengan "0" pada awalnya dan kemudian diupdate
        Label valueLabel = new Label("0"); // Nilai awal
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setStyle("-fx-font-size: 44px; -fx-font-weight: bold;");

        VBox box = new VBox(5, titleLabel, valueLabel);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 8px;");
        return box;
    }

    private void styleSidebarButton(Button btn, boolean active) {
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + (active ? "#9aaaff" : "transparent") + "; -fx-text-fill: white;");
    }

    public void setBooks(List<Book> books){
        // Mengonversi List<Book> ke ObservableList<Book> dan set ke TableView
        System.out.println("cek jumlah buku di teriman"+books.size());
        table.setItems(FXCollections.observableArrayList(books));
        System.out.println("DEBUG: Displaying " + books.size() + " books in DataBookPage.");
    }

    public void setTotalBuku(int totalBook){
        this.totalBookValueLabel.setText(String.valueOf(totalBook));
    }

    public void setTotalBorrow(int totalBorrow){
        this.totalBorrowValueLabel.setText(String.valueOf(totalBorrow));
    }

    public void setTotalReturn(int totalReturn){
        this.totalReturnValueLabel.setText(String.valueOf(totalReturn));
    }
}
