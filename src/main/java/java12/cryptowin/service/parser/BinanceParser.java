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
public class BinanceParser {

    //BTH, BCH  - BITCOIN_CASH
    public List<CryptoMonitor> parse() throws IOException {

        List<CryptoMonitor> list = new ArrayList<>();
        list.add(addCurrency("BTCUSDT", CryptCoinType.BITCOIN));
        list.add(addCurrency("ETHUSDT", CryptCoinType.ETHEREUM));
        list.add(addCurrency("DASHUSDT", CryptCoinType.DASH));
        list.add(addCurrency("EOSUSDT", CryptCoinType.EOS));
        list.add(addCurrency("LTCUSDT", CryptCoinType.LITECOIN));
        list.add(addCurrency("TRXUSDT", CryptCoinType.TRON));
        list.add(addCurrency("IOTAUSDT", CryptCoinType.IOTA_MIOTA));
        list.add(addCurrency("XLMUSDT", CryptCoinType.STELLAR));
        list.add(addCurrency("XRPUSDT", CryptCoinType.XRP));

        return list;
    }

    private CryptoMonitor addCurrency(String kye, CryptCoinType cryptCoinType) throws IOException {
        String apiUrl = "https://api.binance.com/api/v1/ticker/24hr";
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(stringGson, ArrayList.class);
        double bid_price = 0;
        double ask_price = 0;
        for (int i = 0; i < objects.size(); i++) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) objects.get(i);
            Object name = linkedTreeMap.get("symbol");
            if (name.equals(kye)) {
                bid_price = new BigDecimal(Double.parseDouble((String) (linkedTreeMap.get("bidPrice"))))
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue(); // string, цена покупки
                ask_price = new BigDecimal(Double.parseDouble((String) (linkedTreeMap.get("askPrice"))))
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue();// string, цена продажи
            }
        }

        return new CryptoMonitor(cryptCoinType, CryptoExchange.BINANCE, bid_price, LocalDateTime.now(), ask_price);
    }
}
