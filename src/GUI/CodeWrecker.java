package GUI;

import Core.*;
import Core.Analysis;
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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class CodeWrecker {
    static void crackView(Stage primaryStage, Application app) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label strLabel = new Label(GUI.resourceBundle.getString("string.to.crack"));
        TextField strField = new TextField();
        HBox strHb = new HBox();
        strHb.getChildren().addAll(strLabel, strField);
        strHb.setSpacing(10);
        grid.add(strHb, 1, 1);

        Label numLabel = new Label(GUI.resourceBundle.getString("number.of.solutions.to.output"));
        TextField numField = new TextField();
        HBox numHb = new HBox();
        numHb.getChildren().addAll(numLabel, numField);
        numHb.setSpacing(10);
        grid.add(numHb, 1,4);

        Label crackedLabel = new Label(GUI.resourceBundle.getString("solutions"));
        TextArea  crackedField = new TextArea();
        VBox crackedHb = new VBox(10);
        crackedField.setPrefSize(300, 100);
        crackedHb.getChildren().addAll(crackedLabel, crackedField);
        crackedHb.setSpacing(10);
        grid.add(crackedHb, 1, 6);

        Label fileLabel = new Label(GUI.resourceBundle.getString("files.selected"));
        TextField fileField = new TextField();
        HBox fileHb = new HBox();
        fileHb.getChildren().addAll(fileLabel, fileField);
        fileHb.setSpacing(10);
        grid.add(fileHb, 1, 3);

        Button crackButton = new Button(GUI.resourceBundle.getString("crack"));
        crackButton.setOnMouseClicked(event -> {crackedField.setText("");
            Decryption[] ds = CodeWrecker.main(fileField.getText(), strField.getText(), numField.getText()); for(Decryption d : ds){
                crackedField.setText(crackedField.getText() + " " + d);
            }
        });
        HBox hbCrackButton = new HBox(10);
        hbCrackButton.setAlignment(Pos.CENTER);
        hbCrackButton.getChildren().add(crackButton);
        grid.add(hbCrackButton, 1, 5);

        Button analysisButton = new Button(GUI.resourceBundle.getString("add.analysis.files"));
        analysisButton.setOnMouseClicked(event -> {
            try {
                String file = Util.getFile();
                fileField.setText(file + ";" + fileField.getText());
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });
        HBox hbAnalysisButton = new HBox(10);
        hbAnalysisButton.setAlignment(Pos.CENTER);
        hbAnalysisButton.getChildren().add(analysisButton);
        grid.add(hbAnalysisButton, 1, 2);

        Button saveButton = new Button(GUI.resourceBundle.getString("save.solutions.to.file"));
        saveButton.setOnMouseClicked(event -> {
            try {
                Util.writeToFile(crackedField.getText());
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });
        HBox hbSaveButton = new HBox(10);
        hbSaveButton.getChildren().add(saveButton);
        grid.add(hbSaveButton, 1, 7);

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

        Scene scene = new Scene(grid, 750, 500);
        primaryStage.setScene(scene);
    }
    public static Decryption[] main(String s, String message, String number) {

        //Build the analysis
        String[] filenames = s.split(";");
        CompoundAnalysis analysis = new CompoundAnalysis(new ArrayList<>());
        for (String filename : filenames) {
            try {
                String data = Core.Util.readWholeStream(new FileInputStream(filename));
                analysis.addAnalysis(Analysis.getFromString(data));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
                System.out.println("Ignoring.");
            }
        }
        //Add the decrypters
        Decrypter.registerDecrypterFinder(CaesarDecrypter::findBest);

        //Change where you get solutions amount

        try {
            int n = Integer.parseInt(number);
            if (n < 1 || n > 26) {
            throw new IllegalArgumentException("n must be in range [1..26]");
            }
            Decryption[] ds = Decrypter.findBest(message, n, analysis);
            //Fix output
            return ds;
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect number of solutions");
            return null;
        }
    }
}
