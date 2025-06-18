package resource.view.user;

import data.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert.AlertType;
import main.Index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DataBorrowBook {
    private Book displayandBorrow;
    private Label titleLabel;
    private ImageView coverView;
    private Label isbnLabel;
    private Label authorLabel;
    private Label quantityLabel;
    private TextField durationField;
    private TextField untilDateField;
    private Button submitButton;
    private Index app;

    public DataBorrowBook(Index app){

    }

    private void initializeUI() {

        VBox contentArea = new VBox(24);
        contentArea.setPadding(new Insets(24));
        contentArea.setAlignment(Pos.TOP_CENTER);

        VBox cardContainer = new VBox(20);
        cardContainer.setPadding(new Insets(25));
        cardContainer.setAlignment(Pos.TOP_LEFT);
        cardContainer.setPrefWidth(997);
        cardContainer.setPrefHeight(729);
        cardContainer.setMaxHeight(729);
        cardContainer.setMaxWidth(997);
        cardContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 0);");

        HBox mainContentCard = new HBox(30);
        mainContentCard.setAlignment(Pos.TOP_CENTER);


        VBox coverSection = new VBox();
        coverSection.setAlignment(Pos.CENTER);

        coverView = new ImageView();
        coverView.setFitWidth(406);
        coverView.setFitHeight(631);
        coverView.setPreserveRatio(true);

        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(406, 631);
        card.setPadding(new Insets(0));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #ffffff; -fx-border-width: 1;");
        card.getChildren().add(coverView);


        coverSection.getChildren().add(card);
        VBox rightSideWrapper = new VBox();
        rightSideWrapper.setAlignment(Pos.TOP_CENTER);
        rightSideWrapper.setMaxWidth(500);

        VBox rightSideDetailsAndForm = new VBox(15);
        HBox.setHgrow(rightSideWrapper, Priority.ALWAYS);

        titleLabel = new Label();
        titleLabel.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 32));
        titleLabel.setWrapText(true);
        titleLabel.setTextFill(Color.web("#000000"));
        titleLabel.setAlignment(Pos.TOP_LEFT);
        Separator separator = new Separator();
        separator.setPadding(new Insets(30, 0, 10, 0));
        separator.setMinHeight(5);
        VBox textDetailsSection = new VBox(5);
        textDetailsSection.setAlignment(Pos.TOP_LEFT);
        textDetailsSection.setMinWidth(250);

        isbnLabel = createDetailLabel("ISBN", "");
        authorLabel = createDetailLabel("Author", "");
        quantityLabel = createDetailLabel("Quantity", "");

        textDetailsSection.getChildren().addAll(
                isbnLabel,
                authorLabel,
                quantityLabel
        );

        VBox borrowForm = new VBox(15);
        borrowForm.setAlignment(Pos.CENTER);
        borrowForm.setMinWidth(500);

        VBox durationContainer = new VBox(5);
        durationContainer.setAlignment(Pos.CENTER);
        Label durationLabel = new Label("Duration");
        durationLabel.setAlignment(Pos.CENTER);
        durationLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 25));
        durationField = new TextField();
        durationField.setAlignment(Pos.CENTER);
        durationField.setPromptText("DD/MM/YY");
        durationField.setPrefWidth(463);
        durationField.setMaxWidth(463);
        durationField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #ccc; -fx-padding: 8; -fx-background-color: #e9e9e9;-fx-text-fill: #838383;");

        durationContainer.getChildren().addAll(durationLabel, durationField);

        VBox untilContainer = new VBox(5);
        untilContainer.setAlignment(Pos.CENTER);
        Label untilLabel = new Label("Until");
        untilLabel.setAlignment(Pos.CENTER);
        untilLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 25));
        untilDateField = new TextField();
        untilDateField.setPromptText("DD/MM/YY");
        untilDateField.setAlignment(Pos.CENTER);
        untilDateField.setPrefWidth(463);
        untilDateField.setMaxWidth(463);
        untilDateField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #ccc; -fx-padding: 8; -fx-background-color: #e9e9e9;-fx-text-fill: #838383;");
        untilContainer.getChildren().addAll(untilLabel, untilDateField);



        submitButton = new Button("Submit");
        submitButton.setAlignment(Pos.CENTER);
        submitButton.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 32));

        submitButton.setStyle("-fx-background-color: #23D957; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 35;");
        VBox.setMargin(submitButton, new Insets(10, 0, 0, 0));
        submitButton.setOnAction(e -> handleSubmit());
        submitButton.setMaxWidth(200);
        borrowForm.getChildren().addAll(durationContainer, untilContainer, submitButton);
        rightSideDetailsAndForm.getChildren().addAll(
                titleLabel,
                textDetailsSection,
                separator,
                borrowForm

        );

        rightSideWrapper.getChildren().add(rightSideDetailsAndForm);

        mainContentCard.getChildren().addAll(coverSection, rightSideWrapper);

        cardContainer.getChildren().add(mainContentCard);
        contentArea.getChildren().add(cardContainer);
    }

    private Label createDetailLabel(String prefix, String value) {
        Label label = new Label();
        label.setText(prefix + " : " + value);
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        label.setTextFill(Color.web("#000000"));
        return label;
    }

    public void displayBook(Book book) {
        this.displayandBorrow = book;
        if (book != null) {
            titleLabel.setText(book.getTitle());
            isbnLabel.setText("ISBN : " + (book.getIsbn() != null ? book.getIsbn() : ((Integer) book.getId_book() != null ? book.getId_book() : "N/A")));
            authorLabel.setText("Author : " + book.getAuthor());
            quantityLabel.setText("Quantity : " + book.getQuantity());

            Image coverImage = loadImage(book.getCoverImagePath());
            coverView.setImage(coverImage);
            durationField.clear();
            untilDateField.clear();
        } else {
            titleLabel.setText("");
            isbnLabel.setText("ISBN : ");
            authorLabel.setText("Author : ");
            quantityLabel.setText("Quantity : ");
            coverView.setImage(null);
            durationField.clear();
            untilDateField.clear();
        }
    }
    private void handleSubmit() {
        if (displayandBorrow == null) {
            showAlert(AlertType.ERROR, "Error", "Tidak ada buku yang dipilih untuk dipinjam.");
            return;
        }

        String durationText = durationField.getText();
        String returnDate = untilDateField.getText();

        if (durationText.isEmpty() || returnDate == null) {
            showAlert(AlertType.WARNING, "Peringatan", "Harap isi Durasi dan Tanggal Kembali.");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
            if (duration <= 0) {
                showAlert(AlertType.WARNING, "Peringatan", "Durasi harus lebih dari 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Peringatan", "Durasi harus berupa angka.");
            return;
        }
        int quantity = Integer.parseInt(displayandBorrow.getQuantity());
        if (quantity <= 0) {
            showAlert(AlertType.ERROR, "Error Peminjaman", "Maaf, buku ini tidak tersedia.");
            return;
        }

        System.out.println("Meminjam buku: " + displayandBorrow.getTitle());
        System.out.println("Durasi: " + duration + " hari");
        System.out.println("Tanggal Kembali: " + returnDate);

//        displayandBorrow.setQuantiti(bookToDisplayAndBorrow.getStock() - 1);
        displayBook(displayandBorrow);

        showAlert(AlertType.INFORMATION, "Sukses", "Peminjaman buku '" + displayandBorrow.getTitle() + "' berhasil!");

    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new Image(getClass().getResourceAsStream("images/B001.png"));
        }
        try {

            Image img = new Image(getClass().getResourceAsStream(imagePath));
            if (!img.isError()) {
                return img;
            }
            File file = new File(imagePath);
            if (file.exists()) {
                return new Image(new FileInputStream(file));
            } else {
                System.err.println("Failed to load image from resource or file system: " + imagePath);
                return new Image(getClass().getResourceAsStream("images/B001.png"));
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Failed to load image  " + imagePath + ": " + e.getMessage());
            return new Image(getClass().getResourceAsStream("images/B001.png"));
        }
    }

}
