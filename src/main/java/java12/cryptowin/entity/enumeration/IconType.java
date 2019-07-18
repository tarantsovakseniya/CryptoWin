package java12.cryptowin.entity.enumeration;

public enum IconType {
    First_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/elliot.jpg"),
    Second_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/elyse.png"),
    Third_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/helen.jpg"),
    Fourth_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/jenny.jpg"),
    Fifth_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/matthew.png"),
    Sixth_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/molly.png"),
    Seventh_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/steve.jpg"),
    Ninth_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/stevie.jpg"),
    Tenth_ICON("http://bootstraptema.ru/snippets/icons/2017/smtb/veronika.jpg");

    private String url;

    public String getUrl() {
        return url;
    }

    IconType(String url) {
        this.url = url;
    }
}
