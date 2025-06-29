package main.java.UI.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.UI.user.model.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.FlowPane;

public class BookDetailPage extends VBox {

    private book currentBook;
    private ImageView coverView;
    private Label titleLabel;
    private Label isbnLabel; 
    private Label authorLabel;
    private Label stockLabel;
    private FlowPane relatedBooksFlowPane;

    private Consumer<book> onBorrowBookRequested;
    private Consumer<book> onBookDetailsAndBorrowRequested;

    public BookDetailPage(Consumer<book> onBorrowBookRequested, Consumer<book> onBookDetailsAndBorrowRequested) {
        this.onBorrowBookRequested = onBorrowBookRequested;
        this.onBookDetailsAndBorrowRequested = onBookDetailsAndBorrowRequested;

        this.setPadding(new Insets(16));
        this.setSpacing(16);
        this.setStyle("-fx-background-color: #f8f9fa;");
        VBox.setVgrow(this, Priority.ALWAYS);

        // -- Bagian atas: Detail Buku Utama --
        VBox mainDetailCard = new VBox(15);
        mainDetailCard.setPadding(new Insets(20));
        mainDetailCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #ddd; -fx-border-width: 1;");
        VBox.setVgrow(mainDetailCard, Priority.ALWAYS);

        HBox topInfoContainer = new HBox(70);
        topInfoContainer.setAlignment(Pos.TOP_LEFT);
        topInfoContainer.setMaxWidth(1046);
        topInfoContainer.setMinWidth(1046);
        topInfoContainer.setPrefWidth(1046);
        coverView = new ImageView();
        coverView.setFitWidth(200);
        coverView.setFitHeight(266);
        coverView.setPreserveRatio(true);
        VBox.setMargin(coverView, new Insets(0, 0, 0, 10)); 
        VBox infoContainer = new VBox(3); 
        infoContainer.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(infoContainer, Priority.ALWAYS);
        titleLabel = new Label();
        titleLabel.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 32));
        titleLabel.setWrapText(true);
        VBox.setMargin(titleLabel, new Insets(0, 0, 10, 0)); 
       
        isbnLabel = new Label();
        isbnLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        isbnLabel.setStyle("-fx-text-fill: #000000;");

        authorLabel = new Label();
        authorLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        authorLabel.setStyle("-fx-text-fill: #000000;");

        stockLabel = new Label();
        stockLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        stockLabel.setStyle("-fx-text-fill: #000000;");

        
        HBox actionButtons = new HBox(90);
        actionButtons.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(actionButtons, new Insets(10, 0, 0, 0)); 
        Button fullDetailsButton = new Button("Detail Book"); 
        fullDetailsButton.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 32));
        fullDetailsButton.setStyle("-fx-background-color: #4750ec; -fx-text-fill: white; -fx-padding: 3 30; -fx-background-radius: 50;");
        fullDetailsButton.setOnAction(e -> {
            if (currentBook != null) {
                onBookDetailsAndBorrowRequested.accept(currentBook);
            }
        });

        Button borrowNowButton = new Button("Borrow Now"); 
        borrowNowButton.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 32));
        borrowNowButton.setStyle("-fx-background-color: #23d957; -fx-text-fill: white; -fx-padding: 3 30; -fx-background-radius: 50;");
        borrowNowButton.setOnAction(e -> {
            if (currentBook != null) {
                onBorrowBookRequested.accept(currentBook);
            }
        });
        actionButtons.getChildren().addAll(fullDetailsButton, borrowNowButton);
        
        
        infoContainer.getChildren().addAll(titleLabel, isbnLabel, authorLabel, stockLabel, actionButtons);
        topInfoContainer.getChildren().addAll(coverView, infoContainer);
        mainDetailCard.getChildren().add(topInfoContainer); 

        Label relatedBooksHeader = new Label("Other book"); 
        relatedBooksHeader.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 32));
        VBox.setMargin(relatedBooksHeader, new Insets(7, 0, 0, 0));
        relatedBooksHeader.setMaxWidth(Double.MAX_VALUE);
        relatedBooksHeader.setAlignment(Pos.CENTER);

        relatedBooksFlowPane = new FlowPane(20, 35);
        relatedBooksFlowPane.setPadding(new Insets(10));
        relatedBooksFlowPane.setAlignment(Pos.TOP_CENTER);
        
        this.getChildren().addAll(mainDetailCard, relatedBooksHeader, relatedBooksFlowPane);
        VBox.setVgrow(relatedBooksFlowPane, Priority.ALWAYS);
    }

    public void setBook(book book) {
        this.currentBook = book;
        if (book != null) {
            titleLabel.setText(book.getTitle());
            isbnLabel.setText("ISBN                           : " + book.getISBN());
            authorLabel.setText("Author                        : " + book.getAuthor());
            stockLabel.setText("Quantity                    : " + book.getStock());

            Image coverImage = null;
            try {
                coverImage = new Image(getClass().getResourceAsStream(book.getCoverImagePath()));
                if (coverImage.isError() && !book.getCoverImagePath().startsWith("file:")) {
                    throw new IllegalArgumentException("Resource not found or failed to load: " + book.getCoverImagePath());
                }
            } catch (Exception e) {
                try {
                    File file = new File(book.getCoverImagePath());
                    if (file.exists() && file.isFile()) {
                        coverImage = new Image(new FileInputStream(file));
                    } else {
                        throw new FileNotFoundException("File not found: " + book.getCoverImagePath());
                    }
                } catch (FileNotFoundException | IllegalArgumentException fe) {
                    System.err.println("Failed to load image for " + book.getTitle() + " from both resource and file: " + fe.getMessage());
                    coverImage = new Image(getClass().getResourceAsStream("/images/placeholder_book.png"));
                }
            }
            coverView.setImage(coverImage);
            
            loadRelatedBooks();
        } else {
            titleLabel.setText("No Book Selected");
            isbnLabel.setText("");
            authorLabel.setText("");
            stockLabel.setText("");
            coverView.setImage(null);
            relatedBooksFlowPane.getChildren().clear();
        }
    }

    private void loadRelatedBooks() {
        List<book> allBooks = new ArrayList<>();
        allBooks.add(new book("B001", "Habis Gelap Terbitlah Terang", "9786022603283","Armijn Pane", "images/B001.png",15)); 
        allBooks.add(new book("B005", "Hitler's Spies", "123", "David Kahn",  "images/B005.png",4)); 
        allBooks.add(new book("B002", "Arthur's April Fool", "123", "Marc Brown",  "images/B002.png",3)); 
        allBooks.add(new book("B008", "Architecture The Whole Story", "123", "Jonathan Glancey", "images/B008.png",10));
        allBooks.add(new book("B007", "The Locked Door", "123", "Freida McFadden",  "images/B007.png",  2)); 
        allBooks.add(new book("B003", "Anne of Green Gables", "123", "L. M. Montgomery",  "images/B003.png",8));
        allBooks.add(new book("B004", "Ars Magna", "123", "Gerolamo Cardano",  "images/B004.png",  6));
        allBooks.add(new book("B006", "How I Survived Middle School", "123", "Nancy Krulik", "images/B006.png",7));


        relatedBooksFlowPane.getChildren().clear();
        
        List<book> relatedBooksToDisplay = new ArrayList<>();
        for (book book : allBooks) {
            if (!book.getId().equals(currentBook.getId())) {
                relatedBooksToDisplay.add(book);
            }
            if (relatedBooksToDisplay.size() >= 4) {
                break;
            }
        }

        
        
        if (!relatedBooksToDisplay.isEmpty()) {
            relatedBooksFlowPane.getChildren().add(CardUtils.createFourBookCoversCard(relatedBooksToDisplay, onBookDetailsAndBorrowRequested, "Borrow")); //
        } else {
            Label noRelatedBooksLabel = new Label("No related books found.");
            noRelatedBooksLabel.setFont(Font.font("Montserrat", 14));
            noRelatedBooksLabel.setStyle("-fx-text-fill: #777;");
            relatedBooksFlowPane.getChildren().add(noRelatedBooksLabel);
        }
    }
    
    public void refreshData() {
        System.out.println("Refreshing Book Detail Page...");
    }
}