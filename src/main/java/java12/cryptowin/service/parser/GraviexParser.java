package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GraviexParser {

    public List<CryptoMonitor> parse() throws IOException {
        // XRP, Stellar, TRON, EOS, IOTA are absent

        List<CryptoMonitor> result = new ArrayList();

        result.add(addCurrency("btcusd", CryptCoinType.BITCOIN));
        result.add(addCurrency("ethusd", CryptCoinType.ETHEREUM));
        result.add(addCurrency("ltcusd", CryptCoinType.LITECOIN));

        return result;
    }
    private CryptoMonitor addCurrency(String kye, CryptCoinType cryptCoinType) throws IOException {
        Gson gson = new Gson();
        String apiUrl = "https://graviex.net:443//api/v3/tickers.json";
        String gsonString = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        LinkedTreeMap objects = gson.fromJson(gsonString, LinkedTreeMap.class);
        LinkedTreeMap values = (LinkedTreeMap) objects.get(kye);
        double buyPrice = Double.parseDouble((String) values.get("buy"));
        double sellPrice = Double.parseDouble((String) values.get("sell"));

        return new CryptoMonitor(cryptCoinType, CryptoExchange.GRAVIEX, buyPrice, LocalDateTime.now(), sellPrice);

    }
}
