package businessLogic;

import dataLayer.ExchangeRates;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ParserDataTest {

    private StringBuilder stringBuilder = new StringBuilder();

    @Before
    public void getSourceForTestParser() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("bnpTableForTest.html").getFile());
        BufferedReader bufferedReader;

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine + System.lineSeparator());
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         stringBuilder.toString().replace(",", ".");
    }

    @Test
    public void convertToObject() {
        //Arrange
        ParserData parserData = new ParserData(stringBuilder.toString());
        ExchangeRates actual = parserData.convertToObject().get(0);

        //Act
        ExchangeRates expected = new ExchangeRates("Australian Dollar", "1 AUD", 2.9655);

        //Assert
        assertEquals(expected, actual);
    }
}