package main.java.UI.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

public class DashboardPage {
    private VBox dashboardLayout;

    public DashboardPage() {
        dashboardLayout = new VBox(50);
        dashboardLayout.setPadding(new Insets(0));

        // --- Top Cards ---
        HBox topCards = new HBox(50);
        topCards.setAlignment(Pos.TOP_CENTER);

        // Member Card
        VBox memberCard = createCard("MEMBER", "540", "#1d84de");

        // Book Card
        VBox bookCard = createCard("BOOK", "1.414", "#15b144");
        topCards.getChildren().addAll(memberCard, bookCard);
        dashboardLayout.getChildren().add(topCards);
        topCards.setPadding(new Insets(0,0,20,0));
        
        VBox bookMovementChartCard = createChartCard(
            "Book Movement Chart",
            "/resources/admin/images/Diagram.png", // Menggunakan path absolut dengan leading slash
            964, // Lebar chart di dalam card
            300  // Tinggi chart di dalam card
        );
        
        // Bungkus chart card dalam HBox untuk memusatkannya
        HBox centerChartContainer = new HBox();
        centerChartContainer.setAlignment(Pos.CENTER);
        centerChartContainer.getChildren().add(bookMovementChartCard);
        dashboardLayout.getChildren().add(centerChartContainer);
    }

    private VBox createCard(String title, String value, String backgroundColor) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 8;");
        card.setPrefWidth(284);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.WHITE);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 64));
        valueLabel.setTextFill(Color.WHITE);

        card.getChildren().addAll(titleLabel, valueLabel);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));
        shadow.setRadius(10);
        card.setEffect(shadow);

        return card;
    }
    
    private VBox createChartCard(String chartTitle, String imagePath, double chartWidth, double chartHeight) {
        VBox card = new VBox(0);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.60)));
        card.setPrefSize(964, 400);
        card.setMaxSize(964, 400);
        
        // Header biru untuk judul chart
        HBox titleHeader = new HBox();
        titleHeader.setStyle("-fx-background-color: #3f51b5; -fx-background-radius: 12 12 0 0;");
        titleHeader.setPadding(new Insets(15, 20, 15, 20));
        titleHeader.setAlignment(Pos.CENTER_LEFT);
        titleHeader.setPrefHeight(60);

        Label titleLabel = new Label(chartTitle);
        titleLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);
        titleHeader.getChildren().add(titleLabel);
        
        HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.TOP_CENTER);
        
        // ImageView placeholder chart
        ImageView chartImageView = createChartPlaceholder(imagePath, chartWidth, chartHeight);
        imageContainer.setPadding(new Insets(15, 5, 5, 5));
        imageContainer.getChildren().add(chartImageView);
        
        card.getChildren().addAll(titleHeader, imageContainer);
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
        System.out.println("⏳ Memuat gambar dari path: " + imagePath);

        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("⚠️ imagePath kosong/null. Menggunakan placeholder.");
            return createPlaceholderImage();
        }

        try {
            // Coba load dari classpath dengan path absolut
            var resource = DashboardPage.class.getResource(imagePath);
            System.out.println("📦 Resource dari classpath: " + resource);

            if (resource != null) {
                Image img = new Image(DashboardPage.class.getResourceAsStream(imagePath));
                if (!img.isError()) {
                    System.out.println("✅ Berhasil memuat gambar dari classpath");
                    return img;
                }
            }

            // Jika gagal dari classpath, coba dari file system
            if (!imagePath.startsWith("/")) {
                File file = new File(imagePath);
                if (file.exists()) {
                    System.out.println("📁 File ditemukan di file system: " + file.getAbsolutePath());
                    return new Image(new FileInputStream(file));
                }
            }

            // Coba path alternatif
            String[] alternativePaths = {
                "/admin/images/Diagram.png",
                "/images/Diagram.png",
                "admin/images/Diagram.png",
                "images/Diagram.png"
            };

            for (String altPath : alternativePaths) {
                try {
                    var altResource = DashboardPage.class.getResource(altPath);
                    if (altResource != null) {
                        Image img = new Image(DashboardPage.class.getResourceAsStream(altPath));
                        if (!img.isError()) {
                            System.out.println("✅ Berhasil memuat gambar dari path alternatif: " + altPath);
                            return img;
                        }
                    }
                } catch (Exception e) {
                    // Continue to next alternative
                }
            }

            System.err.println("❌ Tidak dapat memuat gambar dari path manapun. Menggunakan placeholder.");
            return createPlaceholderImage();

        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("❌ Error memuat gambar " + imagePath + ": " + e.getMessage());
            return createPlaceholderImage();
        }
    }

    // Method untuk membuat placeholder image jika file tidak ditemukan
    private static Image createPlaceholderImage() {
        try {
            // Coba buat gambar placeholder sederhana atau gunakan gambar default
            // Alternatif: return gambar dari URL atau buat gambar programmatically
            System.out.println("🎨 Membuat placeholder image");
            
            // Jika tidak ada gambar sama sekali, bisa return null dan handle di UI
            // Atau bisa membuat rectangle placeholder
            return null; // Akan di-handle di calling method
            
        } catch (Exception e) {
            System.err.println("❌ Error membuat placeholder: " + e.getMessage());
            return null;
        }
    }

    public VBox getView() {
        return dashboardLayout;
    }
}