package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CodeWrecker");
        primaryStage.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        Button decryptButton = new Button("Crack an encrypted string");
        HBox hbDecryptButton = new HBox(10);
        hbDecryptButton.setAlignment(Pos.CENTER);
        hbDecryptButton.getChildren().add(decryptButton);
        grid.add(hbDecryptButton, 1, 1);

        Button encryptButton = new Button("Encrypt a string");
        HBox hbEncryptButton = new HBox(10);
        hbEncryptButton.setAlignment(Pos.CENTER);
        hbEncryptButton.getChildren().add(encryptButton);
        grid.add(hbEncryptButton, 1, 2);

        Button analysisButton = new Button("Generate an analysis table");
        HBox hbAnalysisButton = new HBox(10);
        hbAnalysisButton.setAlignment(Pos.CENTER);
        hbAnalysisButton.getChildren().add(analysisButton);
        grid.add(hbAnalysisButton, 1, 3);
    }
}
