package resource.view.user;

import data.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

        HBox content = new HBox(10);
        content.getChildren().add(bookGrid);
        content.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(content);
        VBox.setVgrow(bookGrid, Priority.ALWAYS);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        displayBooks();
    }

    private void displayBooks()  {
        bookGrid.getChildren().clear();
        for (int i = 0; i < 10; i +=5) {
            List<Book> group = new ArrayList<>();
            for (int j = 0; j < 5 && (i + j) < books.size(); j++) {
                group.add(books.get(i + j));
            }
            bookGrid.getChildren().add(CardUtils.createFourBookCoversCard(group, onBookDetailsRequested, "Selengkapnya"));
        }
    }
    
    public void refreshData()  {
        System.out.println("Refreshing Data Book Page...");
    }
}