package java12.cryptowin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/main")
public class CryptoWinController {

    @GetMapping
    public ModelAndView getMain() {

        ModelAndView result = new ModelAndView("main");
//        result.addObject("items", items);

        return result;
    }
}