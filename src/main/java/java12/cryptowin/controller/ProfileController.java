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

@Controller
@RequestMapping("/userProfile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ModelAndView getProfile() {
        ModelAndView view = new ModelAndView("security/user-profile");
        view.addObject("user", userService.getCurrentUser());
        view.addObject("subscription", subscriptionService.getByUserId(userService.getCurrentUser().getId()));
        return view;

    }

    @GetMapping("/delete")
    public ModelAndView deleteSubscription(@RequestParam("id") long id) {
        ModelAndView view = new ModelAndView("security/user-profile");

        subscriptionService.deleteById(id);
        view.addObject("user", userService.getCurrentUser());
        view.addObject("error", "success");
        view.addObject("subscription", subscriptionService.getByUserId(userService.getCurrentUser().getId()));

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
        view.addObject("subscription", subscriptionService.getByUserId(userService.getCurrentUser().getId()));

        return view;
    }

    @GetMapping("/changeData")
    public ModelAndView getFortToChange() {
        ModelAndView view = new ModelAndView("security/user-profile");
        view.addObject("ref", "info");
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
        view.addObject("subscription", subscriptionService.getByUserId(user.getId()));
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
            subscriptionService.deleteById(id);
            Subscription newSome = new Subscription(id, userService.getCurrentUser(), coinType, min, max, profit);
            subscriptionService.save(newSome);
            view.addObject("subscription", subscriptionService.getByUserId(userService.getCurrentUser().getId()));
            view.addObject("error", "add");


        }
        view.addObject("user", userService.getCurrentUser());


        return view;
    }

}
