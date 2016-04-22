package dataLayer;

public class ExchangeRates {

    private String currency;
    private String code;
    private double midRate;

    public ExchangeRates() {
    }

    public ExchangeRates(String currency, String code, double midRate) {
        setCurrency(currency);
        setCode(code);
        setMidRate(midRate);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMidRate() {
        return midRate;
    }

    public void setMidRate(double midRate) {
        this.midRate = midRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRates that = (ExchangeRates) o;

        if (Double.compare(that.midRate, midRate) != 0) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        temp = Double.doubleToLongBits(midRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("Currency: %-45s Code: %-10s MidRate: %-1s", currency, code, midRate) + System.lineSeparator();
    }
}