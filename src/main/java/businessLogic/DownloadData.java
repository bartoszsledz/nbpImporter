package businessLogic;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Base class for importers which read data from page.
 * Class returns for {@link ParserData} the read page in the form of String.
 */
public class DownloadData {

    private final String sourceContent;

    /**
     * Constructor , which gets the address of the page.
     *
     * @param sourcePath - address.
     * @throws FileNotFoundException when the path is invalid.
     */
    DownloadData(final String sourcePath) throws FileNotFoundException {
        sourceContent = getDataFromSource(sourcePath);
    }

    private String getDataFromSource(final String sourcePath) throws FileNotFoundException {
        final BufferedReader bufferedReader;
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new InputStreamReader(new URL(sourcePath).openStream(), "UTF-8"));
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ".");
        } catch (final Exception e) {
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