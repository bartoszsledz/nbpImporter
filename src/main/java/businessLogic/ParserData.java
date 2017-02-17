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
class ParserData {

    private Document document;
    private ArrayList<ExchangeRates> listOfExchangeRates;
    private ExchangeRates exchangeRates;

    ParserData() {
    }

    /**
     * This is the main method converts the data to objects {@link ExchangeRates}.
     *
     * @param dataToConvert String html resource.
     * @return a list of exchange rates from web page.
     */
    ArrayList<ExchangeRates> convertToObject(final String dataToConvert) {
        listOfExchangeRates = new ArrayList<>();
        document = Jsoup.parse(dataToConvert);

        final Element table = document.select("table[class=\"pad5\"]").first();
        final Elements inputElements = table.getElementsByTag("tr");
        inputElements.remove(0);

        for (final Element row : inputElements) {
            final Elements elements = row.select("td");
            exchangeRates = new ExchangeRates();
            exchangeRates.setCurrency(elements.get(TableSections.CURRENCY).text());
            exchangeRates.setCode(elements.get(TableSections.CODE).text());
            exchangeRates.setMidRate(Double.parseDouble(elements.get(TableSections.MID_RATE).text()));
            exchangeRates.setTableName(helpToGetTableName(document.select("p[class=\"nag\"]").text()));
            exchangeRates.setDate(helpToGetDate(document.select("p[class=\"nag\"]").text()));
            exchangeRates.setTableType(helpToSetTableType(document.select("p[class=\"head2\"]").text()));
            listOfExchangeRates.add(exchangeRates);
        }
        return listOfExchangeRates;
    }

    private String helpToGetDate(final String value) {
        return value.substring(value.length() - 10);
    }

    private String helpToGetTableName(final String value) {
        return value.substring(0, 24);
    }

    private String helpToSetTableType(final String value) {
        return value.substring(value.length() - 1);
    }

    /**
     * This method provides a formatted list of data.
     *
     * @return a formatted list of data.
     */
    String getListOfExchangeRates() {
        final StringJoiner tmp = new StringJoiner("");
        for (final ExchangeRates exchangeRates : listOfExchangeRates) {
            tmp.add(exchangeRates.toString());
        }
        return tmp.toString();
    }

    ArrayList<ExchangeRates> getExchangeRates() {
        return listOfExchangeRates;
    }

    /**
     * This method sets the name of the table from web page.
     */
    void setTableName() {
        exchangeRates.setTableName(document.select("p[class=\"nag\"]").text());
    }

    /**
     * This method gets the name of the table.
     *
     * @return table name.
     */
    String getTableName() {
        return exchangeRates.getTableName();
    }
}