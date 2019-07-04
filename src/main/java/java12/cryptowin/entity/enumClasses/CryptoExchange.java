package java12.cryptowin.entity.enumClasses;

public enum CryptoExchange {
    Binance (""), Poloniex (""), EXMO ("");

   private String url;

    CryptoExchange(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
