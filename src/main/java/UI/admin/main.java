package main.java.UI.admin;

import main.java.UI.admin.DashboardPage;
import main.java.UI.admin.ManageAdmin;
import main.java.UI.admin.ManageBook;
import main.java.UI.admin.ReturnBook;
import main.java.UI.admin.ReportBook;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class main extends Application {

    private BorderPane rootLayout;
    private VBox contentArea;
    private Label topBarWelcomeLabel;
    private VBox mainContentContainer;
    private Label dashboardNavLabel;
    private Label manageBookNavLabel;
    private Label returnBookNavLabel;
    private Label viewReportNavLabel;
    private Label manageAdminNavLabel;

    @Override
    public void start(Stage primaryStage) {
        rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(0));
        rootLayout.setStyle("-fx-background-color: #F0F2F5;");

        VBox leftSidebar = new VBox();
        leftSidebar.setPrefWidth(275);
        leftSidebar.setStyle("-fx-background-color: #3f51b5;");
        leftSidebar.setPadding(new Insets(20, 0, 0, 0));

        Label libraryLabel = new Label("L I B R A R Y");
        libraryLabel.setFont(Font.font("Montserrat",FontWeight.BOLD, 40));
        libraryLabel.setTextFill(Color.WHITE);
        libraryLabel.setAlignment(Pos.CENTER);
        libraryLabel.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(libraryLabel, new Insets(0, 0, 20, 0));
        // --- Sidebar (Ditempel langsung) ---
        VBox navButtons = new VBox(10);
        dashboardNavLabel = createNavLabel("Dashboard");
        manageBookNavLabel = createNavLabel("Manage Book");
        returnBookNavLabel = createNavLabel("Return Book"); 
        viewReportNavLabel = createNavLabel("View Report"); 
        manageAdminNavLabel = createNavLabel("Manage Admin"); 
        navButtons.getChildren().addAll(
            dashboardNavLabel,
            manageBookNavLabel,
            returnBookNavLabel,
            viewReportNavLabel,
            manageAdminNavLabel
        );
        VBox.setVgrow(navButtons, Priority.ALWAYS); // Membuat navButtons mengisi sisa ruang vertikal

        leftSidebar.getChildren().addAll(libraryLabel, navButtons);
        rootLayout.setLeft(leftSidebar);
        
        // --- Event Handlers untuk Label Navigasi ---
        dashboardNavLabel.setOnMouseClicked(e -> { 
            resetNavButtonStyles(dashboardNavLabel,manageBookNavLabel,returnBookNavLabel,viewReportNavLabel,manageAdminNavLabel);
            dashboardNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showDashboardView();
            });
        manageBookNavLabel.setOnMouseClicked(e -> { 
            resetNavButtonStyles(dashboardNavLabel,manageBookNavLabel,returnBookNavLabel,viewReportNavLabel,manageAdminNavLabel);
            manageBookNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showManageBookView();
            });
        returnBookNavLabel.setOnMouseClicked(e -> { 
            resetNavButtonStyles(dashboardNavLabel,manageBookNavLabel,returnBookNavLabel,viewReportNavLabel,manageAdminNavLabel);
            returnBookNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showReturnBookView();
            });
        viewReportNavLabel.setOnMouseClicked(e -> { 
            resetNavButtonStyles(dashboardNavLabel,manageBookNavLabel,returnBookNavLabel,viewReportNavLabel,manageAdminNavLabel);
            viewReportNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showViewReportBookView();
            });
        manageAdminNavLabel.setOnMouseClicked(e -> { 
            resetNavButtonStyles(dashboardNavLabel,manageBookNavLabel,returnBookNavLabel,viewReportNavLabel,manageAdminNavLabel);
            manageAdminNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showManageAdminView();
            });
        
        HBox topBar = new HBox(10);
        topBar.setPrefHeight(100);
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(0, 70, 0, 50));

        topBarWelcomeLabel = new Label("Welcome, Ahmad"); // Label "Welcome, Ahmad"
        topBarWelcomeLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 29));
        topBarWelcomeLabel.setAlignment(Pos.CENTER_RIGHT); // Seharusnya ini align di HBox, bukan labelnya

        Region spacerTopBar = new Region();
        HBox.setHgrow(spacerTopBar, Priority.ALWAYS); // Spacer untuk mendorong userInfoContainer ke kanan

        // User Info Container (Avatar + Name)
        HBox userInfoContainer = new HBox(30); // Spacing 30 antara avatar dan nama
        userInfoContainer.setAlignment(Pos.CENTER_RIGHT);

        StackPane avatarWrapper = new StackPane();
        avatarWrapper.setPrefSize(71, 71);
        avatarWrapper.setMaxSize(71, 71);

        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(71, 71); 
        avatarCircle.setStyle("-fx-background-color: #4750ec; -fx-background-radius: 50;"); // Warna dan radius
        Label avatarLetter = new Label("A");
        avatarLetter.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        avatarLetter.setTextFill(Color.WHITE);
        avatarCircle.getChildren().add(avatarLetter);
        avatarWrapper.getChildren().add(avatarCircle); // Masukkan lingkaran ke wrapper jika ada layer lain

        Label userNameLabel = new Label("Ahmad");
        userNameLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 29));
        userNameLabel.setTextFill(Color.BLACK); // Warna teks hitam untuk nama

        userInfoContainer.getChildren().addAll(avatarWrapper, userNameLabel); // Tambahkan avatar dan nama

        topBar.getChildren().addAll(topBarWelcomeLabel, spacerTopBar, userInfoContainer);
        
        mainContentContainer = new VBox();
        mainContentContainer.getChildren().add(topBar);
//        rootLayout.setCenter(topBar);
        // --- Content Area ---
        contentArea = new VBox();
        contentArea.setPadding(new Insets(20));
//        contentArea.getChildren().add(topBar);
        mainContentContainer.getChildren().add(contentArea);
        rootLayout.setCenter(mainContentContainer);

        // Tampilkan Dashboard secara default
        dashboardNavLabel.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
        showDashboardView();
        Scene scene = new Scene(rootLayout, 1440, 900);
        primaryStage.setTitle("Library Management System - Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method untuk membuat Label navigasi
    private Label createNavLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Montserrat",FontWeight.BOLD,32));
        label.setTextFill(Color.WHITE);
        label.setPrefHeight(40);
        label.setPrefWidth(275);
        label.setStyle("-fx-background-color: transparent;");
        label.setAlignment(Pos.CENTER); // Text alignment inside the label
        return label;
    }

    private void resetNavButtonStyles(Label... buttons) {
        for (Label button : buttons) {
            button.setStyle("-fx-background-color: transparent;");
        }
    }

    // Metode untuk menampilkan View yang berbeda
    private void showDashboardView() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new DashboardPage().getView());
    }

    private void showManageBookView() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new ManageBook().getView()); 
    }

    private void showReturnBookView() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new ReturnBook().getView());
    }

    private void showViewReportBookView() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new ReportBook().getView());
    }

    private void showManageAdminView() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new ManageAdmin().getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}