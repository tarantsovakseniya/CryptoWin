package java12.cryptowin.controller;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/main")
public class CryptoWinController {
    @GetMapping
    public ModelAndView getMain() {

        Map<String, List<BindingTable>> items = new HashMap<>();
        items.put("ETH", Arrays.asList(new BindingTable("Binance", 10000, 10040),
                new BindingTable("CREX24", 10000, 10040),
                new BindingTable("POLONIEX", 10000, 10040),
                new BindingTable("EXMO", 10000, 10040)));
        items.put("BTC", Arrays.asList(new BindingTable("Binance", 10100, 10042),
                new BindingTable("CREX24", 9800, 9140),
                new BindingTable("POLONIEX", 9300, 9240),
                new BindingTable("EXMO", 10010, 10040)));
        items.put("XRP", Arrays.asList(new BindingTable("Binance", 10002, 12040),
                new BindingTable("CREX24", 11000, 11040),
                new BindingTable("POLONIEX", 12000, 10000),
                new BindingTable("EXMO", 10050, 10040)));
        items.put("BTCHC", Arrays.asList(new BindingTable("Binance", 10000, 10040),
                new BindingTable("CREX24", 9000, 9040),
                new BindingTable("POLONIEX", 9000, 9040),
                new BindingTable("EXMO", 9000, 9040)));

//        for (Map.Entry<String, List<BindingTable>> entry : items.entrySet()) {
//            entry.
//            entry.getValue().
//                    forEach(bindingTable -> bindingTable.getNameMarket());
//        }

        ModelAndView result = new ModelAndView("main");
        result.addObject("items", items);

        return result;
    }
}