package resource.components;

import data.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;

public class CardUtils {

    /**
     *
     * @param book
     * @param onDetailsClicked
     * @return
     */
    public static VBox createBookCoverCard(Book book, Runnable onDetailsClicked) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 250); 
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        ImageView coverView = new ImageView();
        coverView.setFitWidth(120);
        coverView.setFitHeight(180);
        coverView.setPreserveRatio(true);
        
        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            try {
                File file = new File(book.getCoverImagePath());
                if (file.exists()) {
                     coverView.setImage(new Image(new FileInputStream(file)));
                } else {
                    coverView.setImage(new Image(CardUtils.class.getResourceAsStream(book.getCoverImagePath())));
                }
            } catch (FileNotFoundException | NullPointerException e) {
                System.err.println("Failed to load image for " + book.getTitle() + ": " + e.getMessage());
                coverView.setImage(new Image(CardUtils.class.getResourceAsStream("images/B002.png"))); // Pastikan Anda memiliki placeholder_book.png
            }
        } else {
             coverView.setImage(new Image(CardUtils.class.getResourceAsStream("images/B002.png")));
        }
        Label titleLabel = new Label(book.getTitle());
        titleLabel.setFont(Font.font("System Bold", 14));
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        VBox.setMargin(titleLabel, new Insets(5, 0, 0, 0));
        Button detailsButton = new Button("Selengkapnya");
        detailsButton.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5 10; -fx-background-radius: 5;");
        detailsButton.setOnAction(e -> onDetailsClicked.run());

        card.getChildren().addAll(coverView, titleLabel, detailsButton);
        return card;
    }
    
    public static VBox createDashboardCountCard(String title, Label countLabel, String backgroundColor, Pos alignment) {
        VBox card = new VBox(5);
        card.setAlignment(alignment);
        card.setPrefSize(301, 182);
        card.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 10;");
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Montserrat",FontWeight.BOLD,40));
        titleLabel.setTextFill(Color.WHITE);
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 20));

        countLabel.setFont(Font.font("Montserrat",FontWeight.BOLD,64));
        countLabel.setTextFill(Color.WHITE);
        VBox.setMargin(countLabel, new Insets(0, 0, 0, 20));

        card.getChildren().addAll(titleLabel, countLabel);
        return card;
    }
    private static Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new Image(CardUtils.class.getResourceAsStream("/images/placeholder_book.png"));
        }
        try {
            Image img = new Image(CardUtils.class.getResourceAsStream(imagePath));
            if (img.isError() && !imagePath.startsWith("/")) {
                File file = new File(imagePath);
                if (file.exists()) {
                    return new Image(new FileInputStream(file));
                } else {
                    System.err.println("Failed to load image from resource or file system: " + imagePath);
                    return new Image(CardUtils.class.getResourceAsStream("/images/placeholder_book.png"));
                }
            } else if (img.isError()) {
                 System.err.println("Failed to load image from classpath resource: " + imagePath);
                 return new Image(CardUtils.class.getResourceAsStream("/images/placeholder_book.png"));
            }
            return img;
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Failed to load image " + imagePath + ": " + e.getMessage());
            return new Image(CardUtils.class.getResourceAsStream("/resource/images/B001.png"));
        }
    }

    public static VBox createFourBookCoversCard(List<Book> booksInGroup, Consumer<Book> onDetailsClickedForFirstBook, String buttonText) {
        VBox card = new VBox(30);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 10; -fx-border-radius: 10;  -fx-border-width: 1;");
        card.setAlignment(Pos.CENTER_LEFT);

        HBox coversHBox = new HBox(50);
        coversHBox.setAlignment(Pos.CENTER);

        final double PREVIEW_WIDTH = 202;
        final double HBOX_GAP = 20;
        double totalHBoxWidth = (PREVIEW_WIDTH * 4) + (HBOX_GAP * 3);
        card.setPrefWidth(1064);

        VBox bookDataNotAvailable = new VBox(5);
        bookDataNotAvailable.setAlignment(Pos.CENTER);
        bookDataNotAvailable.setPrefWidth(202);

        if (booksInGroup.size() == 0){
            Label emptyLabel = new Label("Data Book Not Available");
            emptyLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 15));
            bookDataNotAvailable.getChildren().addAll(emptyLabel);

            coversHBox.getChildren().add(bookDataNotAvailable);
        } else {
            for (int i = 0; i < booksInGroup.size(); i++) {
                VBox bookPreview = new VBox(5);
                bookPreview.setAlignment(Pos.CENTER);
                bookPreview.setPrefWidth(202);

                Book book = booksInGroup.get(i);
                ImageView coverView = new ImageView(loadImage(book.getCover()));
                coverView.setFitWidth(190);
                coverView.setFitHeight(263);
                coverView.setPreserveRatio(true);

                Button detailsButton = new Button(buttonText); // Ubah teks menjadi "Detail" atau "Info"

                detailsButton.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 20));
                detailsButton.setStyle("-fx-background-color: #23d957; -fx-text-fill: white; -fx-padding: 3 10; -fx-background-radius: 9;");

                final Book currentBook = book; // Perlu variabel final untuk lambda
                detailsButton.setOnAction(e -> onDetailsClickedForFirstBook.accept(currentBook));

                bookPreview.getChildren().addAll(coverView, detailsButton);
                coversHBox.getChildren().add(bookPreview);
            }
        }

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        card.getChildren().add(coversHBox);
        return card;
    }

}