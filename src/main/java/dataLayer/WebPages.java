package dataLayer;

public enum WebPages {
    Tableaen("http://www.nbp.pl/homen.aspx?f=/kursy/ratesa.html"),
    Tableben("http://www.nbp.pl/homen.aspx?f=/kursy/RatesB.html"),
    Tableapl("http://www.nbp.pl/home.aspx?f=/kursy/kursya.html"),
    Tablebpl("http://www.nbp.pl/home.aspx?f=/kursy/kursyb.html");

    private String page;

    WebPages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
