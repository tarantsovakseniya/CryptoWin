package java12.cryptowin.controller;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import java12.cryptowin.entity.enumClasses.TimeType;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/charts")
public class ChartsController {

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;

    private List<CryptoMonitor> fillListToUserRequest(String coinType, String timeType, String exchangeType) {
        List<CryptoMonitor> all = cryptoMonitorService.getAll();

        System.out.println(all.size());
        CryptoMonitor cryptoMonitor;
        LocalDateTime localDate = LocalDateTime.now();
        if (timeType.equals(TimeType.YEAR.getName())) {
            localDate = localDate.minusYears(1);
        }
        if (timeType.equals(TimeType.MONTH.getName())) {
            localDate = localDate.minusMonths(1);
        }
        if (timeType.equals(TimeType.WEEK.getName())) {
            localDate = localDate.minusWeeks(1);
        }
        if (timeType.equals(TimeType.TODAY.getName())) {
            localDate = localDate.minusHours(24);
        }
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getDate()==null){
                cryptoMonitorService.deleteById(all.get(i).getId());
                all.remove(all.get(i));
            }
        }

        for (int i = 0; i < all.size(); i++) {
            cryptoMonitor = all.get(i);

            if (!cryptoMonitor.getCoinType().getNameOfCoin().equals(coinType) ||
                    !cryptoMonitor.getExchange().getName().equals(exchangeType) ||
                    cryptoMonitor.getDate().isBefore(localDate))
                all.remove(cryptoMonitor);

        }

        all.sort(new Comparator<CryptoMonitor>() {
            @Override
            public int compare(CryptoMonitor o1, CryptoMonitor o2) {
                if(o1.getDate().isBefore(o2.getDate())){
                    return -1;
                }
                else{
                    if(o2.getDate().isAfter(o2.getDate())){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        });

        return all;
    }

    @PostMapping
    public ModelAndView postData(@RequestParam(name = "coinType") String coinType,
                                  @RequestParam(name = "exchangeType") String exchangeType,
                                  @RequestParam(name = "timeType") String timeType,
                                  @RequestParam(name = "state") String state) {
        ModelAndView result = new ModelAndView("chart");
        String error = null;
        if (coinType.equals("") || exchangeType.equals("") || timeType.equals("") || state.equals("")){
            error = "error";
        }

        List<CryptoMonitor> crypto = fillListToUserRequest(coinType, timeType, exchangeType);
        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());
        result.addObject("timeType", timeType);
        result.addObject("exchangeType", exchangeType);
        result.addObject("coinType", coinType);
        result.addObject("state", state);
        result.addObject("error",error);
        result.addObject("user", userService.getCurrentUser());

        return result;

    }

    @GetMapping
    public ModelAndView getCharts(@RequestParam(name = "coinType", required = false) String coinType,
                                  @RequestParam(name = "exchangeType", required = false) String exchangeType,
                                  @RequestParam(name = "timeType", required = false) String timeType,
                                  @RequestParam(name = "state", required = false) String state,
                                  @RequestParam(name = "error", required = false) String error){
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
        if(state == null){
            state = "sell";
        }

        List<CryptoMonitor> crypto = fillListToUserRequest(coinType, timeType, exchangeType);
        result.addObject("crypto", crypto);
        result.addObject("coins", CryptCoinType.values());
        result.addObject("exchanges", CryptoExchange.values());
        result.addObject("times", TimeType.values());
        result.addObject("timeType", timeType);
        result.addObject("exchangeType", exchangeType);
        result.addObject("coinType", coinType);
        result.addObject("state", state);
        result.addObject("user", userService.getCurrentUser());
        result.addObject("error", error);

        return result;

    }
}
