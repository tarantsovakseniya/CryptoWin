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
public class BinanceParser {

    //BTH, BCH  - BITCOIN_CASH
    public List<CryptoMonitor> parse() throws IOException {

        List<CryptoMonitor> list = new ArrayList();
        String apiUrl = "https://api.binance.com/api/v1/ticker/24hr";
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();
        Gson gson = new Gson();

        ArrayList objects = gson.fromJson(stringGson, ArrayList.class);

        for (int i = 0; i < objects.size(); i++) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) objects.get(i);
            Object name = linkedTreeMap.get("symbol");
            if (name.toString().contains("USDT")) {
                String nameCrypto = name.toString().substring(0, name.toString().length() - 4);
                double bid_price = Double.parseDouble((String)linkedTreeMap.get("bidPrice"));// string, цена покупки
                double ask_price = Double.parseDouble((String)linkedTreeMap.get("askPrice"));// string, цена продажи
                if (nameCrypto.equals(CryptCoinType.BITCOIN.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.ETHEREUM.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.BITCOIN_CASH.name()) || nameCrypto.equals("BCH")) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.DASH.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.EOS.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.EOS, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.LITECOIN.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.IOTA_MIOTA.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.IOTA_MIOTA, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.TRON.name()) || nameCrypto.equals("TRX")) {
                    list.add(new CryptoMonitor(CryptCoinType.TRON, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.STELLAR.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.STELLAR, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                } else if (nameCrypto.equals(CryptCoinType.XRP.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.BINANCE,
                            bid_price, LocalDate.now(), ask_price));
                }
            }
        }
        return list;
    }
}
