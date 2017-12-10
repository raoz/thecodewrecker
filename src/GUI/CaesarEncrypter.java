package GUI;

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
            } catch( Exception e ) {
                System.out.println("Incorrect key error!");
                return "";
            }
        }
    }
    static void writeToFile(String message, Stage stage)throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to write to");
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null){
            FileWriter writer = new FileWriter(selectedFile);
            writer.write(message);
            writer.close();
        }

    }
    static String readFromFile() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to read from");
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        }
        return "";
    }
}
