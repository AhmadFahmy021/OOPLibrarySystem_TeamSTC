package resource.components.layout.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import main.Index;

public class TopBarAdmin extends HBox {

    private Index app; // Assuming you have an 'Index' class to handle scene switching

    public TopBarAdmin(Index app) {
        this.app = app;
        setStyle("-fx-background-color: white;");
        setPadding(new Insets(10, 20, 10, 20));
        setSpacing(20);
        setAlignment(Pos.CENTER_RIGHT);

        Label welcomeLabel = new Label("Welcome Back, Ahmad");
        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Circle avatar = new Circle(15, Color.web("#3f48cc"));
        Text usernameHuruf = new Text("A");
        usernameHuruf.setFill(Paint.valueOf("FFFFFF"));
        StackPane cicleProfile = new StackPane(avatar, usernameHuruf);

        ComboBox<String> usernameBox = new ComboBox<>();
        usernameBox.setPromptText("Ahmad");
        usernameBox.getItems().addAll("Profile", "Logout");
        usernameBox.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
        usernameBox.setOnAction(e -> {
            if ("Logout".equals(usernameBox.getValue())) {
                app.showLoginPage();
            } else {
                // This part sets the button cell back to "Ahmad" if "Profile" is selected
                usernameBox.setButtonCell(new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText("Ahmad");
                    }
                });
            }
        });

        HBox profile = new HBox(10, cicleProfile, usernameBox);
        profile.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(welcomeLabel, spacer, profile);
    }
}
