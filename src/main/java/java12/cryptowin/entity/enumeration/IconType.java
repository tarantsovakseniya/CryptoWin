package java12.cryptowin.entity.enumeration;

public enum IconType {
    FIRST_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/elliot.jpg"),
    SECOND_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/elyse.png"),
    THIRD_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/helen.jpg"),
    FOURTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/jenny.jpg"),
    FIFTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/matthew.png"),
    SIXTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/molly.png"),
    SEVENTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/steve.jpg"),
    NINTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/stevie.jpg"),
    TENTH_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/veronika.jpg");

    private String url;

    public String getUrl() {
        return url;
    }

    IconType(String url) {
        this.url = url;
    }
}
