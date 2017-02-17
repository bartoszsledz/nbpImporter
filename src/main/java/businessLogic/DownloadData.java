package businessLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Base class for importers which read data from page.
 * Class returns for {@link ParserData} the read page in the form of String.
 */
class DownloadData {

    DownloadData() {
    }

    String getDataFromSource(final String urlLink) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            String currentLine;
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(urlLink).openStream(), "UTF-8"));
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
            bufferedReader.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString().replace(",", ".");
    }
}