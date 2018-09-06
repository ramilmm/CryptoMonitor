package CryptoMonitor.Model;

public class CurrencyTemplate {

    private Double price;
    private Double size;

    public CurrencyTemplate(Double price, Double size) {
        this.price = price;
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CurrencyTemplate{" +
                "price=" + price +
                ", size=" + size +
                '}';
    }
}
