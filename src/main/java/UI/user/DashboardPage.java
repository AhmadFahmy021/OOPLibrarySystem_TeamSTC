package main.java.UI.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardPage extends VBox {

    private Label totalBookCount;
    private Label returnBookCount;
    private Label borrowBookCount;

    public DashboardPage() {
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #f8f9fa;");
        VBox.setVgrow(this, Priority.ALWAYS); 
        HBox cardsSection = new HBox(50);
        cardsSection.setAlignment(Pos.CENTER);

        totalBookCount = new Label("0");
        returnBookCount = new Label("0");
        borrowBookCount = new Label("0");
        VBox totalBookCard = CardUtils.createDashboardCountCard("Total Book", totalBookCount, "#4750ec", Pos.CENTER_LEFT);
        VBox returnBookCard = CardUtils.createDashboardCountCard("Return Book", returnBookCount, "#da2020", Pos.CENTER_LEFT);
        VBox borrowBookCard = CardUtils.createDashboardCountCard("Borrow Book", borrowBookCount, "#23d957", Pos.CENTER_LEFT);

        cardsSection.getChildren().addAll(totalBookCard, returnBookCard, borrowBookCard);
        VBox dataBookSection = new VBox();
        dataBookSection.setStyle(
            "-fx-background-color: #f0f0f0; " +
            "-fx-background-radius: 10; " +
            "-fx-border-radius: 10; " +
            "-fx-border-color: #ddd; " +
            "-fx-border-width: 1;" +
            "-fx-padding: 0 0 0 0;" 
        );

        Label dataBookHeader = new Label("Data Book");
        dataBookHeader.setFont(Font.font("Montserrat",FontWeight.BOLD,48));
        dataBookHeader.setTextFill(Color.WHITE);
        dataBookHeader.setPrefHeight(83);
        dataBookHeader.setMaxWidth(Double.MAX_VALUE);
        dataBookHeader.setPadding(new Insets(0, 0, 0, 30));
        dataBookHeader.setStyle(
            "-fx-background-color: #4750ec;" +
            "-fx-background-radius: 10 10 0 0;"
        );

        ListView<String> bookListView = new ListView<>();
        bookListView.setPrefHeight(450);
        bookListView.getItems().addAll(
            "Habis Gelap Terbitlah Terang", "Arthur's April Fool", "How I Survived Middle School", "Architecture The Whole Story", "The Locked Door"
        );
        bookListView.setCellFactory(lv -> new ListCell<String>() {
            private final double CELL_HEIGHT = 75.0;
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-pref-height: " + CELL_HEIGHT + "px;");
                    setPrefHeight(CELL_HEIGHT);
                    setMinHeight(CELL_HEIGHT);
                    setMaxHeight(CELL_HEIGHT);

                } else {
                    setText(item);
                    setFont(Font.font("Montserrat", 35)); 
                    setTextFill(Color.BLACK);
                    setPrefHeight(CELL_HEIGHT);
                    setMinHeight(CELL_HEIGHT); 
                    setMaxHeight(CELL_HEIGHT);
                    
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: " + "#9e9e9e" + ";");
                    } else {
                        setStyle("-fx-background-color: " + "#eaeaea" + ";");
                    }
                    
                    setPadding(new Insets(0, 10, 0, 20));
                }
            }
        });
        bookListView.setStyle(
            "-fx-background-color: #eaeaea; " +      
            "-fx-background-radius: 0 0 10 10; " +  
            "-fx-border-radius: 0 0 10 10; " +      
            "-fx-border-color: #fff; " +          
            "-fx-border-width: 0 1 1 1;" +        
            "-fx-background-insets: 0; " +         
            "-fx-padding: 0;"                      
        );

        
        VBox.setMargin(dataBookHeader, new Insets(0, 0, 0, 0)); 
        VBox.setMargin(bookListView, new Insets(0, 0, 0, 0)); 


        VBox.setVgrow(bookListView, Priority.ALWAYS);
        
        dataBookSection.getChildren().addAll(dataBookHeader, bookListView);

        this.getChildren().addAll( cardsSection, dataBookSection);
        updateDashboardCounts(); 
    }

    private void updateDashboardCounts() {
        int totalBooks = 0;
        int returnedBooks = 0;
        int borrowedBooks = 0;

        totalBookCount.setText(String.valueOf(totalBooks));
        returnBookCount.setText(String.valueOf(returnedBooks));
        borrowBookCount.setText(String.valueOf(borrowedBooks));
    }

    public void refreshData() {
        System.out.println("Refreshing Dashboard Page...");
        updateDashboardCounts();
    }
}