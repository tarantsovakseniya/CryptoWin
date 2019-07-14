package java12.cryptowin.entity.enumeration;

public enum CryptoExchange {
    POLONIEX ("Poloniex", "https://poloniex.com/",
            "0$", "0$",
            "0$", "2000$",
            "6$", "0$",
            "0.15%", "https://docs.google.com/document/d/e/2PACX-1vRkXt_oTW-WFEVr8riwKWTr9tc59DZ29JsD8xXKagxQuTl8Dtov7MUlzXs2LUNvgA/pub?embedded=true"),
    BINANCE("Binance", "https://www.binance.com/ru",
            "23$", "0$",
            "23$", "22500$",
            "0.001%", "0$",
            "0.1%", "https://docs.google.com/document/d/e/2PACX-1vScvCH709lSprsZknMqt9ZALm40x-u4WSbf2ysp0WPjM-xyVP_6VE3sDJRfPPlK6w/pub?embedded=true"),
    EXMO("Exmo", "https://exmo.com/ru",
            "23$", "0;",
            "23$", "500000$",
            "6$", "0$",
            "0.2%", "https://docs.google.com/document/d/e/2PACX-1vTHeWtcujyUu2ZEQ0sxC9YmTR1jo5aiWpyFrErC7Q8terChOoJWlwzQ9xvjOibAXg/pub?embedded=true"),
    CRYPTO_BRIDGE("CryptoBridge", "https://crypto-bridge.org/",
            "0$", "0$",
            "6$", "0$",
            "6$", "0$",
            "10$", "https://docs.google.com/document/d/e/2PACX-1vQFbjmgtdIy6AqIrfCxSsVUN3hdVmgCVmj7ak98pfVSd3GrpkFBTj6FHRq2VRWQ9qq8wajSVGCN_Wey/pub?embedded=true"),
    GRAVIEX("Graviex", "https://graviex.net/",
            "0$", "0$",
            "5$", "0$",
            "5$", "0$",
            "0.2%", "https://docs.google.com/document/d/e/2PACX-1vTo7hZJSCJJXxiDT5H8vxLDKFsymEyuQfJP_HjIocxsYZhst_fBlL6On9hLYDCf-Q/pub?embedded=true"),
    BITFINEX("Bitfinex", "https://www.bitfinex.com/trading",
            "0$", "0$",
            "7$", "0",
            "7$", "5$ for deposit < 1000$",
            "0.20%", "https://docs.google.com/document/d/e/2PACX-1vT7x38ahAmaoZ_bEEJiB5rgdAt7nyB69ou2sJHCUvicqFhV8gnYQrST_0V1-uA94OMOv6zC56xtMmtO/pub?embedded=true");

    private String name;
    private String url;

    private String minDepositSum;
    private String maxDepositSum;

    private String minWithdrawSum;
    private String maxWithdrawSum;

    private String withdrawCommission;
    private String depositCommission;

    private String transactionCommission;

    private String linkWithDetails;

    public String getLinkWithDetails() {
        return linkWithDetails;
    }

    CryptoExchange(String name, String url,
                   String minDepositSum, String maxDepositSum,
                   String minWithdrawSum, String maxWithdrawSum,
                   String withdrawCommission, String depositCommission,
                   String transactionCommission,
                   String linkWithDetails) {
        this.name = name;
        this.url = url;
        this.minDepositSum = minDepositSum;
        this.maxDepositSum = maxDepositSum;
        this.minWithdrawSum = minWithdrawSum;
        this.maxWithdrawSum = maxWithdrawSum;
        this.withdrawCommission = withdrawCommission;
        this.depositCommission = depositCommission;
        this.transactionCommission = transactionCommission;
        this.linkWithDetails=linkWithDetails;
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
