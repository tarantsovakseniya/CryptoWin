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
public class PoloniexParser {

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> result = new ArrayList();
        Gson gson = new Gson();

        String apiUrl = "https://poloniex.com/public?command=returnTicker";

        String gsonString = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        LinkedTreeMap objects = gson.fromJson(gsonString, LinkedTreeMap.class);

        objects.forEach((k,v) -> {
            LinkedTreeMap linked = (LinkedTreeMap) v;

            double buyPrice = Double.parseDouble((String) linked.get("lowestAsk"));
            double sellPrice = Double.parseDouble((String) linked.get("highestBid"));

            // IOTA and TRON are absent
            if (k.equals("USDC_BTC")) {
                result.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDC_LTC")) {
                result.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDC_ETH")) {
                result.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDC_XRP")) {
                result.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDC_BCH")) {
                result.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDC_STR")) {
                result.add(new CryptoMonitor(CryptCoinType.STELLAR, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDT_DASH")) {
                result.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            } else if (k.equals("USDT_EOS")) {
                result.add(new CryptoMonitor(CryptCoinType.EOS, CryptoExchange.POLONIEX,
                        buyPrice, LocalDate.now(), sellPrice));
            }
        });

        return result;
    }
}
