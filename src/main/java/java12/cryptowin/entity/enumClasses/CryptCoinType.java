package java12.cryptowin.entity.enumClasses;

public enum CryptCoinType {
    BITCOIN("Bitcoin"),
    ETHEREUM("Ethereum"),
    XRP("XRP"),
    LITECOIN("Litecoin"),
    BITCOIN_CASH("Bitcoin Cash"),
    STELLAR("Stellar"),
    TRON("TRON"),
    DASH("Dash"),
    EOS("EOS"),
    IOTA_MIOTA( "IOTA (MIOTA)");

    private String nameOfCoin;

    CryptCoinType(String nameOfCoin) {
        this.nameOfCoin = nameOfCoin;
    }

    public String getNameOfCoin() {
        return nameOfCoin;
    }
}
