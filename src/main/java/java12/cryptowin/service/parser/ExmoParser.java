package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.*;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExmoParser {

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> list = new ArrayList<>();

        list.add(addCurrency("BTC_USD",CryptCoinType.BITCOIN));
        list.add(addCurrency("ETH_USD",CryptCoinType.ETHEREUM));
        list.add(addCurrency("DASH_USD",CryptCoinType.DASH));
        list.add(addCurrency("EOS_USD",CryptCoinType.EOS));
        list.add(addCurrency("LTC_USD",CryptCoinType.LITECOIN));
        list.add(addCurrency("TRX_USD",CryptCoinType.TRON));
        list.add(addCurrency("XLM_USD",CryptCoinType.STELLAR));
        list.add(addCurrency("XRP_USD",CryptCoinType.XRP));

        return list;
    }

    private CryptoMonitor addCurrency(String kye, CryptCoinType cryptCoinType) throws IOException {
        String apiUrl = "https://api.exmo.com/v1/ticker/";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        LinkedTreeMap objects = gson.fromJson(stringGson, LinkedTreeMap.class);

        LinkedTreeMap values = (LinkedTreeMap) objects.get(kye);
        double buy_price = new BigDecimal(Double.parseDouble((String) (values.get("buy_price")))).setScale(3, RoundingMode.HALF_EVEN).doubleValue(); //покупка
        double sell_price = new BigDecimal(Double.parseDouble((String) (values.get("sell_price")))).setScale(3, RoundingMode.HALF_EVEN).doubleValue(); // продажа

        return new CryptoMonitor(cryptCoinType, CryptoExchange.EXMO, buy_price, LocalDateTime.now(), sell_price);
    }
}


