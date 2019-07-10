package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ExmoParser {

    // на exmo нет ни IOTA, ни IOT, ни MOITA

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> list = new ArrayList<>();
        String apiUrl = "https://api.exmo.com/v1/ticker/";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();

        LinkedTreeMap objects = gson.fromJson(stringGson, LinkedTreeMap.class);

        objects.forEach((key, value) -> {
            if (key.toString().contains("_USD")) {
                LinkedTreeMap linked = (LinkedTreeMap) value;
                double buy_price = new BigDecimal(Double.parseDouble((String) (linked.get("buy_price")))).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                double sell_price = new BigDecimal(Double.parseDouble((String) (linked.get("sell_price")))).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
                String nameCrypto = key.toString().substring(0, key.toString().length() - 4);
                if (nameCrypto.equals(CryptCoinType.BITCOIN.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.ETHEREUM.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.BITCOIN_CASH.getNameOfCoin()) || nameCrypto.equals("BCH")) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.DASH.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.EOS.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.EOS, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.LITECOIN.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.IOTA_MIOTA.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.IOTA_MIOTA, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.TRON.getNameOfCoin()) || nameCrypto.equals("TRX")) {
                    list.add(new CryptoMonitor(CryptCoinType.TRON, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.STELLAR.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.STELLAR, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                } else if (nameCrypto.equals(CryptCoinType.XRP.getNameOfCoin())) {
                    list.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.EXMO,
                            buy_price, LocalDate.now(), sell_price));
                }
            }
        });
        return list;
    }
}
