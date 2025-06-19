package resource.view.admin;

import data.Book;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Index;
import resource.view.user.DashboardUser;

public class DashboardAdmin extends VBox{
    private Label memberTotalLabel;
    private Label borrowTotalLabel;

    public DashboardAdmin(Index app){

        this.setStyle("-fx-background-color: #f5f5f5;");
        this.setSpacing(10);
        this.setPadding(new Insets(20));

        HBox statCards = new HBox(20);
        statCards.setAlignment(Pos.CENTER);

        VBox memberTotalCard = createUpdatableCard("total member\t", "#1D84DE");
        memberTotalLabel = (Label) memberTotalCard.getChildren().get(1); // Dapatkan Label nilai

        VBox borrowBookCard = createUpdatableCard("total book   ", "#15B144");
        borrowTotalLabel = (Label) borrowBookCard.getChildren().get(1); // Dapatkan Label nilai

        statCards.getChildren().addAll(memberTotalCard, borrowBookCard);

        Label dataBookLabel = new Label("Data Book");
        dataBookLabel.setStyle("-fx-background-color: #3f48cc; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10 10 0 0;");
        dataBookLabel.setPadding(new Insets(10));
        dataBookLabel.setMaxWidth(Double.MAX_VALUE);

        // 1. Definisikan Sumbu
        final CategoryAxis xAxis = new CategoryAxis(); // Sumbu X untuk kategori (misalnya bulan)
        final NumberAxis yAxis = new NumberAxis();    // Sumbu Y untuk nilai numerik
        xAxis.setLabel("Bulan");
        yAxis.setLabel("Penjualan");

        // 2. Buat Bar Chart
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Penjualan Bulanan");

        // 3. Siapkan Data Series (misalnya, satu baris data untuk tahun 2023)
        XYChart.Series<String, Number> series2023 = new XYChart.Series<>();
        series2023.setName("Tahun 2023");

        // Tambahkan data ke series
        series2023.getData().add(new XYChart.Data<>("Jan", 2500));
        series2023.getData().add(new XYChart.Data<>("Feb", 3000));
        series2023.getData().add(new XYChart.Data<>("Mar", 2800));
        series2023.getData().add(new XYChart.Data<>("Apr", 3200));
        series2023.getData().add(new XYChart.Data<>("Mei", 3500));
        series2023.getData().add(new XYChart.Data<>("Jun", 4000));
        // ... bisa tambahkan bulan lain

        // Anda bisa menambahkan Series lain (misalnya untuk tahun 2024)
        XYChart.Series<String, Number> series2024 = new XYChart.Series<>();
        series2024.setName("Tahun 2024");
        series2024.getData().add(new XYChart.Data<>("Jan", 2800));
        series2024.getData().add(new XYChart.Data<>("Feb", 3300));
        series2024.getData().add(new XYChart.Data<>("Mar", 3000));

        // 4. Tambahkan Series ke Chart
        barChart.getData().addAll(series2023, series2024);

        // Pengaturan tambahan untuk Bar Chart (opsional)
        barChart.setCategoryGap(20); // Jarak antar kategori
        barChart.setBarGap(3);

        VBox tableBox = new VBox(dataBookLabel, barChart);
        tableBox.setStyle("-fx-background-color: #f8f8f8; -fx-background-radius: 0 10; -fx-border-radius: 0 10;");
        tableBox.setPadding(new Insets(10));


//        table.setStyle("-fx-background-radius: 10; -fx-border-radius: 0 10;");

        this.getChildren().addAll(statCards, tableBox);

        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    private VBox createUpdatableCard(String title, String color) {
        Label titleLabel = new Label(title.toUpperCase());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Label nilai ini akan diisi dengan "0" pada awalnya dan kemudian diupdate
        Label valueLabel = new Label("0"); // Nilai awal
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setStyle("-fx-font-size: 44px; -fx-font-weight: bold;");

        VBox box = new VBox(5, titleLabel, valueLabel);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 8px;");
        return box;
    }

    public void setBorrowTotalLabel(int borrowTotalLabel) {
        this.borrowTotalLabel.setText(String.valueOf(borrowTotalLabel));
    }

    public void setMemberTotalLabel(int memberTotalLabel) {
        this.memberTotalLabel.setText(String.valueOf(memberTotalLabel));
    }
}
