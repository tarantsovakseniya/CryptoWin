package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import java12.cryptowin.service.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/main")
public class CryptoWinController {

    @Autowired
    CryptoMonitorService cryptoMonitorService;

    @GetMapping
    public ModelAndView getMain() {

//        List<CryptoMonitor> items = cryptoMonitorService.getAll();

        List<CryptoMonitor> items = Arrays.asList
                (new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.BINANCE, 10000, 10040),
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