package resource.view;

import com.sun.tools.javac.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Index;

public class Login extends StackPane{
    public Login(Index app){
        Label header = new Label("Sign In to Library App");
        header.setFont(Font.font("Montserrat", FontWeight.BOLD, 25));
        header.setTextFill(Color.WHITE);
        header.setPadding(new Insets(20));
        HBox headerBox = new HBox(header);
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, #0000ff, #6666ff); -fx-background-radius: 20 20 0 0;");
        headerBox.setAlignment(Pos.CENTER);

        // Tab (manual)
        ToggleButton signInTab = new ToggleButton("Sign In");
        ToggleButton signUpTab = new ToggleButton("Sign Up");
        ToggleGroup toggleGroup = new ToggleGroup();
        signInTab.setToggleGroup(toggleGroup);
        signUpTab.setToggleGroup(toggleGroup);

        signUpTab.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        signInTab.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        signInTab.setSelected(true);

        signInTab.getStyleClass().add("tab-button");
        signUpTab.getStyleClass().add("tab-button");


        HBox tabBox = new HBox(signInTab, signUpTab);
        tabBox.setAlignment(Pos.CENTER);
        tabBox.setStyle("-fx-background-color: white;");

        tabBox.setPadding(Insets.EMPTY);
        tabBox.setSpacing(5);

        // Form
        TextField emailField = new TextField();
        emailField.setPromptText("Email address");

        TextField idField = new TextField();
        idField.setPromptText("ID");

        Button loginBtn = new Button("Sign In");
        loginBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 5;");
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        Hyperlink forgotLink = new Hyperlink("Forgot Password ?");
        forgotLink.setStyle("-fx-text-fill: blue");

//        Register Data

        TextField newID = new TextField();
        newID.setPromptText("ID");

        TextField newFullName = new TextField();
        newFullName.setPromptText("Full Name");

        TextField newMajor = new TextField();
        newMajor.setPromptText("Major");

        TextField newEmail = new TextField();
        newEmail.setPromptText("Email address");


        Button registerBtn = new Button("Sign Up");
        registerBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 5;");
        registerBtn.setMaxWidth(Double.MAX_VALUE);


        VBox form = new VBox();
        form.setPadding(new Insets(20));
        form.setMaxWidth(300);
//        form.setAlignment(Pos.CENTER);

        VBox signInForm = new VBox(10,
                new Label("ID"),
                idField,
                new Label("Email Address"),
                emailField,
                loginBtn,
                forgotLink
        );

        // Form Sign Up
        VBox signUpForm = new VBox(10,
                new Label("ID"),
                newID,
                new Label("Full Name"),
                newFullName,
                new Label("Major"),
                newMajor,
                new Label("Email Address"),
                newEmail,
                registerBtn
        );

        // Set default form
        form.getChildren().setAll(signInForm);

        // Event Tab
        signInTab.setOnAction(e -> {
            header.setText("Sign In to Library App");
            form.getChildren().setAll(signInForm);
            signInTab.setSelected(true);
            signUpTab.setSelected(false);
        });

        signUpTab.setOnAction(e -> {
            header.setText("Sign Up to Library App");
            form.getChildren().setAll(signUpForm);
            signInTab.setSelected(false);
            signUpTab.setSelected(true);
        });

        loginBtn.setOnAction(e->{
//            app.showDashboardUser();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (idField.getText().equals("A01") && emailField.getText().equals("admin@gmail.com")){
                alert.setContentText("Login anda berhasil");
                alert.showAndWait();
                app.showDashboardUser();
            }
//            if (idField)
        });

        // Form Wrapper (putih, rounded)
        VBox formWrapper = new VBox(form);
        formWrapper.setStyle("-fx-background-color: white; -fx-background-radius: 0 0 20 20; -fx-padding: 20;");
        formWrapper.setAlignment(Pos.CENTER);

        VBox headerWrapper = new VBox(headerBox, tabBox);
        headerWrapper.setSpacing(0);

        VBox card = new VBox(headerWrapper, formWrapper);
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);");

//        // Layout
//        StackPane root = new StackPane(card);
//        root.setStyle("-fx-background-color: #4d4dff;"); // Background biru
//        root.setPadding(new Insets(40));
//        StackPane.setAlignment(card, Pos.CENTER);
//
//        // Scene
//        Scene scene = new Scene(root, 900, 500);
//        scene.getStylesheets().add(getClass().getResource("tabs.css").toExternalForm());
        this.getChildren().add(card);
        this.setStyle("-fx-background-color: #4d4dff;");
        this.setPadding(new Insets(40));
        this.setAlignment(card, Pos.CENTER);

    }


}
