package java12.cryptowin.controller;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import java12.cryptowin.seo.XmlUrl;
import java12.cryptowin.seo.XmlUrlSet;
import java12.cryptowin.service.seo.SeoGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SeoController {

    @Autowired
    private SeoGeneratorService generatorService;

    @RequestMapping(value = "/robots.txt")
    @ResponseBody
    public String getRobotsTxt() {
        return generatorService.getRobotsTxt();
    }

    @RequestMapping(value = "/sitemap.xml", produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public XmlUrlSet main() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/better-offer", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/charts", XmlUrl.Priority.MEDIUM);

        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("http://crypto-benefit.com" + link, priority));
    }

}
