package businessLogic;

import dataLayer.ExchangeRates;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * It's a test class for class {@link ParserData}.
 */
public class ParserDataTest {

    private static final String table = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"15\" width=\"640\"> <tr> <td style=\"vertical-align:top\"> <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"> <tr> <td style=\"text-align:left; vertical-align:top\"> <p class=\"head1 print_hidden\">Kursy walut</p> <p class=\"head2\">Kursy średnie walut obcych &ndash; tabela A</p> </td> <td style=\"text-align:right; vertical-align:top\" class=\"print_hidden\">  <a href=\"javascript:{printPage();}\" class=\"static\"> <img src=\"/img/print.gif\" alt=\"drukuj\" style=\"width:21px; height:18px; margin:0px 10px; border:0px solid black; vertical-align:middle;\" />Drukuj</a>  </td> </tr> </table> <p align=\"justify\">Bieżące kursy średnie walut obcych w&nbsp;złotych określonych w&nbsp;§ 2 pkt 1 i&nbsp;2 uchwały Nr&nbsp;51/2002 Zarządu Narodowego Banku Polskiego z&nbsp;dnia 23 września 2002 r. w&nbsp;sprawie sposobu wyliczania i&nbsp;ogłaszania bieżących kursów walut obcych (Dz. Urz. NBP z&nbsp;2015 r. poz.&nbsp;11):</p> <center> <p class=\"nag\">Tabela nr 034/A/NBP/2017 z dnia 2017-02-17</p> <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"361\" class=\"pad5\"> <colgroup> <col width=\"180\" /> <col width=\"80\" /> <col width=\"80\" /> </colgroup> <tr> <th class=\"lgby2\"><b>Nazwa waluty</b></th> <th class=\"lgby2\"><b>Kod waluty</b></th> <th class=\"lgby2\"><b>Kurs średni</b></th> </tr> <tr> <td class=\"bgt1 left\">bat (Tajlandia)</td> <td class=\"bgt1 right\">1 THB</td> <td class=\"bgt1 right\">0.1163</td> </tr> </table>";
    private static final String CORRECT_URL = "http://www.nbp.pl/home.aspx?f=/kursy/kursya.html";
    private DownloadData downloadData;
    private ParserData parserData;

    @Before
    public void setUp() {
        downloadData = mock(DownloadData.class);
        when(downloadData.getDataFromSource(CORRECT_URL)).thenReturn(table);
    }

    @Test
    public void should_get_correctly_exchange_rate_and_parse_to_object() {
        // given
        final ExchangeRates expected = new ExchangeRates("Tabela nr 034/A/NBP/2017", "2017-02-17", "bat (Tajlandia)", "1 THB", 0.1163, "A");
        parserData = new ParserData();

        // when
        final ExchangeRates exchangeRate = parserData.convertToObject(downloadData.getDataFromSource(CORRECT_URL)).get(0);

        // then
        assertThat(exchangeRate).isEqualTo(expected);
    }

    @Test
    public void should_not_get_correctly_exchange_rate_and_parse_to_object_when_get_null_html_document() {
        // given
        parserData = new ParserData();

        // when
        final Throwable thrown = catchThrowable(() -> parserData.convertToObject(null).get(0));

        // then
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("String input must not be null");
    }
}