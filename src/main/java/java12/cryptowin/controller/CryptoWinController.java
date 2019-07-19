package java12.cryptowin.controller;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.pojo.CryptoMonitorResult;
import java12.cryptowin.service.jpa.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
        List<CryptoMonitorResult> items = cryptoMonitorService.getListForMailPage();

        ModelAndView result = new ModelAndView("main");
        result.addObject("items", items);
        result.addObject("cryptCoins", CryptCoinType.values());
        result.addObject("user", userService.getCurrentUser());

        return result;
    }

    @GetMapping(value = "/better-offer")
    public ModelAndView getBetterOffer(@RequestParam("cryptCoin") CryptCoinType cryptCoin,
                                       @RequestParam(value = "wantToBuy", required=false) String buy){
        ModelAndView result = new ModelAndView("better-offer");

        List<CryptoMonitorResult> items = cryptoMonitorService.getListForMailPage();

        Map<List<CryptoMonitorResult>, Double> betterOffers = null;

        if(buy==null) {
            betterOffers= betterOfferService.getBetterOffer(items, cryptCoin);
        }
        else {
            double buyDouble=Double.parseDouble(buy);
            betterOffers = betterOfferService.getBetterOfferNew(items, cryptCoin, buyDouble);
        }

        result.addObject("cryptCoin", cryptCoin);
        result.addObject("betterOffers", betterOffers);
        result.addObject("user", userService.getCurrentUser());
        result.addObject("wantToBuy", buy);

        return result;
    }
}