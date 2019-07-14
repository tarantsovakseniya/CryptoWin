package java12.cryptowin.entity.enumeration;

public enum CryptCoinType {
    BITCOIN("BTC"),
    ETHEREUM("ETH"),
    XRP("XRP"),
    LITECOIN("LTC"),
    BITCOIN_CASH("BCH"),
    STELLAR("XLM"),
    TRON("TRON"),
    DASH("DASH"),
    EOS("EOS"),
    IOTA_MIOTA( "IOTA");

    private String nameOfCoin;

    CryptCoinType(String nameOfCoin) {
        this.nameOfCoin = nameOfCoin;
    }

    public String getNameOfCoin() {
        return nameOfCoin;
    }
}
