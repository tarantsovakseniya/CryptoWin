package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import java12.cryptowin.entity.enumClasses.TimeType;
import java12.cryptowin.service.jpa.BetterOfferService;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/")

public class CryptoWinController {

    @Autowired
    CryptoMonitorService cryptoMonitorService;


    @Autowired
    BetterOfferService betterOfferService;

    @GetMapping
    public ModelAndView getMain() {

//        List<CryptoMonitor> items = cryptoMonitorService.getAll();


        List<CryptoMonitor> items = Arrays.asList
                (new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.BINANCE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.GRAVIEX, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.CRYPTO_BRIDGE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.POLONIEX, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.EXMO, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.BINANCE, 10100, 10042),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.POLONIEX, 9300, 9240),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.EXMO, 10010, 10040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.BINANCE, 10002, 12040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.POLONIEX, 11000, 11040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.EXMO, 10050, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.BINANCE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.POLONIEX, 9000, 9040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.EXMO, 9000, 9040));

        ModelAndView result = new ModelAndView("main");
        result.addObject("items", items);
        result.addObject("cryptCoins", CryptCoinType.values());

        return result;
    }

    @GetMapping(value = "/better-offer")
    public ModelAndView getBetterOffer(@RequestParam("cryptCoin") CryptCoinType cryptCoin) {
        ModelAndView result = new ModelAndView("better-offer");

//        List<CryptoMonitor> items = cryptoMonitorService.getAll();
        List<CryptoMonitor> items = Arrays.asList
                (new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.BINANCE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.GRAVIEX, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.CRYPTO_BRIDGE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.POLONIEX, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.EXMO, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.BINANCE, 10100, 10042),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.POLONIEX, 9300, 9240),
                        new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.EXMO, 10010, 10040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.BINANCE, 10002, 12040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.POLONIEX, 11000, 11040),
                        new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.EXMO, 10050, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.BINANCE, 10000, 10040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.POLONIEX, 9000, 9040),
                        new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.EXMO, 9000, 9040));

        Map<List<CryptoMonitor>, Double> betterOffers = betterOfferService.getBetterOffer(items, cryptCoin);

        result.addObject("cryptCoinName", cryptCoin.getNameOfCoin());
        result.addObject("betterOffers", betterOffers);

        return result;
    }

}