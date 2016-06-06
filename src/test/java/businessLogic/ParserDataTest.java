package businessLogic;

import dataLayer.ExchangeRates;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * It's a test class for class {@link ParserData}.
 */
public class ParserDataTest {

    private StringBuilder stringBuilder = new StringBuilder();
    private String finalSourceString;

    @Before
    public void getSourceForTestParser() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("businessLogic/bnpTableForTest.html").getFile());
        BufferedReader bufferedReader;

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine + System.lineSeparator());
            }
            finalSourceString = stringBuilder.toString().replace(",", ".");
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test method compare the data received after used main method from {@link ParserData},
     * with data from a web page as a temporary file bnpTableForTest.html.
     * The result should be positive.
     */
    @Test
    public void convertToObject() {
        //Arrange
        ParserData parserData = new ParserData(finalSourceString);
        ExchangeRates actual = parserData.convertToObject().get(0);

        //Act
        ExchangeRates expected = new ExchangeRates("Australian Dollar", "1 AUD", 2.9655);
        expected.setTableName("Table No. 077/A/NBP/2016");
        expected.setDate("2016-04-21");

        //Assert
        assertEquals(expected, actual);
    }
}