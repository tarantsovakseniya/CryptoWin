package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoBridgeParser {
    // отсутствуют XLM, TRON(TRX), EOS, IOTA

    public List<CryptoMonitor> parse() throws IOException {

        List<CryptoMonitor> list = new ArrayList<>();
        String apiUrl = "https://api.crypto-bridge.org/api/v1/ticker";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get
                ().text();

        ArrayList objects = gson.fromJson(stringGson, ArrayList.class);

        for (int i = 0; i < objects.size(); i++) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) objects.get(i);

            Object name = linkedTreeMap.get("id");
            if (name.toString().contains("_USDT")) {
                String nameCrypto = name.toString().substring(0, name.toString
                        ().length() - 5);
                double buyingRate = new BigDecimal(Double.parseDouble((String)
                        (linkedTreeMap.get("bid")))).setScale(2, RoundingMode.HALF_EVEN).doubleValue();//цена покупки
                double sellingRate = new BigDecimal(Double.parseDouble((String)
                        (linkedTreeMap.get("ask")))).setScale(2,RoundingMode.HALF_EVEN).doubleValue();//цена продажи
                if (nameCrypto.equals(CryptCoinType.BITCOIN.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                } else if (nameCrypto.equals(CryptCoinType.ETHEREUM.getNameOfCoin
                        ())) {
                    list.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                } else if (nameCrypto.equals(CryptCoinType.XRP.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                } else if (nameCrypto.equals(CryptCoinType.LITECOIN.getNameOfCoin
                        ())) {
                    list.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                } else if (nameCrypto.equals
                        (CryptCoinType.BITCOIN_CASH.getNameOfCoin()) || nameCrypto.equals("BCH")) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                } else if (nameCrypto.equals(CryptCoinType.DASH.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.CRYPTO_BRIDGE,
                            buyingRate, LocalDateTime.now(), sellingRate));
                }
            }
        }
        return list;
    }
}
