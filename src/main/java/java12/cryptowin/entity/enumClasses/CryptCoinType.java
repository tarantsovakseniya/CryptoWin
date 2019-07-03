package java12.cryptowin.entity.enumClasses;

public enum CryptCoinType {
    BITCOIN("BTC"),
    ETHEREUM("ETH"),
    XRP("XRP"),
    LITECOIN("LTC"),
    BITCOIN_CASH("BTH"),
    STELLAR("XML"),
    TRON("TRON"),
    DASH("Dash"),
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
