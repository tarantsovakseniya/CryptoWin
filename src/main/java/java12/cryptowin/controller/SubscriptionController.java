package java12.cryptowin.controller;

import java12.cryptowin.entity.User;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.service.jpa.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ModelAndView getSubscription(@RequestParam(value = "error", required = false) String error){
        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("coins", CryptCoinType.values());
        result.addObject("error", error);
        return result;
    }

    @GetMapping("/addNew")
    public ModelAndView getFormForAdd(@RequestParam(value = "coin") CryptCoinType coinType,
                                      @RequestParam(value = "user", required = false) User user){
        ModelAndView result = new ModelAndView("subscription/regSubscription");
        return result;

    }

}
