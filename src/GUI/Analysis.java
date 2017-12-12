package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class Analysis {
    static void analysisView(Stage primaryStage, Application app) {
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

        Label inLabel = new Label(GUI.resourceBundle.getString("file.to.read.data.from"));
        TextField inFilenameField = new TextField("");
        Button fileButton = new Button(GUI.resourceBundle.getString("choose.file"));
        fileButton.setOnMouseClicked(event ->
        {
            File f = null;  //ask user for file
            try {
                f = Util.getFile();
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
            if(f == null) {
                return; //No file
            }
            inFilenameField.setText(f.getName()); //display file name
        });
        HBox inFileBox = new HBox(10);
        inFileBox.setAlignment(Pos.CENTER);
        inFileBox.getChildren().addAll(inLabel, inFilenameField, fileButton);
        grid.add(inFileBox, 1, 1);

        Label outLabel = new Label(GUI.resourceBundle.getString("file.to.write.table.to"));
        TextField outFilenameField = new TextField("");
        Button outfileButton = new Button(GUI.resourceBundle.getString("choose.file"));
        fileButton.setOnMouseClicked(event ->
        {
            File f = null;  //ask user for file
            try {
                f = Util.getFile();
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
            if(f == null) {
                return; //No file
            }
            outFilenameField.setText(f.getName()); //display file name
        });
        HBox outFileBox = new HBox(10);
        outFileBox.setAlignment(Pos.CENTER);
        outFileBox.getChildren().addAll(outLabel, outFilenameField, outfileButton);
        grid.add(outFileBox, 1, 2);

        HBox tokenBox = new HBox();
        Label tokenLabel = new Label(GUI.resourceBundle.getString("choose.token"));
        ToggleGroup tokenToggle = new ToggleGroup();
        RadioButton charToken = new RadioButton(GUI.resourceBundle.getString("character"));
        charToken.setToggleGroup(tokenToggle);
        charToken.setSelected(true);
        RadioButton sylToken = new RadioButton(GUI.resourceBundle.getString("syllable"));
        sylToken.setToggleGroup(tokenToggle);
        tokenBox.getChildren().addAll(tokenLabel, charToken, sylToken);
        grid.add(tokenBox, 1, 3);

        HBox typeBox = new HBox();
        Label typeLabel = new Label(GUI.resourceBundle.getString("choose.token"));
        ToggleGroup typeToggle = new ToggleGroup();
        RadioButton freqType = new RadioButton(GUI.resourceBundle.getString("frequency"));
        freqType.setToggleGroup(typeToggle);
        freqType.setSelected(true);
        RadioButton markovType = new RadioButton(GUI.resourceBundle.getString("markov"));
        markovType.setToggleGroup(typeToggle);
        typeBox.getChildren().addAll(typeLabel, freqType, markovType);
        grid.add(typeBox, 1, 4);

        Button goButton = new Button(GUI.resourceBundle.getString("analyse"));
        grid.add(goButton, 1, 5);

        Scene scene = new Scene(grid, 750, 500);
        primaryStage.setScene(scene);
    }
}
