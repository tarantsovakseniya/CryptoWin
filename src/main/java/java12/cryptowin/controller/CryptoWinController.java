package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.service.jpa.BetterOfferService;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class CryptoWinController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private BetterOfferService betterOfferService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getMain() {

        List<CryptoMonitor> items = cryptoMonitorService.getAll();

        ModelAndView result = new ModelAndView("main");
        result.addObject("items", items);
        result.addObject("cryptCoins", CryptCoinType.values());
        result.addObject("user", userService.getCurrentUser());

        return result;
    }

    @GetMapping(value = "/better-offer")
    public ModelAndView getBetterOffer(@RequestParam("cryptCoin") CryptCoinType cryptCoin) {
        ModelAndView result = new ModelAndView("better-offer");

        List<CryptoMonitor> items = cryptoMonitorService.getAll();

        Map<List<CryptoMonitor>, Double> betterOffers = betterOfferService.getBetterOffer(items, cryptCoin);

        result.addObject("cryptCoinName", cryptCoin.getNameOfCoin());
        result.addObject("betterOffers", betterOffers);
        result.addObject("user", userService.getCurrentUser());

        return result;
    }

}