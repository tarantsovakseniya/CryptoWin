package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GraviexParser {

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> result = new ArrayList();
        Gson gson = new Gson();

        String apiUrl = "https://graviex.net:443//api/v3/tickers.json";

        String gsonString = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        LinkedTreeMap objects = gson.fromJson(gsonString, LinkedTreeMap.class);

        objects.forEach((k, v) -> {
            LinkedTreeMap linked = (LinkedTreeMap) v;

            double buyPrice = Double.parseDouble((String) linked.get("buy"));
            double sellPrice = Double.parseDouble((String) linked.get("sell"));

            // XRP, Stellar, TRON, EOS, IOTA are absent
            if (k.equals("btcusd")) {
                result.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.GRAVIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("ethbtc")) {
                result.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.GRAVIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("ltcusd")) {
                result.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.GRAVIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("bchusdt")) {
                result.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.GRAVIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("dashusdt")) {
                result.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.GRAVIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            }
        });

        return result;
    }
}
