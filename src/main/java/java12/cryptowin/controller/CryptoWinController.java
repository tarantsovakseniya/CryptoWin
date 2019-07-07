package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import java12.cryptowin.entity.enumClasses.TimeType;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java12.cryptowin.service.jpa.BetterOfferService;
import java.util.*;

@Controller
public class CryptoWinController {

    @Autowired
    CryptoMonitorService cryptoMonitorService;

    @Autowired
    BetterOfferService betterOfferService;

    @RequestMapping(value = "/charts", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getCharts(@RequestParam( name = "coinType", required = false) String coinType,
                                  @RequestParam(name = "exchangeType", required = false) String  exchangeType,
                                  @RequestParam(name = "timeType", required = false) String timeType){
        ModelAndView result = new  ModelAndView("chart");
        if(coinType==null){
            coinType = CryptCoinType.BITCOIN.getNameOfCoin();
        }
        if(exchangeType == null){
            exchangeType = CryptoExchange.BINANCE.getName();
        }
        if(timeType == null){
            timeType = TimeType.WEEK.getName();
        }

        List<CryptoMonitor> crypto = fillListToUserRequest(coinType, timeType, exchangeType, cryptoMonitorService.getAll());
        //cryptoMonitorService.getByCryptoAndExName(coinType, exchangeType);
        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());
        result.addObject("timeType", timeType);
        result.addObject("exchangeType", exchangeType);
        result.addObject("coinType", coinType);

        return result;

    }

    private List<CryptoMonitor> fillListToUserRequest(String coinType, String timeType, String exchangeType, List<CryptoMonitor> all) {
        all.forEach(cryptoMonitor -> {
            if(!cryptoMonitor.getCoinType().getNameOfCoin().equals(coinType)){
                all.remove(cryptoMonitor);
            }

            if(!cryptoMonitor.getExchange().getName().equals(exchangeType)){
                all.remove(cryptoMonitor);
            }

            LocalDate localDate = LocalDate.now();
            if(timeType.equals(TimeType.YEAR.getName())){
                localDate = localDate.minusYears(1);
            }
            if(timeType.equals(TimeType.MONTH.getName())){
                localDate = localDate.minusMonths(1);
            }
            if(timeType.equals(TimeType.WEEK.getName())){
                localDate.minusWeeks(1);
            }

            if(cryptoMonitor.getDate().isBefore(localDate)){
                all.remove(cryptoMonitor);
            }
        });
        return all;
    }

    @GetMapping(value = {"/main", "/"})
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

    @GetMapping(value = {"/main/better-offer", "/better-offer"})
    public ModelAndView postBetterOffer(@RequestParam("cryptCoin") CryptCoinType cryptCoin) {
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