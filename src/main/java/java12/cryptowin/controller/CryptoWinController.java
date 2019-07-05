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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = {"/main", "/"})
public class CryptoWinController {

    @Autowired
    CryptoMonitorService cryptoMonitorService;

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
            timeType = TimeType.YEAR.getName();
        }

        List<CryptoMonitor> crypto = cryptoMonitorService.getAll();
       //crypto = filterByCryptoNameDateAndExchange(crypto, coinType, timeType, exchangeType);


        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());

        result.addObject("coinType", coinType);
        result.addObject("timeType", timeType);
        result.addObject("exchangeType", exchangeType);

        return result;

    }

    // method that filters cryptoMonitors for request
    private List<CryptoMonitor> filterByCryptoNameDateAndExchange(List<CryptoMonitor> crypto,
                                                                  String coinType, String timeType, String exchangeType) {

        LocalDate localDate = LocalDate.parse(timeType);
        crypto.forEach(crypt ->{
            if(!crypt.getCoinType().getNameOfCoin().equals(coinType)){
                crypto.remove(crypt);
            }

            if(!crypt.getExchange().getName().equals(exchangeType)){
                crypto.remove(crypt);
            }
            if(crypt.getDate().isBefore(localDate)){
                crypto.remove(crypt);
            }

        });

        return crypto;
    }

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
        result.addObject("cryptCoins",CryptCoinType.values());

        return result;
    }
}