package resource.view.user;

import data.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resource.components.CardUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Consumer;

public class DetailBook extends VBox {
    private Book currentBook;
    private ImageView coverView;
    private Label titleLabel;
    private Label isbnLabel;
    private Label authorLabel;
    private Label stockLabel;
    private FlowPane relatedBooksFlowPane;

    private Consumer<Book> onBorrowBookRequested;
    private Consumer<Book> onBookDetailsAndBorrowRequested;

    public DetailBook(Consumer<Book> onBorrowBookRequested, Consumer<Book> onBookDetailsAndBorrowRequested){
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

    public void setBook(Book book) {
        this.currentBook = book;
        if (book != null) {
            titleLabel.setText(book.getTitle());
            isbnLabel.setText("ISBN \t: " + book.getIsbn());
            String author = book.getAuthor();
            authorLabel.setText("Author \t: " + author.toUpperCase());
            stockLabel.setText("Quantity \t: " + book.getQuantity());

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
        } else {
            titleLabel.setText("No Book Selected");
            isbnLabel.setText("");
            authorLabel.setText("");
            stockLabel.setText("");
            coverView.setImage(null);
            relatedBooksFlowPane.getChildren().clear();
        }
    }

    public void displayRelatedBooks(List<Book> relatedBooks) {
        relatedBooksFlowPane.getChildren().clear(); // Bersihkan tampilan sebelumnya

        if (relatedBooks != null && !relatedBooks.isEmpty()) {
            // Asumsi CardUtils.createFourBookCoversCard mengembalikan Node yang sesuai
            // Pastikan CardUtils.createFourBookCoversCard diimplementasikan dengan benar
            relatedBooksFlowPane.getChildren().add(CardUtils.createFourBookCoversCard(relatedBooks, onBookDetailsAndBorrowRequested, "Borrow"));
        } else {
            Label noRelatedBooksLabel = new Label("No related books found.");
            noRelatedBooksLabel.setFont(Font.font("Montserrat", 14));
            noRelatedBooksLabel.setStyle("-fx-text-fill: #777;");
            relatedBooksFlowPane.getChildren().add(noRelatedBooksLabel);
        }
    }
}
