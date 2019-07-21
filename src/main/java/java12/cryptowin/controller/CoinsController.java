package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import java12.cryptowin.pojo.CryptoMonitorResult;
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

@Controller
public class CoinsController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;

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

        List<CryptoMonitor> cryptoToday = new ArrayList<>();
        List<CryptoMonitor> cryptoAll = cryptoMonitorService.getAll();
        cryptoAll.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getDate().toLocalDate().equals(LocalDate.now())) {
                cryptoToday.add(cryptoMonitor);
            }
        });

        final double[] maxPriceBuy = {0};
        final double[] minPriceSell = {0};
        final double[] minPriceBuy = {0};
        final double[] maxPriceSell = {0};
        cryptoToday.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getCoinType().equals(coinType) && cryptoMonitor.getExchange().equals(cryptoExchange)) {
                if (cryptoMonitor.getSellingRate() < minPriceSell[0]) {
                    minPriceSell[0] = cryptoMonitor.getSellingRate();
                }
                if (cryptoMonitor.getBuyingRate() > maxPriceBuy[0]) {
                    maxPriceBuy[0] = cryptoMonitor.getBuyingRate();
                }
                if (cryptoMonitor.getSellingRate() > maxPriceSell[0]) {
                    maxPriceSell[0] = cryptoMonitor.getSellingRate();
                }
                if (cryptoMonitor.getBuyingRate() < minPriceBuy[0]) {
                    maxPriceSell[0] = cryptoMonitor.getBuyingRate();
                }
            }
        });

        result.addObject("items", cryptoMonitorService);
        result.addObject("user", userService.getCurrentUser());
        result.addObject("minSell",minPriceSell[0]);
        result.addObject("maxBuy",maxPriceBuy[0]);
        result.addObject("maxSell",maxPriceSell[0]);
        result.addObject("minBuy",minPriceBuy[0]);


        return result;
    }

}
