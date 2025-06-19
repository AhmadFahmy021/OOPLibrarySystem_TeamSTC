package resource.components;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;

import java.util.function.Consumer;

public class CardHeader extends HBox {
    private Button actionButton;

    public CardHeader(String labelText, String buttonText, String backgroundColor, String buttonColor, Consumer<ActionEvent> onButtonClickAction) {
//        HBox headerBox = new HBox(10); // Spasi 10px antar elemen
        this.setAlignment(Pos.CENTER_LEFT); // Elemen di tengah vertikal, di kiri horizontal
        this.setPadding(new Insets(10, 20, 10, 20)); // Padding di sekitar header
        this.setStyle("-fx-background-color: " + backgroundColor + "; " +
                "-fx-background-radius: 10 10 0 0 ;"); // Radius sudut jika diinginkan

        // Label
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE); // Teks putih sesuai gambar
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, 18)); // Font dan ukuran sesuai gambar

        // Spacer (Pemisah)
        Region spacer = new Region(); // Region kosong yang akan tumbuh
        HBox.setHgrow(spacer, Priority.ALWAYS); // Memberi tahu HBox agar spacer mengambil semua ruang ekstra

        // Button
        actionButton = new Button(buttonText);
        actionButton.setTextFill(Color.WHITE); // Teks hitam untuk button
        actionButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14)); // Font dan ukuran button
        actionButton.setStyle("-fx-background-color: " + buttonColor + "; " + // Warna background button
                "-fx-text-fill: black; " + // Pastikan teks hitam
                "-fx-padding: 8 20; " + // Padding di dalam button
                "-fx-background-radius: 10 10 10 10;"); // Radius sudut yang sangat besar untuk membuat oval
        if (onButtonClickAction != null) {
            actionButton.setOnAction(onButtonClickAction::accept);
        }

        // Tambahkan elemen ke HBox
        this.getChildren().addAll(label, spacer, actionButton);
    }

    public Button getAction(){
        return actionButton;
    }
}
