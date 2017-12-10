package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class GUI extends Application {
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");

    public static void main(String[] args) {
        launch(args);
    }

    private void crackView(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label strLabel = new Label(resourceBundle.getString("string.to.crack"));
        TextField strField = new TextField();
        HBox strHb = new HBox();
        strHb.getChildren().addAll(strLabel, strField);
        strHb.setSpacing(10);
        grid.add(strHb, 1, 1);

        Button crackButton = new Button(resourceBundle.getString("crack"));
        HBox hbCrackButton = new HBox(10);
        hbCrackButton.setAlignment(Pos.CENTER);
        hbCrackButton.getChildren().add(crackButton);
        grid.add(hbCrackButton, 1, 2);

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
    }

    public void encryptView(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button backButton = new Button(resourceBundle.getString("back"));
        backButton.setOnMouseClicked(event -> start(primaryStage));
        HBox hbBackButton = new HBox(10);
        hbBackButton.setAlignment(Pos.CENTER);
        hbBackButton.getChildren().add(backButton);
        grid.add(hbBackButton, 1,6);

        Label strLabel = new Label(resourceBundle.getString("string.to.encrypt"));
        TextField strField = new TextField();
        HBox strHb = new HBox();
        strHb.getChildren().addAll(strLabel, strField);
        strHb.setSpacing(10);
        grid.add(strHb, 1, 1);

        Label keyLabel = new Label(resourceBundle.getString("key.to.use.for.encryption"));
        TextField keyField = new TextField();
        HBox keyHb = new HBox();
        keyHb.getChildren().addAll(keyLabel, keyField);
        keyHb.setSpacing(5);
        grid.add(keyHb, 1, 2);

        Label textLabel = new Label(resourceBundle.getString("encrypted.text"));
        TextField textField = new TextField();
        HBox textHb = new HBox();
        textHb.getChildren().addAll(textLabel, textField);
        textHb.setSpacing(10);
        grid.add(textHb, 1, 4);

        Button encryptButton = new Button(resourceBundle.getString("encrypt"));
        encryptButton.setOnMouseClicked(event -> textField.setText(CaesarEncrypter.main(strField.getText(), keyField.getText())));
        HBox hbEncryptButton = new HBox(10);
        hbEncryptButton.setAlignment(Pos.CENTER);
        hbEncryptButton.getChildren().add(encryptButton);
        grid.add(hbEncryptButton, 1,3);

        Button writeButton = new Button(resourceBundle.getString("write.to.file"));
        writeButton.setOnMouseClicked(event -> {
            try {
                CaesarEncrypter.writeToFile(textField.getText(), primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        HBox hbWriteButton = new HBox(10);
        hbWriteButton.setAlignment(Pos.CENTER);
        hbWriteButton.getChildren().add(writeButton);
        grid.add(hbWriteButton, 1,5);

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
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

        Button decryptButton = new Button(resourceBundle.getString("crack.an.encrypted.string"));
        decryptButton.setOnMouseClicked(event -> crackView(primaryStage));
        HBox hbDecryptButton = new HBox(10);
        hbDecryptButton.setAlignment(Pos.CENTER);
        hbDecryptButton.getChildren().add(decryptButton);
        grid.add(hbDecryptButton, 1, 1);

        Button encryptButton = new Button(resourceBundle.getString("encrypt.a.string"));
        encryptButton.setOnMouseClicked(event -> encryptView(primaryStage));
        //encryptButton.setOnMouseClicked(event -> (new Alert(Alert.AlertType.INFORMATION, "Encrypt!", ButtonType.OK)).show());
        HBox hbEncryptButton = new HBox(10);
        hbEncryptButton.setAlignment(Pos.CENTER);
        hbEncryptButton.getChildren().add(encryptButton);
        grid.add(hbEncryptButton, 1, 2);

        Button analysisButton = new Button(resourceBundle.getString("generate.an.analysis.table"));
        analysisButton.setOnMouseClicked(event -> new Alert(Alert.AlertType.INFORMATION, "Here be an analysis table one day!", ButtonType.OK).show()  );
        HBox hbAnalysisButton = new HBox(10);
        hbAnalysisButton.setAlignment(Pos.CENTER);
        hbAnalysisButton.getChildren().add(analysisButton);
        grid.add(hbAnalysisButton, 1, 3);
    }
}
