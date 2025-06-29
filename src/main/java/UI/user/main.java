package main.java.UI.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.UI.user.model.book; 
import java.util.function.Consumer;
import javafx.scene.text.FontWeight;

public class main extends Application { 
    
    private DashboardPage dashboardPage;
    private DataBookPage dataBookPage;
    private BorrowBookPage borrowBookPage;
    private BookDetailPage bookDetailPage; 
    private BookInfoPage bookInfoPage;
    private StackPane contentStack; 
    private Label welcomeLabel;
    @Override
    public void start(Stage primaryStage) {
        
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

        VBox navButtons = new VBox(10);
        Label dashboardButton = createNavLabel("Dashboard");
        dashboardButton.setAlignment(Pos.CENTER);
        dashboardButton.setPrefWidth(275);
        dashboardButton.setFont(Font.font("Montserrat",FontWeight.BOLD, 32));
        
        Label dataBookButton = createNavLabel("Data Book");
        dataBookButton.setAlignment(Pos.CENTER);
        dataBookButton.setPrefWidth(275);
        dataBookButton.setFont(Font.font("Montserrat",FontWeight.BOLD, 32));

        Label borrowBookButton = createNavLabel("Borrow Book");
        borrowBookButton.setAlignment(Pos.CENTER);
        borrowBookButton.setPrefWidth(275);
        borrowBookButton.setFont(Font.font("Montserrat",FontWeight.BOLD, 32));
        
        HBox topBar = new HBox(10);
        topBar.setPrefHeight(100);
        topBar.setStyle("-fx-background-color: #ffffff;");
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(0, 70, 0, 50));

        welcomeLabel = new Label("Welcome, Ahmad");
        welcomeLabel.setFont(Font.font("Montserrat",FontWeight.BOLD, 29));
        welcomeLabel.setAlignment(Pos.CENTER_LEFT);
        
        StackPane avatarWrapper = new StackPane();
        avatarWrapper.setPrefSize(71, 71); 
        avatarWrapper.setMaxSize(71, 71); 
        HBox userInfoContainer = new HBox(30); 
        userInfoContainer.setAlignment(Pos.CENTER_RIGHT);
        StackPane avatarCircle = new StackPane();
        avatarCircle.setPrefSize(35, 35);
        avatarCircle.setStyle("-fx-background-color: #4750ec; -fx-background-radius: 50;");
        Label avatarLetter = new Label("A");
        avatarLetter.setFont(Font.font("Montserrat",FontWeight.BOLD, 32));
        avatarLetter.setTextFill(Color.WHITE);
        avatarCircle.getChildren().add(avatarLetter);
        avatarWrapper.getChildren().add(avatarCircle);
        Label userNameLabel = new Label("Ahmad");
        userNameLabel.setFont(Font.font("Montserrat",FontWeight.BOLD,29));
        userInfoContainer.getChildren().addAll(avatarWrapper, userNameLabel);
        
        Region spacerTopBar = new Region();
        HBox.setHgrow(spacerTopBar, Priority.ALWAYS);

        topBar.getChildren().addAll(welcomeLabel, spacerTopBar, userInfoContainer);
        dashboardPage = new DashboardPage();
        Consumer<book> onBookDetailsRequest = book -> {
            bookDetailPage.setBook(book); 
            showPage(bookDetailPage);     
        };
        dataBookPage = new DataBookPage(onBookDetailsRequest);
        borrowBookPage = new BorrowBookPage();
        bookInfoPage = new BookInfoPage();
        Consumer<book> onBorrowFromDetails = book -> {
            borrowBookPage.displayBook(book);

            showPage(borrowBookPage);              
            resetNavButtonStyles(dashboardButton, dataBookButton, borrowBookButton); 
            borrowBookButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);"); 
        };
        
        Consumer<book> onFullDetailsAndBorrowRequest = book -> {
            bookInfoPage.displayBook(book);
            showPage(bookInfoPage); 
            resetNavButtonStyles(dashboardButton, dataBookButton, borrowBookButton); 
            dataBookButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
        };
        bookDetailPage = new BookDetailPage(onBorrowFromDetails, onFullDetailsAndBorrowRequest);
        dashboardButton.setOnMouseClicked(e -> {
            resetNavButtonStyles(dashboardButton, dataBookButton, borrowBookButton);
            dashboardButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showPage(dashboardPage);
            dashboardPage.refreshData();
        });
        dataBookButton.setOnMouseClicked(e -> {
            resetNavButtonStyles(dashboardButton, dataBookButton, borrowBookButton);
            dataBookButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            showPage(dataBookPage);
            dataBookPage.refreshData();
        });
        borrowBookButton.setOnMouseClicked(e -> {
            resetNavButtonStyles(dashboardButton, dataBookButton, borrowBookButton);
            borrowBookButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);");
            borrowBookPage.displayBook(null);
            showPage(borrowBookPage);
        });

        navButtons.getChildren().addAll(dashboardButton, dataBookButton, borrowBookButton);
        VBox.setVgrow(navButtons, Priority.ALWAYS); 
        leftSidebar.getChildren().addAll(libraryLabel, navButtons);
        
        contentStack = new StackPane();
        contentStack.getChildren().addAll(dashboardPage, dataBookPage, borrowBookPage, bookDetailPage, bookInfoPage);
        
        showPage(dashboardPage);
        dashboardButton.setStyle("-fx-background-color: rgba(249,249,249,0.44);"); // Aktifkan tombol dashboard

        VBox mainContentRight = new VBox();
        VBox.setVgrow(mainContentRight, Priority.ALWAYS);

        mainContentRight.getChildren().add(topBar);
        mainContentRight.getChildren().add(contentStack);
        VBox.setVgrow(contentStack, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setLeft(leftSidebar);
        root.setCenter(mainContentRight);

        Scene scene = new Scene(root, 1440, 1024); 
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }

    private void showPage(Region pageToShow) {
        dashboardPage.setVisible(false);
        dashboardPage.setManaged(false);

        dataBookPage.setVisible(false);
        dataBookPage.setManaged(false);

        borrowBookPage.setVisible(false);
        borrowBookPage.setManaged(false);

        bookDetailPage.setVisible(false); // Sembunyikan juga halaman detail
        bookDetailPage.setManaged(false);
        bookInfoPage.setManaged(false);
        bookInfoPage.setVisible(false);
        pageToShow.setVisible(true);
        pageToShow.setManaged(true);
        if (pageToShow == dashboardPage) {
            welcomeLabel.setVisible(true);
            welcomeLabel.setManaged(true);
        } else {
            welcomeLabel.setVisible(false);
            welcomeLabel.setManaged(false);
        }
    }

    private Label createNavLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Montserrat",FontWeight.BOLD,32));
        label.setTextFill(Color.WHITE);
        label.setPrefHeight(40);
        label.setPrefWidth(200);
        label.setStyle("-fx-background-color: transparent;");
        return label;
    }

    private void resetNavButtonStyles(Label... buttons) {
        for (Label button : buttons) {
            button.setStyle("-fx-background-color: transparent;");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }    
}