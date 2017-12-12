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

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class CodeWrecker {
    static void crackView(Stage primaryStage, Application app) {
        final Map<String, Core.Analysis> analyses = new HashMap<>(); // List of filename->analysus
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label strLabel = new Label(GUI.resourceBundle.getString("string.to.crack"));
        TextArea decryptArea = new TextArea();
        HBox strHb = new HBox();
        strHb.getChildren().addAll(strLabel, decryptArea);
        strHb.setSpacing(10);
        grid.add(strHb, 1, 1);

        Button readButton = new Button(GUI.resourceBundle.getString("read.string.from.file"));
        readButton.setOnMouseClicked(event -> {
            try {
                decryptArea.setText(Util.readFromFile());
            } catch (Exception e) {
                Util.error(GUI.resourceBundle.getString("an.unknown.error.has.occured"));
                e.printStackTrace();
            }
        });

        HBox hbReadButton = new HBox(10);
        hbReadButton.setAlignment(Pos.CENTER);
        hbReadButton.getChildren().add(readButton);
        grid.add(hbReadButton, 1, 0);

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
        TextField analysesField = new TextField();
        HBox fileHb = new HBox();
        VBox selectedFiles = new VBox();
        fileHb.getChildren().addAll(fileLabel, selectedFiles);
        fileHb.setSpacing(10);
        grid.add(fileHb, 1, 3);

        Button crackButton = new Button(GUI.resourceBundle.getString("crack"));
        crackButton.setOnMouseClicked(event -> {
            Decryption[] solutions = CodeWrecker.main(analyses.values(), decryptArea.getText(), numField.getText());
            if (solutions == null) { //No solutions; something bad happened
                crackedField.setText("");
            } else {
                crackedField.setText(
                        Arrays.stream(solutions) //Take the solutions
                                .map(Decryption::toString). //Convert to string
                                collect(Collectors.joining("\n")) //Join with newlines
                );
            }
        });
        HBox hbCrackButton = new HBox(10);
        hbCrackButton.setAlignment(Pos.CENTER);
        hbCrackButton.getChildren().add(crackButton);
        grid.add(hbCrackButton, 1, 5);

        Button analysisButton = new Button(GUI.resourceBundle.getString("add.analysis.files"));
        analysisButton.setOnMouseClicked(event -> {
            try {
                File analysisFile = Util.getFile();
                if(analysisFile == null) { //User pressed Cancel
                    return;
                }

                //Convert to string
                String analysisString = Core.Util.readWholeStream(new FileInputStream(analysisFile));
                Core.Analysis analysis = Core.Analysis.getFromString(analysisString);
                analyses.put(analysisFile.getName(), analysis);

                HBox analysisEntry = new HBox();
                Label analysisLabel = new Label(analysisFile.getName());
                selectedFiles.getChildren().add(analysisEntry);
                Button removeButton = new Button(GUI.resourceBundle.getString("remove"));
                removeButton.setOnMouseClicked(event1 -> {
                    analyses.remove(analysisFile.getName()); //Remove from list
                    selectedFiles.getChildren().remove(analysisEntry); //Remove from display
                });
                analysisEntry.getChildren().addAll(analysisLabel, removeButton);
            } catch (ArrayIndexOutOfBoundsException e) {
                Util.error(GUI.resourceBundle.getString("invalid.analysis.file"));
            } catch (IllegalArgumentException e) {
                Util.error(GUI.resourceBundle.getString("invalid.analysis.file"));
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


    public static Decryption[] main(Collection<Analysis> analyses, String message, String number) {
        //Build the analysis
        CompoundAnalysis analysis = new CompoundAnalysis(new ArrayList<>());
        analyses.forEach(analysis::addAnalysis);
        if(Decrypter.decrypters.size() == 0) {
            Decrypter.registerDecrypterFinder(CaesarDecrypter::findBest);
        }

        try {
            //Number of solutions
            int n = Integer.parseInt(number);
            if (n < 1 || n > 26) {
                throw new IllegalArgumentException("n must be in range [1..26]");
            }
            return Decrypter.findBest(message, n, analysis);
        } catch (IllegalStateException e) {
            Util.error(GUI.resourceBundle.getString("no.analysis.files"));
        } catch (IllegalFormatException e) {
            Util.error(GUI.resourceBundle.getString("number.of.solutions.must.be.numeric"));
        } catch (IllegalArgumentException e) {
            Util.error(GUI.resourceBundle.getString("incorrect.number.of.solutions.requested"));
        }
        return null;
    }
}
