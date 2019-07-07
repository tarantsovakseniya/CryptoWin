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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ExmoParser {

    // на exmo нет ни IOTA, ни IOT, ни MOITA

    public List<CryptoMonitor> parse() throws IOException {
        List<CryptoMonitor> list = new ArrayList();
        String apiUrl = "https://api.exmo.com/v1/ticker/";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();

        LinkedTreeMap objects = gson.fromJson(stringGson, LinkedTreeMap.class);

        objects.forEach((key, value) -> {
            if (key.toString().contains("_USD")) {
                LinkedTreeMap linked = (LinkedTreeMap) value;
                Object buy_price = linked.get("buy_price");
                Object sell_price = linked.get("sell_price");
                String nameCrypto = key.toString().substring(0, key.toString().length() - 4);
                if (nameCrypto.equals(CryptCoinType.BITCOIN.name())){
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(),  Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.ETHEREUM.name())){
                    list.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.BITCOIN_CASH.name())|| nameCrypto.equals("BCH")){
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()),LocalDate.now(),  Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.DASH.name())){
                    list.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.EOS.name())){
                    list.add(new CryptoMonitor(CryptCoinType.EOS, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.LITECOIN.name())){
                    list.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.IOTA_MIOTA.name())){
                    list.add(new CryptoMonitor(CryptCoinType.IOTA_MIOTA, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.TRON.name()) || nameCrypto.equals("TRX")){
                    list.add(new CryptoMonitor(CryptCoinType.TRON, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.STELLAR.name())){
                    list.add(new CryptoMonitor(CryptCoinType.STELLAR, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
                else if (nameCrypto.equals(CryptCoinType.XRP.name())){
                    list.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.EXMO,
                            Double.parseDouble(buy_price.toString()), LocalDate.now(), Double.parseDouble(sell_price.toString())));
                }
            }
        });
        return list;
    }
}
