package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/charts")
public class ChartsController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ModelAndView postData(@RequestParam(name = "coinType") String coinType,
                                 @RequestParam(name = "exchangeType") String exchangeType,
                                 @RequestParam(name = "timeType") String timeType,
                                 @RequestParam(name = "state") String state) {
        ModelAndView result = new ModelAndView("chart");
        String error = null;
        if (coinType.equals("") || exchangeType.equals("") || timeType.equals("") || state.equals("")) {
            error = "error";
        }

        List<CryptoMonitor> crypto = cryptoMonitorService.fillListToUserRequest(coinType, timeType, exchangeType);

        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());
        result.addObject("time", timeType);
        result.addObject("exchange", exchangeType);
        result.addObject("coin", coinType);
        result.addObject("state", state);
        result.addObject("error", error);
        result.addObject("user", userService.getCurrentUser());

        return result;

    }


    @GetMapping
    public ModelAndView getCharts(@RequestParam(name = "coin", required = false) String coinType,
                                  @RequestParam(name = "exchange", required = false) String exchangeType,
                                  @RequestParam(name = "time", required = false) String timeType,
                                  @RequestParam(name = "state", required = false) String state,
                                  @RequestParam(name = "error", required = false) String error) {
        ModelAndView result = new ModelAndView("chart");
        if (coinType == null) {
            coinType = CryptCoinType.BITCOIN.getNameOfCoin();
        }
        if (exchangeType == null) {
            exchangeType = CryptoExchange.BINANCE.getName();
        }
        if (timeType == null) {
            timeType = TimeType.WEEK.getName();
        }
        if (state == null) {
            state = "sell";
        }


        List<CryptoMonitor> crypto = cryptoMonitorService.fillListToUserRequest(coinType, timeType, exchangeType);
        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());
        result.addObject("time", timeType);
        result.addObject("exchange", exchangeType);
        result.addObject("coin", coinType);
        result.addObject("state", state);
        result.addObject("user", userService.getCurrentUser());

        return result;

    }
}
