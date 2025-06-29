package main.java.UI.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.java.UI.user.model.book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataBookPage extends VBox {

    private final FlowPane bookGrid;
    private final Consumer<book> onBookDetailsRequested;
    private List<book> books;
    public DataBookPage(Consumer<book> onBookDetailsRequested)  {
        this.onBookDetailsRequested = onBookDetailsRequested;
        this.setPadding(new Insets(23.5));
        this.setStyle("-fx-background-color: #f4f4f4;");
        VBox.setVgrow(this, Priority.ALWAYS);
        
        bookGrid = new FlowPane(20, 35); 
        bookGrid.setPadding(new Insets(10));
        bookGrid.setAlignment(Pos.TOP_LEFT);
        loadDummyBooks();
        this.getChildren().add( bookGrid);
        VBox.setVgrow(bookGrid, Priority.ALWAYS);
    }

    private void loadDummyBooks() {
        books = new ArrayList<>();
        books.add(new book("B001", "Habis Gelap Terbitlah Terang", "9786022603283","Armijn Pane", "/main/java/UI/user/images/B001.png",15)); 
        books.add(new book("B005", "Hitler's Spies", "123", "David Kahn",  "/main/java/UI/user/images/B005.png",4)); 
        books.add(new book("B002", "Arthur's April Fool", "123", "Marc Brown",  "/main/java/UI/user/images/B002.png",3)); 
        books.add(new book("B008", "Architecture The Whole Story", "123", "Jonathan Glancey", "/main/java/UI/user/images/B008.png",10)); 
        books.add(new book("B007", "The Locked Door", "123", "Freida McFadden",  "/main/java/UI/user/images/B007.png",  2)); 
        books.add(new book("B003", "Anne of Green Gables", "123", "L. M. Montgomery",  "/main/java/UI/user/images/B003.png",8));
        books.add(new book("B004", "Ars Magna", "123", "Gerolamo Cardano",  "/main/java/UI/user/images/B004.png",  6));
        books.add(new book("B006", "How I Survived Middle School", "123", "Nancy Krulik", "/main/java/UI/user/images/B006.png",7));


        displayBooks();
    }
    
    private void displayBooks()  {
        bookGrid.getChildren().clear();
        for (int i = 0; i < 8; i +=4) { 
            List<book> group = new ArrayList<>();
            for (int j = 0; j < 4 && (i + j) < books.size(); j++) {
                group.add(books.get(i + j));
            }
            bookGrid.getChildren().add(CardUtils.createFourBookCoversCard(group, onBookDetailsRequested, "Selengkapnya"));
        }
    }
    
    public void refreshData()  {
        System.out.println("Refreshing Data Book Page...");
        loadDummyBooks();
    }
}