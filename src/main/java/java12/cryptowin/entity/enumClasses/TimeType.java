package java12.cryptowin.entity.enumClasses;

public enum TimeType {
    MONTH("за последнй месяц"),
    YEAR("за последний год"),
    WEEK("за последнюю неделю");

    private String name;

    TimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
