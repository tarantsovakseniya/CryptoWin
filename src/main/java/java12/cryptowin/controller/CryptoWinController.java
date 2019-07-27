package java12.cryptowin.controller;

import java12.cryptowin.entity.*;
import java12.cryptowin.entity.enumeration.*;
import java12.cryptowin.service.jpa.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class CryptoWinController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private BetterOfferService betterOfferService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public ModelAndView getMain() {
        List<CryptoMonitor> items = cryptoMonitorService.getAllWithMaxLocalDateTime();

        ModelAndView result = new ModelAndView("main");
        result.addObject("items", items);
        result.addObject("cryptCoins", CryptCoinType.values());
        result.addObject("user", userService.getCurrentUser());

        return result;
    }

    @GetMapping(value = "/better-offer")
    public ModelAndView getBetterOffer(@RequestParam(value = "cryptCoin") CryptCoinType cryptCoin,
                                       @RequestParam(value = "wantToBuy", required = false) Double buy,
                                       @ModelAttribute("formCalc") FormCalcBetterOffer formCalc) {

        ModelAndView result = new ModelAndView("better-offer");

        List<CryptoMonitor> items = cryptoMonitorService.getAllWithMaxLocalDateTime();

        Map<List<CryptoMonitor>, Double> betterOffers = betterOfferService.getBetterOffer(items, cryptCoin);

        if (buy != null) {
            betterOfferService.getCalc(formCalc, items, cryptCoin, buy);
        }

        if (buy == null) {
            buy = 1000.0;
        }
        result.addObject("cryptCoin", cryptCoin);
        result.addObject("betterOffers", betterOffers);
        result.addObject("user", userService.getCurrentUser());
        result.addObject("items", CryptoExchange.values());

        result.addObject("wantToBuy", buy);
        result.addObject("formCalc", formCalc);

        return result;
    }
}