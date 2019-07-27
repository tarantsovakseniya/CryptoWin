package java12.cryptowin.seo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "url")
public class XmlUrl {
    public enum Priority {
        HIGH("1.0"), MEDIUM("0.5");

        private String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @XmlElement
    private String link;

    @XmlElement
    private String lastmod = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);

    @XmlElement
    private String changefreq = "daily";

    @XmlElement
    private String priority;

    public XmlUrl() {

    }

    public XmlUrl(String link, Priority priority) {
        this.link = link;
        this.priority = priority.getValue();
    }

    public String getLink() {
        return link;
    }

    public String getPriority() {
        return priority;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public String getLastmod() {
        return lastmod;
    }
}
