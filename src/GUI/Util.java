package GUI;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.stream.Collectors;

public class Util {
    /**
     * Writes a message to a file chosen by user
     * @param message Message to write to file
     * @throws Exception
     */
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

    /**
     * @return The contents of an user chosen file in a String; returning empty string if no file chosen
     * @throws Exception
     */
    static String readFromFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to read from");
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        }
        return "";
    }

    /**
     * @return File chosen by user
     * @throws Exception
     */
    static File getFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(GUI.resourceBundle.getString("open.analysis.file"));
        return fileChooser.showOpenDialog(null);
    }

    /**
     * Displays an error prompt
     * @param text Error text
     */
    static void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GUI.resourceBundle.getString("error"));
        alert.setHeaderText(GUI.resourceBundle.getString("there.was.an.error"));
        alert.setContentText(text);

        alert.showAndWait();
    }
}
