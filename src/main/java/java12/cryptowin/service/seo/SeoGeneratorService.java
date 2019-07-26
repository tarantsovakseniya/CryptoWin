package java12.cryptowin.service.seo;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import java12.cryptowin.seo.XmlUrl;
import java12.cryptowin.seo.XmlUrlSet;
import org.springframework.stereotype.Service;

@Service
public class SeoGeneratorService {

    public String getRobotsTxt() {
        return "User-agent: *<br>" +
                "<br>" +
                "Allow: /*.js<br>" +
                "Allow: /*.css<br>" +
                "Allow: /*.jpg<br>" +
                "Allow: /*.gif<br>" +
                "Allow: /*.png<br>" +
                "Allow: /<br>" +
                "Allow: /charts/<br>" +
                "Allow: /stats/<br>" +
                "Allow: /better-offer/<br>" +
                "Disallow: /admin/<br>" +
                "Disallow: /login/<br>" +
                "Disallow: /login/<br>" +
                "Disallow: /register/<br>" +
                "Disallow: /user/<br>" +
                "Disallow: /data/<br>" +
                "Disallow: /about<br>" +
                "<br>" +
                "Host: http://crypto-benefit.com<br>" +
                "Sitemap: http://crypto-benefit.com/sitemap.xml";
    }

    public XmlUrlSet getXmlUrlSet() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();

        //main
        xmlUrlSet.addUrl(new XmlUrl(
                "http://crypto-benefit.com/",
                XmlUrl.Priority.HIGH));

        //better-offer and coin-exchange
        for (CryptCoinType cryptCoinType : CryptCoinType.values()) {
            xmlUrlSet.addUrl(new XmlUrl(
                    "http://crypto-benefit.com/better-offer/?cryptCoin=" + cryptCoinType,
                    XmlUrl.Priority.HIGH));
            for (CryptoExchange cryptoExchange : CryptoExchange.values()) {
                xmlUrlSet.addUrl(new XmlUrl(
                        "http://crypto-benefit.com/stats/?cryptCoin=" + cryptCoinType + "&exchange=" + cryptoExchange,
                        XmlUrl.Priority.HIGH));
            }
        }

        //charts
        xmlUrlSet.addUrl(new XmlUrl("http://crypto-benefit.com/charts", XmlUrl.Priority.MEDIUM));

        return xmlUrlSet;
    }
}
