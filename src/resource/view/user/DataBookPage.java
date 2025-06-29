package resource.view.user;

import data.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import resource.components.CardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataBookPage extends VBox {

    private final FlowPane bookGrid;
    private final Consumer<Book> onBookDetailsRequested;
    private List<Book> books;

    public DataBookPage(Consumer<Book> onBookDetailsRequested)  {
        this.onBookDetailsRequested = onBookDetailsRequested;
        this.setPadding(new Insets(23.5));
        this.setStyle("-fx-background-color: #f4f4f4;");
        VBox.setVgrow(this, Priority.ALWAYS);
        
        bookGrid = new FlowPane(20, 35); 
        bookGrid.setPadding(new Insets(10));
        bookGrid.setAlignment(Pos.TOP_LEFT);

        ScrollPane sp = new ScrollPane();
        sp.setContent(bookGrid);
//        sp.setPadding(new Insets(10));
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        HBox content = new HBox(10);
        content.getChildren().add(sp);
//        content.getChildren().add(bookGrid);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(content);
        VBox.setVgrow(bookGrid, Priority.ALWAYS);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        displayBooks();
    }

//    private void displayBooks()  {
//        bookGrid.getChildren().clear();
////        bookGrid.getChildren().add(CardUtils.createFourBookCoversCard(books, onBookDetailsRequested, "Selengkapnya"));
//        int booksbagi = books.size() / 2;
//        for (int i = 0; i < books.size(); i +=booksbagi) {
//            List<Book> group = new ArrayList<>();
//            for (int j = 0; j < booksbagi && (i + j) < books.size(); j++) {
//                group.add(books.get(i + j));
//            }
//            bookGrid.getChildren().add(CardUtils.createFourBookCoversCard(group, onBookDetailsRequested, "Selengkapnya"));
//        }
////        for (int i = 0; i < 10; i +=5) {
////            List<Book> group = new ArrayList<>();
////            for (int j = 0; j < 5 && (i + j) < books.size(); j++) {
////                group.add(books.get(i + j));
////            }
////            bookGrid.getChildren().add(CardUtils.createFourBookCoversCard(group, onBookDetailsRequested, "Selengkapnya"));
////        }
//    }

private void displayBooks()  {
    bookGrid.getChildren().clear(); // Bersihkan grid sebelumnya

    if (books != null && !books.isEmpty()) {
        System.out.println("DEBUG: Displaying " + books.size() + " books in DataBookPage.");

        // --- Perbaiki logika looping pengelompokan ---
        // Loop ini akan membuat KARTU BESAR (dari createFourBookCoversCard)
        // untuk setiap grup 4 buku (atau jumlah sisa di akhir)
        final int GROUP_SIZE = 4; // Sesuaikan dengan berapa buku per kartu besar yang Anda inginkan
        // Asumsi createFourBookCoversCard memang menampilkan 4 buku

        for (int i = 0; i < books.size(); i += GROUP_SIZE) {
            List<Book> group = new ArrayList<>();
            // Ambil buku untuk grup saat ini
            for (int j = 0; j < GROUP_SIZE && (i + j) < books.size(); j++) {
                group.add(books.get(i + j));
            }

            // Tambahkan kartu grup ke FlowPane
            VBox card = CardUtils.createFourBookCoversCard(group, onBookDetailsRequested, "Selengkapnya");
            if (card != null) {
                bookGrid.getChildren().add(card);
            } else {
                System.err.println("DEBUG: CardUtils.createFourBookCoversCard returned null for group starting at index: " + i);
            }
        }
    } else {
        System.out.println("DEBUG: No books to display in DataBookPage. Showing 'No books available' message.");
        Label noBooksLabel = new Label("No books available.");
        noBooksLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #777;");
        VBox centeredMessage = new VBox(noBooksLabel);
        centeredMessage.setAlignment(Pos.CENTER);
        // Penting: Atur PrefSize agar pesan ini memiliki "area" di dalam FlowPane
        centeredMessage.setPrefSize(bookGrid.getPrefWidth(), bookGrid.getPrefHeight()); // Menggunakan prefWidth/Height FlowPane
        bookGrid.getChildren().add(centeredMessage);
    }
}
    
    public void refreshData()  {
        System.out.println("Refreshing Data Book Page...");
    }
}