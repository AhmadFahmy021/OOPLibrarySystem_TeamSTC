package resource.components.layout.admin;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.Index;

public class DashboardLayoutAdmin extends BorderPane {

    private VBox mainContentContainer;

    public DashboardLayoutAdmin(Index app) {

        SideBarAdmin sideBar = new SideBarAdmin(app);
        this.setLeft(sideBar);

        VBox rightSideContent = new VBox();
        rightSideContent.setSpacing(0);

        TopBarAdmin topBar = new TopBarAdmin(app);
        rightSideContent.getChildren().add(topBar);

        mainContentContainer = new VBox(10); // Spasi antar elemen dalam konten utama
        mainContentContainer.setStyle("-fx-background-color: #f5f5f5;"); // Background untuk area konten utama

        VBox.setVgrow(mainContentContainer, javafx.scene.layout.Priority.ALWAYS);

        rightSideContent.getChildren().add(mainContentContainer);

        this.setCenter(rightSideContent);
    }
    public void setContent(javafx.scene.Node node) {
        mainContentContainer.getChildren().clear(); // Hapus konten sebelumnya
        mainContentContainer.getChildren().add(node); // Tambahkan konten baru
    }
}
