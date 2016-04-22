package businessLogic;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadData {

    private final String sourceContent;

    protected DownloadData(String sourcePath) throws FileNotFoundException {
        sourceContent = getDataFromSource(sourcePath);
    }

    private String getDataFromSource(String sourcePath) throws FileNotFoundException {
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new InputStreamReader(new URL(sourcePath).openStream(), "UTF-8"));
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine + System.lineSeparator());
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 404 / Page not found.");
        }
        throw new FileNotFoundException();
    }

    @Override
    public String toString() {
        return "DownloadData{" +
                "sourceContent='" + sourceContent + '\'' +
                '}';
    }
}