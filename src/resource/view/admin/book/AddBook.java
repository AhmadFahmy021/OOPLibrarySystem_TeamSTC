package resource.view.admin.book;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class AddBook extends VBox {

    private ImageView coverPreviewImageView;
    private TextField isbnField ;
    private TextField titleField;
    private TextField authorField;
    private TextField quantityField;
    private File selectedCoverFile;

    public AddBook(Consumer<ActionEvent> onSubmitAction){
        this.setStyle("-fx-background-color: #f5f5f5;");
        this.setSpacing(10);
        this.setPadding(new Insets(20));
//        VBox
        Label isbnLabel = new Label("ISBN");
        Label titleLabel = new Label("Title");
        Label authorLabel = new Label("Author");
        Label quantityLabel = new Label("Quantity");
        Label coverLabel = new Label("Cover Book");

        isbnField = new TextField();
        titleField = new TextField();
        authorField = new TextField();
        quantityField = new TextField();

        // Inisialisasi ImageView untuk pratinjau
        coverPreviewImageView = new ImageView();
        coverPreviewImageView.setFitWidth(100); // Ukuran pratinjau
        coverPreviewImageView.setFitHeight(133); // Sesuaikan rasio
        coverPreviewImageView.setPreserveRatio(true);
        coverPreviewImageView.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;"); // Border visual

        Button uploadImageButton = new Button("Upload Image");
        uploadImageButton.setOnAction(e -> {
            // Dapatkan Stage tempat VBox ini berada
            Stage stage = (Stage) this.getScene().getWindow();
            openFileChooser(stage);
        });

        HBox coverInputBox = new HBox(10);
        coverInputBox.getChildren().addAll(uploadImageButton, coverPreviewImageView);
        // --- Tombol Submit dan Cancel ---
        Button saveButton = new Button("Save Book");
        saveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        // Tautkan tombol save ke callback yang diterima di konstruktor
        if (onSubmitAction != null) {
            saveButton.setOnAction(onSubmitAction::accept);
        }

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> {
            // Logika untuk tombol cancel, misalnya kembali ke ManageBookPage
            // Anda mungkin perlu meneruskan callback lain untuk ini juga,
            // atau menggunakan referensi 'app' jika AddBook memiliki akses ke 'Index'.
            // Untuk sementara, kita bisa mencetak saja atau kembali ke ManageBookPage secara langsung
            System.out.println("Cancel button clicked!");
            // Asumsi AddBook tidak memiliki referensi app secara langsung,
            // ini perlu ditangani oleh controller yang memanggil AddBook.
        });


        HBox actionButtonsBox = new HBox(10);
        actionButtonsBox.setAlignment(Pos.CENTER_RIGHT); // Tombol di kanan
        actionButtonsBox.getChildren().addAll(cancelButton, saveButton);


        VBox inputContainer = new VBox(10);
        inputContainer.getChildren().addAll(
                isbnLabel, isbnField,
                titleLabel, titleField,
                authorLabel, authorField,
                quantityLabel, quantityField,
                coverLabel, coverInputBox
        );

        this.getChildren().addAll(inputContainer, actionButtonsBox);

//        // Tata tombol upload dan pratinjau dalam HBox
//        HBox coverInputBox = new HBox(10); // Spasi 10px antar elemen
//        coverInputBox.getChildren().addAll(uploadImageButton, coverPreviewImageView);
//
//        VBox inputContainer = new VBox(10); // Mengubah nama dari 'input' menjadi 'inputContainer'
//        inputContainer.getChildren().addAll(
//                isbnLabel, isbnField,
//                titleLabel, titleField,
//                authorLabel, authorField,
//                quantityLabel, quantityField,
//                coverLabel, coverInputBox // Tambahkan HBox untuk cover
//        );
//        Button buttonSubmit = new Button("Submit");
//        buttonSubmit.setOnAction(e->{
//            isbnField.clear();
//            titleField.clear();
//            authorField.clear();
//            quantityField.clear();
//            coverPreviewImageView.setImage(null);
//
//
//        });
//
//        VBox input = new VBox(10);
//        input.getChildren().addAll(inputContainer, buttonSubmit);
//
//        this.getChildren().addAll(input);
    }

    private void openFileChooser(Stage ownerStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Gambar Sampul Buku");

        // Filter hanya untuk file gambar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Tampilkan dialog dan dapatkan file yang dipilih
        File file = fileChooser.showOpenDialog(ownerStage);

        if (file != null) {
            try {
                // Simpan referensi file yang dipilih
                this.selectedCoverFile = file;

                // Muat gambar ke ImageView untuk pratinjau
                Image image = new Image(new FileInputStream(file));
                coverPreviewImageView.setImage(image);

                System.out.println("Gambar berhasil dipilih: " + file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                System.err.println("Error: File gambar tidak ditemukan: " + e.getMessage());
                // Tampilkan pesan error ke pengguna (misalnya, di Label status)
                coverPreviewImageView.setImage(null); // Kosongkan pratinjau
            } catch (Exception e) {
                System.err.println("Error memuat gambar: " + e.getMessage());
                e.printStackTrace();
                coverPreviewImageView.setImage(null); // Kosongkan pratinjau
            }
        } else {
            System.out.println("Pemilihan gambar dibatalkan.");
            // Jika pengguna membatalkan, pastikan pratinjau kosong
            // this.selectedCoverFile = null; // Bisa disetel null jika tidak ingin mempertahankan pilihan sebelumnya
            // coverPreviewImageView.setImage(null);
        }
    }

    // --- Metode untuk mendapatkan nilai input (opsional, jika Anda ingin mengambil data dari sini) ---
    public String getIsbnInput() {
        return isbnField.getText();
    }

    public String getTitleInput() {
        return titleField.getText();
    }

    public String getAuthorInput() {
        return authorField.getText();
    }

    public String getQuantityInput() {
        return quantityField.getText();
    }

    public File getSelectedCoverFile() {
        return selectedCoverFile;
    }

}
