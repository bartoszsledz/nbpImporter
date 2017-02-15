package dataLayer;

import javax.persistence.*;

@Entity
@Table(name = "exchangeRates")
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "TableName")
    private String tableName;

    @Column(name = "Date")
    private String date;

    @Column(name = "Currency")
    private String currency;

    @Column(name = "Code")
    private String code;

    @Column(name = "MidRate")
    private double midRate;

    @Column(name = "TableType")
    private String tableType;

    public ExchangeRates() {
    }

    /**
     * This class stores information about exchange rates from page.
     *
     * @param currency - currency name.
     * @param code     - currency code.
     * @param midRate  - average exchange rate.
     */
    public ExchangeRates(final String currency, final String code, final double midRate) {
        setCurrency(currency);
        setCode(code);
        setMidRate(midRate);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public double getMidRate() {
        return midRate;
    }

    public void setMidRate(final double midRate) {
        this.midRate = midRate;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(final String tableType) {
        this.tableType = tableType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ExchangeRates that = (ExchangeRates) o;

        if (Double.compare(that.midRate, midRate) != 0) return false;
        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        int result;
        final long temp;
        result = code != null ? code.hashCode() : 0;
        temp = Double.doubleToLongBits(midRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("Currency: %-45s Code: %-10s MidRate: %-1s Date: %-10s TableName: %-45s", currency, code, midRate, date, tableName) + System.lineSeparator();
    }
}