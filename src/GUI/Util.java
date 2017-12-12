package GUI;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;

public class Util {
    static void writeToFile(String message)throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to write to");
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null){
            try(FileWriter writer = new FileWriter(selectedFile)) {
                writer.write(message);
            }
        }
    }
    static String readFromFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to read from");
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String line = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                return sb.toString();
            }
        }
        return "";
    }
    static String getFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open analysis file");
        File file = fileChooser.showOpenDialog(null);
        return file.getName();
    }

    static void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GUI.resourceBundle.getString("error"));
        alert.setHeaderText(GUI.resourceBundle.getString("there.was.an.error"));
        alert.setContentText(text);

        alert.showAndWait();
    }
}
