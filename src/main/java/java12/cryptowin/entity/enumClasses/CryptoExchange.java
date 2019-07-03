package java12.cryptowin.entity.enumClasses;

public enum CryptoExchange {
    ;

    private double minDepositSum;
    private double maxDepositSum;

    private String name;
    private String url;


    CryptoExchange(double minDepositSum, double maxDepositSum, String name, String url) {
        this.minDepositSum = minDepositSum;
        this.maxDepositSum = maxDepositSum;
        this.name = name;
        this.url = url;
    }

    public double getMinDepositSum() {
        return minDepositSum;
    }

    public double getMaxDepositSum() {
        return maxDepositSum;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
