package resource.view.admin.book;

import data.Book;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Index;
import resource.components.CardHeader;

import java.util.List;
import java.util.function.Consumer;

public class ManageBook extends VBox {

    private TableView<Book> table;

    private Label totalBook;
    private Label totalBorrow;
    private Label totalReturn;

//    private Book_Model modelBook;
//    private Book_Model model ;

    public ManageBook(Index app, Consumer<ActionEvent> onAddBook){
        this.setStyle("-fx-background-color: #f5f5f5;");
        this.setSpacing(10);
        this.setPadding(new Insets(20));

        HBox statCards = new HBox(20);
        statCards.setAlignment(Pos.CENTER);
        statCards.setPadding(new Insets(0,0,20,0));

        VBox totalBookCard = createCard("BORROW", "0", "#FFC107"); // Warna kuning-oranye
        totalBook = (Label) totalBookCard.getChildren().get(1);

        VBox returnBookCard = createCard("OPEN", "0", "#28a745"); // Warna hijau
        totalReturn = (Label) returnBookCard.getChildren().get(1);

        VBox borrowBookCard = createCard("ALL", "0", "#007bff"); // Warna biru
        totalBorrow = (Label) borrowBookCard.getChildren().get(1);

        statCards.getChildren().addAll(totalBookCard, returnBookCard, borrowBookCard);

//        Label dataBookLabel = new Label("Data Book");
//        dataBookLabel.setStyle("-fx-background-color: #3f48cc; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10 10 0 0;");
//        dataBookLabel.setPadding(new Insets(10));
//        dataBookLabel.setMaxWidth(Double.MAX_VALUE);
        HBox header = new CardHeader("Manaege Book", "Add Book", "#3f48cc", "#007bff", onAddBook);

        table = new TableView<>();
        table.setPrefHeight(200);

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

        VBox tableBox = new VBox(header, table);
//        VBox tableBox = new VBox(dataBookLabel, table);
        tableBox.setStyle("-fx-background-color: #f8f8f8; -fx-background-radius: 0 10; -fx-border-radius: 0 10;");
        tableBox.setPadding(new Insets(10));

        this.getChildren().addAll(statCards, tableBox);

        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);

    }

    private VBox createCard(String title, String initialValue, String borderColor) {
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.BLACK); // <--- Teks hitam
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // Tetap tebal

        Label valueLabel = new Label(initialValue);
        valueLabel.setTextFill(Color.BLACK); // <--- Teks hitam
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Tetap tebal

        VBox box = new VBox(5, titleLabel, valueLabel);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER_LEFT);

        // --- PENTING: Perubahan styling di sini ---
        box.setStyle("-fx-background-color: white; " + // Latar belakang putih
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + borderColor + "; " + // Border berwarna
                "-fx-border-width: 2px; " + // Ketebalan border
                "-fx-border-radius: 8px;"); // Radius border

        box.setPrefSize(180, 100);

        return box;
    }

    public void setBooks(List<Book> books){
//        List<Book> list = books;
        table.setItems(FXCollections.observableList(books));
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook.setText(String.valueOf(totalBook));
    }

    public void setTotalBorrow(Integer totalBorrow) {
        this.totalBorrow.setText(String.valueOf(totalBorrow));
    }

    public void setTotalReturn(Integer totalReturn) {
        this.totalReturn.setText(String.valueOf(totalReturn));
    }
}
