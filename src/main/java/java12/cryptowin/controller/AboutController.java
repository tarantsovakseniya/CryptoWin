package java12.cryptowin.controller;

import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/about")
    public ModelAndView getAboutPage() {
        ModelAndView result = new ModelAndView("about");
        result.addObject("user", userService.getCurrentUser());
        return result;
    }
}
