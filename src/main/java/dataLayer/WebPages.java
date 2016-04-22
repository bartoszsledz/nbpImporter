package dataLayer;

public enum WebPages {
    TableA_EN("http://www.nbp.pl/homen.aspx?f=/kursy/ratesa.html"),
    TableB_EN("http://www.nbp.pl/homen.aspx?f=/kursy/RatesB.html"),
    TableA_PL("http://www.nbp.pl/home.aspx?f=/kursy/kursya.html"),
    TableB_PL("http://www.nbp.pl/home.aspx?f=/kursy/kursyb.html");

    private String page;

    WebPages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
