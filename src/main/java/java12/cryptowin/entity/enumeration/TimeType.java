package java12.cryptowin.entity.enumeration;

public enum TimeType {
    MONTH("за последнй месяц"),
    YEAR("за последний год"),
    WEEK("за последнюю неделю"),
    TODAY("за сегодняшний день");

    private String name;

    TimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
