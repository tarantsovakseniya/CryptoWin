package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import java12.cryptowin.pojo.CryptoMonitorResult;
import java12.cryptowin.service.jpa.CryptCoinExchangeService;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CoinsController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;

    @Autowired
    private CryptCoinExchangeService coinExchangeService;

    @GetMapping(value = "/stats")
    public ModelAndView getCoinInfo(@RequestParam("cryptCoin") CryptCoinType coinType,
                                    @RequestParam("exchange") CryptoExchange cryptoExchange) {
        ModelAndView result = new ModelAndView("seo-pages/coin-exchange");

        List<CryptoMonitorResult> items = cryptoMonitorService.getAllWithMaxLocalDateTime();
        items.forEach(cryptoMonitorResult -> {
            if (cryptoMonitorResult.getCoinType().equals(coinType) & cryptoMonitorResult.getExchange().equals(cryptoExchange)) {
                result.addObject("cryptMonitor", cryptoMonitorResult);
            }
        });

        Map<String,Double> minMaxResult= coinExchangeService.getMaxMinResultToday(cryptoMonitorService.getAll(),coinType,cryptoExchange);

        result.addObject("items", cryptoMonitorService);
        result.addObject("user", userService.getCurrentUser());
        result.addObject("minSell",minMaxResult.get("minSell"));
        result.addObject("maxBuy",minMaxResult.get("maxBuy"));
        result.addObject("maxSell",minMaxResult.get("maxSell"));
        result.addObject("minBuy",minMaxResult.get("minBuy"));

        return result;
    }

}
