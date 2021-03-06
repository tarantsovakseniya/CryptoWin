package java12.cryptowin.controller;

import java12.cryptowin.entity.*;
import java12.cryptowin.entity.enumeration.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ModelAndView getProfile() {
        ModelAndView view = new ModelAndView("security/user-profile");
        view.addObject("user", userService.getCurrentUser());
        List<Subscription> subscriptions = subscriptionService.getByUser(userService.getCurrentUser());
        view.addObject("subscription", subscriptionService.getByUser(userService.getCurrentUser()));
        return view;
    }

    @GetMapping("/delete")
    public ModelAndView deleteSubscription(@RequestParam("id") long id) {
        ModelAndView view = new ModelAndView("security/user-profile");

        if (subscriptionService.getById(id).getUser().equals(userService.getCurrentUser())) {
            subscriptionService.deleteById(id);
            view.addObject("user", userService.getCurrentUser());
            view.addObject("error", "success");
            view.addObject("subscription", subscriptionService.getByUser(userService.getCurrentUser()));
        }
        return view;
    }

    @GetMapping("/change")
    public ModelAndView getSubscription(@RequestParam("id") long id) {
        ModelAndView view = new ModelAndView("security/user-profile");
        Subscription subscription = subscriptionService.getById(id);
        view.addObject("ref", "sub");
        view.addObject("s", subscription);
        view.addObject("coins", CryptCoinType.values());
        view.addObject("user", userService.getCurrentUser());
        view.addObject("subscription", subscriptionService.getByUser(userService.getCurrentUser()));

        return view;
    }

    @GetMapping("/changeIcon")
    public ModelAndView getNewIcon(@RequestParam(name = "icon") IconType iconType){
        ModelAndView view = new ModelAndView("security/user-profile");
        User user = userService.getCurrentUser();
        user.setIcon(iconType);
        userService.update(user);
        view.addObject("subscription", subscriptionService.getByUser(userService.getCurrentUser()));
        view.addObject("user", user);
        view.addObject("error", "add");
        return view;
    }

    @GetMapping("/changeData")
    public ModelAndView getFortToChange(@RequestParam(name = "ref", required = false) String ref) {
        ModelAndView view = new ModelAndView("security/user-profile");
        if (ref == null) {
            view.addObject("ref", "info");
        } else {
            view.addObject("ref", ref);
            view.addObject("icons", IconType.values());
        }
        view.addObject("user", userService.getCurrentUser());
        return view;
    }

    @PostMapping("/changeData")
    public ModelAndView postNewInfo(@RequestParam("name") String name,
                                    @RequestParam("lastname") String last,
                                    @RequestParam("password") String password) {
        User user = userService.getCurrentUser();
        user.setPassword(password);
        user.setLastName(last);
        user.setName(name);
        userService.update(user);

        ModelAndView view = new ModelAndView("security/user-profile");
        view.addObject("user", user);
        view.addObject("subscription", subscriptionService.getByUser(user));
        view.addObject("error", "add");
        return view;
    }

    @PostMapping("/change")
    public ModelAndView changeSubscription(@RequestParam("coinType") CryptCoinType coinType,
                                           @RequestParam("minResult") Double min,
                                           @RequestParam("maxResult") Double max,
                                           @RequestParam("profit") Double profit,
                                           @RequestParam("id") Long id) {
        ModelAndView view = new ModelAndView("security/user-profile");
        if (coinType == null || max == null || min == null || profit == null) {
            Subscription subscription = subscriptionService.getById(id);
            view.addObject("ref", "sub");
            view.addObject("s", subscription);
            view.addObject("coins", CryptCoinType.values());
            view.addObject("error", "incorrect");
        } else {
            if (subscriptionService.getById(id).getUser().equals(userService.getCurrentUser())) {
                subscriptionService.deleteById(id);
                Subscription newSome = new Subscription(id, userService.getCurrentUser(), coinType, min, max, profit);
                subscriptionService.save(newSome);
                view.addObject("subscription", subscriptionService.getByUser(userService.getCurrentUser()));
                view.addObject("error", "add");
            }
        }
        view.addObject("user", userService.getCurrentUser());

        return view;
    }

    @GetMapping("/subscription")
    public ModelAndView getSubscription(@RequestParam(value = "error", required = false) String error) {
        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("coins", CryptCoinType.values());
        result.addObject("error", error);
        result.addObject("user", userService.getCurrentUser());
        return result;
    }

    @PostMapping("/subscription")
    public ModelAndView getAdd(@RequestParam("minResult") String minResult,
                               @RequestParam("maxResult") String maxResult,
                               @RequestParam("coinType") String coinType,
                               @RequestParam("profit") String profit) {

        ModelAndView result = new ModelAndView("subscription/subscription");
        result.addObject("user", userService.getCurrentUser());
        result.addObject("coin", coinType);
        result.addObject("coins", CryptCoinType.values());

        String error = null;
        User user = userService.getCurrentUser();
        if (minResult.equals("") || maxResult.equals("") || coinType.equals("") || profit.equals("")) {
            error = "incorrect";
        } else {

            List<Subscription> subscriptions = subscriptionService.getByUser(user);
            Subscription subscription;
            boolean mark = true;

            double minDouble = Double.parseDouble(minResult.replace(",","."));
            double maxDouble = Double.parseDouble(maxResult.replace(",","."));
            double profitDouble = Double.parseDouble(profit.replace(",","."));

            if (subscriptions != null) {
                for (Subscription s : subscriptions) {
                    if (s.getMinResult() == minDouble &&
                            s.getMaxResult() ==  maxDouble &&
                            s.getCryptCoinType() == CryptCoinType.valueOf(coinType) &&
                            s.getProfit() == profitDouble) {
                        mark = false;
                    }
                }
            }

            if (mark) {

                subscription = new Subscription(user, CryptCoinType.valueOf(coinType), minDouble,
                        maxDouble,profitDouble);
                subscriptionService.save(subscription);
                error = "success";
            } else {
                error = "exist";
            }
        }
        result.addObject("error", error);
        return result;
    }


    @GetMapping("/subscription/addNew")
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