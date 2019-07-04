package java12.cryptowin.entity.enumClasses;

public enum CryptoExchange {
    POLONIEX (""),
    BINANCE(""),
    EXMO(""),
    CRYPTO_BRIDGE(""),
    GRAVIEX("");

    private String name;
    private String url;

    private double minDepositSum;
    private double maxDepositSum;

    private String minWithdrawSum;
    private double maxWithdrawSum;

    private String withdrawCommission;
    private String depositCommission;

    private String transactionCommission;

//    CryptoExchange(String name, String url,
//                   double minDepositSum, double maxDepositSum,
//                   String minWithdrawSum, double maxWithdrawSum,
//                   String withdrawCommission, String depositCommission,
//                   String transactionCommission) {
//        this.name = name;
//        this.url = url;
//        this.minDepositSum = minDepositSum;
//        this.maxDepositSum = maxDepositSum;
//        this.minWithdrawSum = minWithdrawSum;
//        this.maxWithdrawSum = maxWithdrawSum;
//        this.withdrawCommission = withdrawCommission;
//        this.depositCommission = depositCommission;
//        this.transactionCommission = transactionCommission;
//    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public double getMinDepositSum() {
        return minDepositSum;
    }

    CryptoExchange(String url) {
        this.url = url;
    }

    public double getMaxDepositSum() {
        return maxDepositSum;
    }

    public String getMinWithdrawSum() {
        return minWithdrawSum;
    }

    public double getMaxWithdrawSum() {
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
