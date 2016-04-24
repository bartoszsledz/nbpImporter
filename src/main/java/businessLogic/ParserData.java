package businessLogic;

import dataLayer.ExchangeRates;
import dataLayer.TableSections;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * The main class responsible for the data is converted into object {@link ExchangeRates}.
 */
public class ParserData {

    private final String source;
    private Elements inputElements;
    private Document document;
    private ArrayList<ExchangeRates> listOfExchangeRates;
    private ExchangeRates exchangeRates;

    /**
     * This is the constructor who gets all the code page as String from {@link DownloadData} class.
     * @param source - page source in the form of String.
     */
    public ParserData(String source) {
        this.source = source;
    }

    /**
     * This is the main method converts the data to objects {@link ExchangeRates}.
     * @return a list of exchange rates from web page.
     */
    public ArrayList<ExchangeRates> convertToObject() {
        listOfExchangeRates = new ArrayList<>();
        importData();

        for (Element row : inputElements) {
            Elements elements = row.select("td");
            exchangeRates = new ExchangeRates();

            exchangeRates.setCurrency(elements.get(TableSections.CURRENCY).text());
            exchangeRates.setCode(elements.get(TableSections.CODE).text());
            exchangeRates.setMidRate(Double.parseDouble(elements.get(TableSections.MID_RATE).text()));
            listOfExchangeRates.add(exchangeRates);
        }
        saveTableName();
        return listOfExchangeRates;
    }

    private void importData() {
        document = Jsoup.parse(source);
        Element table = document.select("table[class=\"pad5\"]").first();
        inputElements = table.getElementsByTag("tr");
        inputElements.remove(0);
    }

    /**
     * This method provides a formatted list of data.
     * @return a formatted list of data.
     */
    public String getListOfExchangeRates() {
        StringJoiner tmp = new StringJoiner("");
        for (ExchangeRates exchangeRates : listOfExchangeRates) {
            tmp.add(exchangeRates.toString());
        }
        return tmp.toString();
    }

    /**
     * This method sets the name of the table from web page.
     */
    public void saveTableName() {
        exchangeRates.setTableName(document.select("p[class=\"nag\"]").text());
    }

    /**
     * This method gets the name of the table.
     * @return table name.
     */
    public String getTableName() {
        return exchangeRates.getTableName();
    }
}