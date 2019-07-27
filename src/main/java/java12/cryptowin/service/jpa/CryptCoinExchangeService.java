package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CryptCoinExchangeService {

    public Map<String, Double> getMaxMinResultToday(List<CryptoMonitor> cryptoMonitors,CryptCoinType coinType, CryptoExchange cryptoExchange) {
        List<CryptoMonitor> cryptoTodayForCoinAndExchange = getAllForToday(cryptoMonitors, coinType, cryptoExchange);

        Map<String, Double> result = new HashMap<>();
        result.put("minSell", cryptoTodayForCoinAndExchange.stream()
                .min(Comparator.comparingDouble(CryptoMonitor::getSellingRate))
                .orElseThrow(NoSuchElementException::new)
                .getSellingRate());
        result.put("maxSell", cryptoTodayForCoinAndExchange.stream()
                .max(Comparator.comparingDouble(CryptoMonitor::getSellingRate))
                .orElseThrow(NoSuchElementException::new)
                .getSellingRate());
        result.put("minBuy", cryptoTodayForCoinAndExchange.stream()
                .min(Comparator.comparingDouble(CryptoMonitor::getBuyingRate))
                .orElseThrow(NoSuchElementException::new)
                .getBuyingRate());
        result.put("maxBuy", cryptoTodayForCoinAndExchange.stream()
                .max(Comparator.comparingDouble(CryptoMonitor::getBuyingRate))
                .orElseThrow(NoSuchElementException::new)
                .getBuyingRate());
        return result;
    }

    private List<CryptoMonitor> getAllForToday(List<CryptoMonitor> cryptoAll, CryptCoinType coinType, CryptoExchange cryptoExchange) {
        List<CryptoMonitor> cryptoTodayForCoinAndExchange = new ArrayList<>();

        cryptoAll.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getDate().toLocalDate().equals(LocalDate.now())
                    && cryptoMonitor.getCoinType().equals(coinType)
                    && cryptoMonitor.getExchange().equals(cryptoExchange)) {
                cryptoTodayForCoinAndExchange.add(cryptoMonitor);
            }
        });
        return cryptoTodayForCoinAndExchange;
    }

}
