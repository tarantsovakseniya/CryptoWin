package java12.cryptowin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;

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
                "Host: http://crypto-benefit.com<br>"/* +
                "Sitemap: http://crypto-benefit.com/sitemap.xml"*/;
    }

}
