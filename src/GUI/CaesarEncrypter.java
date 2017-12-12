package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

class CaesarEncrypter {
    static String main(String message, String keyStr){

        Core.CaesarEncrypter encrypter;
        if(keyStr.equals("")) {
            encrypter = new Core.CaesarEncrypter();
            return encrypter.encrypt(message);
        } else {
            try {
                int key = Integer.parseInt(keyStr);
                encrypter = new Core.CaesarEncrypter(key);
                return encrypter.encrypt(message);
            } catch( NumberFormatException e ) {
                Util.error(GUI.resourceBundle.getString("key.must.be.numeric"));
                return "";
            } catch (IllegalArgumentException e) {
                Util.error(GUI.resourceBundle.getString("key.must.be.in.range.0.26"));
                return "";
            }
        }
    }


    static void encryptView(Stage primaryStage, Application app) throws Exception{
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button backButton = new Button(GUI.resourceBundle.getString("back"));
        backButton.setOnMouseClicked(event -> {
            try {
                app.start(primaryStage);
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });
        HBox hbBackButton = new HBox(10);
        hbBackButton.setAlignment(Pos.CENTER);
        hbBackButton.getChildren().add(backButton);
        grid.add(hbBackButton, 1,8);

        Label strLabel = new Label(GUI.resourceBundle.getString("string.to.encrypt"));
        TextArea strField = new TextArea();
        VBox strHb = new VBox();
        strField.setPrefSize(300, 100);
        strHb.getChildren().addAll(strLabel, strField);
        strHb.setSpacing(10);
        grid.add(strHb, 1, 1);

        Button readButton = new Button(GUI.resourceBundle.getString("read.string.from.file"));
        readButton.setOnMouseClicked(event -> {
            try {
                strField.setText(Util.readFromFile());
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });
        HBox hbReadButton = new HBox(10);
        hbReadButton.setAlignment(Pos.CENTER);
        hbReadButton.getChildren().add(readButton);
        grid.add(hbReadButton, 1, 2);

        Label keyLabel = new Label(GUI.resourceBundle.getString("key.to.use.for.encryption"));
        TextField keyField = new TextField();
        HBox keyHb = new HBox();
        keyHb.getChildren().addAll(keyLabel, keyField);
        keyHb.setSpacing(5);
        grid.add(keyHb, 1, 3);

        Label textLabel = new Label(GUI.resourceBundle.getString("encrypted.text"));
        TextArea textField = new TextArea();
        VBox textHb = new VBox();
        textField.setPrefSize(300, 100);
        textHb.getChildren().addAll(textLabel, textField);
        textHb.setSpacing(10);
        grid.add(textHb, 1, 5);

        Button encryptButton = new Button(GUI.resourceBundle.getString("encrypt"));
        encryptButton.setOnMouseClicked(event -> textField.setText(CaesarEncrypter.main(strField.getText(), keyField.getText())));
        HBox hbEncryptButton = new HBox(10);
        hbEncryptButton.setAlignment(Pos.CENTER);
        hbEncryptButton.getChildren().add(encryptButton);
        grid.add(hbEncryptButton, 1,4);

        Label fileLabel = new Label("");
        fileLabel.textAlignmentProperty().set(TextAlignment.CENTER);
        fileLabel.setAlignment(Pos.CENTER);
        grid.add(fileLabel, 1, 7);

        Button writeButton = new Button(GUI.resourceBundle.getString("write.to.file"));
        writeButton.setOnMouseClicked(event -> {
            try {
                fileLabel.setText("");
                Util.writeToFile(textField.getText());
                fileLabel.setText(GUI.resourceBundle.getString("write.successful"));

            } catch (Exception e) {
                fileLabel.setText(GUI.resourceBundle.getString("write.failed"));
            }
        });

        HBox hbWriteButton = new HBox(10);
        hbWriteButton.setAlignment(Pos.CENTER);
        hbWriteButton.getChildren().add(writeButton);
        grid.add(hbWriteButton, 1,6);

        Scene scene = new Scene(grid, 750, 500);
        primaryStage.setScene(scene);
    }

}
