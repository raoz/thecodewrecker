package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.function.Function;

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
        Button inFileButton = new Button(GUI.resourceBundle.getString("choose.file"));
        inFileButton.setOnMouseClicked(event ->
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
            inFilenameField.setText(f.getAbsolutePath()); //display file name
        });
        HBox inFileBox = new HBox(10);
        inFileBox.setAlignment(Pos.CENTER);
        inFileBox.getChildren().addAll(inLabel, inFilenameField, inFileButton);
        grid.add(inFileBox, 1, 1);

        Label outLabel = new Label(GUI.resourceBundle.getString("file.to.write.table.to"));
        TextField outFilenameField = new TextField("");
        Button outfileButton = new Button(GUI.resourceBundle.getString("choose.file"));
        outfileButton.setOnMouseClicked(event ->
        {
            File f = null;  //ask user for file
            try {
                f = Util.getOutFile();
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
            if(f == null) {
                return; //No file
            }
            outFilenameField.setText(f.getAbsolutePath()); //display file name
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
        goButton.onMouseClickedProperty().set(event -> {
            Function<String, List> t;
            if(tokenToggle.getSelectedToggle() == charToken) {
                t = Core.NaturalLanguage::characters;
            } else {
                t = Core.NaturalLanguage::naiveSyllables;
            }
            Core.Analysis a;
            if(typeToggle.getSelectedToggle() == freqType) {
                a = new Core.FrequencyAnalysis(t);
            }else{
                a= new Core.MarkovAnalysis(t);
            }
            try {
                main(new File(inFilenameField.getText()), new File(outFilenameField.getText()), a);
            } catch (IOException e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });
        grid.add(goButton, 1, 5);

        Scene scene = new Scene(grid, 750, 500);
        primaryStage.setScene(scene);
    }
    static void main(File inputFile, File outputFile, Core.Analysis a) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)))) {
            br.lines().forEach(a::addData);

            try(PrintStream pr = new PrintStream(new FileOutputStream(outputFile))) {
                pr.println(a.toString());
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(GUI.resourceBundle.getString("done"));

            alert.showAndWait();

        }
    }
}
