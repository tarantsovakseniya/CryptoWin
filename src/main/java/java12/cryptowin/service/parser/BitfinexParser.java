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
public class BitfinexParser {

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> result = new ArrayList<>();
        String apiUrl = "https://api.bitfinex.com/v1/pubticker/";

        result.add(addCurrency(apiUrl + "btcusd", CryptCoinType.BITCOIN));
        result.add(addCurrency(apiUrl + "ltcusd", CryptCoinType.LITECOIN));
        result.add(addCurrency(apiUrl + "ethusd", CryptCoinType.ETHEREUM));
        result.add(addCurrency(apiUrl + "dshusd", CryptCoinType.DASH));
        result.add(addCurrency(apiUrl + "xrpusd", CryptCoinType.XRP));
        result.add(addCurrency(apiUrl + "eosusd", CryptCoinType.EOS));
        result.add(addCurrency(apiUrl + "iotusd", CryptCoinType.IOTA_MIOTA));
        result.add(addCurrency(apiUrl + "xlmusd", CryptCoinType.STELLAR));
        result.add(addCurrency(apiUrl + "trxusd", CryptCoinType.TRON));

        return result;
    }

    private CryptoMonitor addCurrency(String url, CryptCoinType type) throws IOException {
        Gson gson = new Gson();
        double buyPrice;
        double sellPrice;

        String gsonString = Jsoup.connect(url).ignoreContentType(true).get().text();
        LinkedTreeMap ticker = gson.fromJson(gsonString, LinkedTreeMap.class);

        buyPrice = Double.parseDouble((String) ticker.get("bid"));
        sellPrice = Double.parseDouble((String) ticker.get("ask"));

        return new CryptoMonitor(type, CryptoExchange.BITFINEX,
                buyPrice, LocalDateTime.now(), sellPrice);
    }
}
