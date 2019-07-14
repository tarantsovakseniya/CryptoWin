package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoBridgeParser {
    // отсутствуют XLM, TRON(TRX), EOS, IOTA

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> list = new ArrayList<>();

        list.add(addCurrency("BTC_USDT", CryptCoinType.BITCOIN));
        list.add(addCurrency("ETH_USDT", CryptCoinType.ETHEREUM));
        list.add(addCurrency("DASH_USDT", CryptCoinType.DASH));
        list.add(addCurrency("LTC_USDT", CryptCoinType.LITECOIN));
        list.add(addCurrency("XRP_USDT", CryptCoinType.XRP));

        return list;
    }

    private CryptoMonitor addCurrency(String kye, CryptCoinType cryptCoinType) throws IOException {
        String apiUrl = "https://api.crypto-bridge.org/api/v1/ticker";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        ArrayList objects = gson.fromJson(stringGson, ArrayList.class);

        double buyingRate = 0;
        double sellingRate = 0;
        for (int i = 0; i < objects.size(); i++) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) objects.get(i);
            Object name = linkedTreeMap.get("id");
            if (name.equals(kye)) {
                buyingRate = new BigDecimal(Double.parseDouble((String) (linkedTreeMap.get("bid")))).setScale(2, RoundingMode.HALF_EVEN).doubleValue(); // string, цена покупки
                sellingRate = new BigDecimal(Double.parseDouble((String) (linkedTreeMap.get("ask")))).setScale(2, RoundingMode.HALF_EVEN).doubleValue();// string, цена продажи
            }
        }

        return new CryptoMonitor(cryptCoinType, CryptoExchange.CRYPTO_BRIDGE, buyingRate, LocalDateTime.now(), sellingRate);

    }
}

