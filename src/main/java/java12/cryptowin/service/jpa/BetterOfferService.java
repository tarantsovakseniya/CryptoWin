package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BetterOfferService {

    public Map<List<CryptoMonitor>, Double> getBetterOffer(List<CryptoMonitor> items, CryptCoinType cryptCoin) {
        List<CryptoMonitor> forResult = new ArrayList<>();
        items.forEach(cryptoMonitor -> {
            if(cryptoMonitor.getCoinType()==cryptCoin){
                forResult.add(cryptoMonitor);
            }
        });

       Map<List<CryptoMonitor>, Double> last = new HashMap<>();
        for (CryptoMonitor cryptoMonitor :forResult) {
            for (CryptoMonitor monitor :forResult) {
                if((cryptoMonitor.getBuyingRate()-monitor.getSellingRate())>0){
                last.put(Arrays.asList(cryptoMonitor,monitor),(cryptoMonitor.getBuyingRate()-monitor.getSellingRate()));}
            }
        }

        Map<List<CryptoMonitor>, Double> topThree =
                last.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return topThree;
    }
}
