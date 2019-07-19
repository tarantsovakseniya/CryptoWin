package java12.cryptowin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AboutController {

    @GetMapping(value = "/about")
    public ModelAndView getAboutPage() {
        ModelAndView modelAndView = new ModelAndView("about");
        return modelAndView;
    }
}
