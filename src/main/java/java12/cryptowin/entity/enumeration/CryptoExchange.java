package java12.cryptowin.entity.enumeration;

public enum CryptoExchange {
    POLONIEX ("Poloniex", "https://poloniex.com/",
            "0$", "0$",
            "0$", "2000$",
            "6$", "0$",
            "0.15%"),
    BINANCE("Binance", "https://www.binance.com/ru",
            "23$", "0$",
            "23$", "22500$",
            "0.001%", "0$",
            "0.1%"),
    EXMO("Exmo", "https://exmo.com/ru",
            "23$", "0;",
            "23$", "500000$",
            "6$", "0$",
            "0.2%"),
    CRYPTO_BRIDGE("CryptoBridge", "https://crypto-bridge.org/",
            "0$", "0$",
            "6$", "0$",
            "6$", "0$",
            "10$"),
    GRAVIEX("Graviex", "https://graviex.net/",
            "0$", "0$",
            "5$", "0$",
            "5$", "0$",
            "0.2%"),
    BITFINEX("Bitfinex", "https://www.bitfinex.com/trading",
            "10$", "0$",
            "0.001 BTC", "0",
            "1.5%", "1.5% + 5 UAH",
            "0.25 %"); // check and correct to actual

    private String name;
    private String url;

    private String minDepositSum;
    private String maxDepositSum;

    private String minWithdrawSum;
    private String maxWithdrawSum;

    private String withdrawCommission;
    private String depositCommission;

    private String transactionCommission;

    CryptoExchange(String name, String url,
                   String minDepositSum, String maxDepositSum,
                   String minWithdrawSum, String maxWithdrawSum,
                   String withdrawCommission, String depositCommission,
                   String transactionCommission) {
        this.name = name;
        this.url = url;
        this.minDepositSum = minDepositSum;
        this.maxDepositSum = maxDepositSum;
        this.minWithdrawSum = minWithdrawSum;
        this.maxWithdrawSum = maxWithdrawSum;
        this.withdrawCommission = withdrawCommission;
        this.depositCommission = depositCommission;
        this.transactionCommission = transactionCommission;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getMinDepositSum() {
        return minDepositSum;
    }

    public String getMaxDepositSum() {
        return maxDepositSum;
    }

    public String getMinWithdrawSum() {
        return minWithdrawSum;
    }

    public String getMaxWithdrawSum() {
        return maxWithdrawSum;
    }

    public String getWithdrawCommission() {
        return withdrawCommission;
    }

    public String getDepositCommission() {
        return depositCommission;
    }

    public String getTransactionCommission() {
        return transactionCommission;
    }
}
