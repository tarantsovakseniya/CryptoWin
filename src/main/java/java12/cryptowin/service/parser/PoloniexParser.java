package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.*;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PoloniexParser {
    public List<CryptoMonitor> parse() throws IOException {

        List<CryptoMonitor> result = new ArrayList<>();

        result.add(addCurrency("USDC_BTC", CryptCoinType.BITCOIN));
        result.add(addCurrency("USDC_LTC", CryptCoinType.LITECOIN));
        result.add(addCurrency("USDC_ETH", CryptCoinType.ETHEREUM));
        result.add(addCurrency("USDC_XRP", CryptCoinType.XRP));
        result.add(addCurrency("USDC_STR", CryptCoinType.STELLAR));
        result.add(addCurrency("USDT_DASH", CryptCoinType.DASH));
        result.add(addCurrency("USDT_EOS", CryptCoinType.EOS));

        return result;
    }

    private CryptoMonitor addCurrency(String key, CryptCoinType cryptCoinType) throws IOException {
        Gson gson = new Gson();
        String apiUrl = "https://poloniex.com/public?command=returnTicker";
        String gsonString = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        LinkedTreeMap objects = gson.fromJson(gsonString, LinkedTreeMap.class);
        LinkedTreeMap values = (LinkedTreeMap) objects.get(key);
        double buyPrice = Double.parseDouble((String) values.get("lowestAsk"));
        double sellPrice = Double.parseDouble((String) values.get("highestBid"));

        return new CryptoMonitor(cryptCoinType, CryptoExchange.POLONIEX, buyPrice, LocalDateTime.now(), sellPrice);
    }
}
