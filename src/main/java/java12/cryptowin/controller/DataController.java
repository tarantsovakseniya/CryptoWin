package java12.cryptowin.controller;

import com.google.gson.Gson;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/data")
public class DataController {
    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @GetMapping
    @ResponseBody
    public String getAnswer(@RequestParam(name = "coinType") String coinType,
                        @RequestParam(name = "exchangeType") String exchangeType,
                        @RequestParam(name = "timeType") String timeType,
                        @RequestParam(name = "state") String state) {
        List<CryptoMonitor> result = cryptoMonitorService.fillListToUserRequest(coinType, timeType,  exchangeType);
        Object[][] main = new Object[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            main[i][0] = result.get(i).getDate().toString();
            if (state.equals("sell")) {
                main[i][1] = result.get(i).getSellingRate();
            } else {
                main[i][1] = result.get(i).getBuyingRate();
            }
        }
        return new Gson().toJson(main);
    }
}
