package businessLogic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class DownloadDataTest {

    private static final String CORRECT_URL = "http://www.nbp.pl/home.aspx?f=/kursy/kursya.html";
    private static final String INCORRECT_URL = "google.pl";
    private DownloadData downloadData;

    @Test
    public void should_get_html_page_in_string() {
        // given
        downloadData = new DownloadData();

        // when
        final String htmlInString = downloadData.getDataFromSource(CORRECT_URL);

        // then
        assertThat(htmlInString).contains("<!doctype html>");
    }

    @Test
    public void should_not_get_html_page_source_in_string_when_set_bad_page_url() {
        // given
        downloadData = new DownloadData();

        // when
        final Throwable thrown = catchThrowable(() -> downloadData.getDataFromSource(INCORRECT_URL));

        // then
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("java.net.MalformedURLException: no protocol: google.pl");
    }
}