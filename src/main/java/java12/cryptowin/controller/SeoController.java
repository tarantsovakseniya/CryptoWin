package java12.cryptowin.controller;

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
        return generatorService.getXmlUrlSet(xmlUrlSet);
    }
}
