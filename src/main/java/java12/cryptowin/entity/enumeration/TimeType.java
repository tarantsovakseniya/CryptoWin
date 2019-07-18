package java12.cryptowin.entity.enumeration;

public enum TimeType {
    WEEK("за последнюю неделю"),
    TWO_WEEK("за две недели"),
    TODAY("за сегодняшний день");

    private String name;

    TimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
