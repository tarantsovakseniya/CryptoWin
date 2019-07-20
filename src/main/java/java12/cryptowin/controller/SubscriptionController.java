package java12.cryptowin.controller;

import java12.cryptowin.entity.Subscription;
import java12.cryptowin.entity.User;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.service.jpa.SubscriptionService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping(value = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getSubscription(@RequestParam(value = "error", required = false) String error) {
        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("coins", CryptCoinType.values());
        result.addObject("error", error);
        result.addObject("user", userService.getCurrentUser());
        return result;
    }

    @PostMapping
    public ModelAndView getAdd(@RequestParam("minResult") Double minResult,
                               @RequestParam("maxResult") Double maxResult,
                               @RequestParam("coinType") CryptCoinType coinType,
                               @RequestParam("profit") Double profit) {

        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("user", userService.getCurrentUser());
        result.addObject("coin", coinType);
        result.addObject("coins", CryptCoinType.values());

        String error = null;
        User user = userService.getCurrentUser();
        if (minResult == null || maxResult == null || coinType == null) {
            error = "incorrect";
        } else {

            List<Subscription> subscriptions = subscriptionService.getByUserId(user.getId());
            Subscription subscription;
            boolean mark = true;

            for (Subscription s : subscriptions) {
                if (s.getMinResult() == minResult &&
                        s.getMaxResult() == maxResult &&
                        s.getCryptCoinType() == coinType &&
                        s.getProfit() == profit) {
                    mark = false;
                }
            }

            if (mark) {

                subscription = new Subscription(user, coinType, minResult, maxResult, profit);
                subscriptionService.save(subscription);
                error = "success";
            }
            else {
                error = "exist";
            }
        }
        result.addObject("error", error);
        return result;
    }


    @GetMapping("/addNew")
    public ModelAndView getFormForAdd(@RequestParam(value = "coin") CryptCoinType coinType,
                                      @RequestParam("error") String error) {
        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("user", userService.getCurrentUser());
        result.addObject("coin", coinType);
        result.addObject("error", error);
        result.addObject("coins", CryptCoinType.values());
        return result;

    }

}
