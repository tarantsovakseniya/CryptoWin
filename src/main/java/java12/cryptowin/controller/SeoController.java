package java12.cryptowin.controller;

import java12.cryptowin.seo.XmlUrl;
import java12.cryptowin.seo.XmlUrlSet;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeoController {

    @RequestMapping(value = "/robots.txt")
    @ResponseBody
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
                "Host: http://crypto-benefit.com<br>" /*+
                "Sitemap: http://crypto-benefit.com/sitemap.xml"*/;
    }

    @RequestMapping(value="/sitemap.xml", produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public XmlUrlSet main() {

        XmlUrlSet xmlUrlSet = new XmlUrlSet();

        create(xmlUrlSet, "/", XmlUrl.Priority.HIGH);

        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("http://crypto-benefit.com" + link, priority));
    }
}
