package CryptoMonitor.Model;

import java.util.ArrayList;

public class ExchangeData {

    private ArrayList<CurrencyTemplate> ask;
    private ArrayList<CurrencyTemplate> bid;

    public ArrayList<CurrencyTemplate> getAsk() {
        return ask;
    }

    public void setAsk(ArrayList<CurrencyTemplate> ask) {
        this.ask = ask;
    }

    public ArrayList<CurrencyTemplate> getBid() {
        return bid;
    }

    public void setBid(ArrayList<CurrencyTemplate> bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "ExchangeData{" +
                "ask=" + ask +
                ", bid=" + bid +
                '}';
    }
}
