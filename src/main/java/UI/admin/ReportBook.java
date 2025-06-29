package main.java.UI.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ReportBook {
    private VBox viewReportBookLayout;

    public ReportBook() {
        viewReportBookLayout = new VBox(20);
        viewReportBookLayout.setPadding(new Insets(20));
        viewReportBookLayout.setAlignment(Pos.TOP_CENTER);
        VBox bookMovementChartCard1 = createChartCard(
//            "Book Movement Chart",
//            "/images/BarLineChart.png", // Pastikan path ini benar, atau sesuaikan
            964, // Lebar chart di dalam card
            300  // Tinggi chart di dalam card
        );
        HBox bottomCards = new HBox(50);
        bottomCards.setAlignment(Pos.TOP_CENTER);
        VBox bookMovementChartCard2 = createChartCard(
//            "Book Movement Chart",
//            "/images/BarLineChart.png", // Pastikan path ini benar, atau sesuaikan
            400, // Lebar chart di dalam card
            300  // Tinggi chart di dalam card
        );
        VBox bookMovementChartCard3 = createChartCard(
//            "Book Movement Chart",
//            "/images/BarLineChart.png", // Pastikan path ini benar, atau sesuaikan
            400, // Lebar chart di dalam card
            300  // Tinggi chart di dalam card
        );
        bottomCards.getChildren().addAll(bookMovementChartCard2,bookMovementChartCard3);
        viewReportBookLayout.getChildren().add(bookMovementChartCard1);
        viewReportBookLayout.getChildren().add(bottomCards);
    }

    private VBox createChartCard( double chartWidth, double chartHeight) {
        VBox card = new VBox(0); // Spacing 0 karena konten akan diatur paddingnya sendiri
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;"); // Background putih, rounded
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.60))); // Efek bayangan
        card.setPrefSize(964, 300);
        card.setMaxSize(964, 300);
        // Header biru untuk judul chart
                HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.TOP_CENTER);
        // ImageView placeholder chart
//        ImageView chartImageView = createChartPlaceholder(imagePath, chartWidth, chartHeight);
        imageContainer.setPadding(new Insets(15, 5, 5, 5));  // Margin di sekitar gambar chart di dalam card
//        imageContainer.getChildren().add(chartImageView);
        card.getChildren().addAll(imageContainer);
        return card;
    }

    private ImageView createChartPlaceholder(String imagePath, double fitWidth, double fitHeight) {
        
        ImageView imageView;
        Image image = loadImage(imagePath);
       
        imageView = new ImageView(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8;");
        return imageView;
    }
    private static Image loadImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new Image(ReportBook.class.getResourceAsStream("images/placeholder_book.png"));
        }
        try {
            Image img = new Image(ReportBook.class.getResourceAsStream(imagePath));
            if (img.isError() && !imagePath.startsWith("/")) {
                File file = new File(imagePath);
                if (file.exists()) {
                    return new Image(new FileInputStream(file));
                } else {
                    System.err.println("Failed to load image from resource or file system: " + imagePath);
                    return new Image(ReportBook.class.getResourceAsStream("images/BarLineChart.png"));
                }
            } else if (img.isError()) {
                 System.err.println("Failed to load image from classpath resource: " + imagePath);
                 return new Image(ReportBook.class.getResourceAsStream("images/BarLineChart.png"));
            }
            return img;
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("Failed to load image " + imagePath + ": " + e.getMessage());
            return new Image(ReportBook.class.getResourceAsStream("images/BarLineChart.png"));
        }
    }
    public VBox getView() {
        return viewReportBookLayout;
    }
}