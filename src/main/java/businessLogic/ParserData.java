package businessLogic;

import dataLayer.ExchangeRates;
import dataLayer.TableSections;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.StringJoiner;

public class ParserData {

    private final String source;
    private Elements inputElements;
    private Document document;
    private ArrayList<ExchangeRates> listOfExchangeRates;
    private ExchangeRates exchangeRates;

    public ParserData(String source) {
        this.source = source;
    }

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

    public String getListOfExchangeRates() {
        StringJoiner tmp = new StringJoiner("");
        for (ExchangeRates exchangeRates : listOfExchangeRates) {
            tmp.add(exchangeRates.toString());
        }
        return tmp.toString();
    }

    public void saveTableName() {
        exchangeRates.setTableName(document.select("p[class=\"nag\"]").text());
    }

    public String getTableName() {
        return exchangeRates.getTableName();
    }
}