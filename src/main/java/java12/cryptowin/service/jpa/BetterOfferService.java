package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.pojo.CryptoMonitorResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BetterOfferService {

    public Map<List<CryptoMonitorResult>, Double> getBetterOffer(List<CryptoMonitorResult> items, CryptCoinType cryptCoin) {
        List<CryptoMonitorResult> forResult = new ArrayList<>();
        items.forEach(cryptoMonitor -> {
            if(cryptoMonitor.getCoinType()==cryptCoin){
                forResult.add(cryptoMonitor);
            }
        });

       Map<List<CryptoMonitorResult>, Double> last = new HashMap<>();
        for (CryptoMonitorResult cryptoMonitor :forResult) {
            for (CryptoMonitorResult monitor :forResult) {
                if((cryptoMonitor.getSellingRate()-monitor.getBuyingRate())>0){
                last.put(Arrays.asList(cryptoMonitor,monitor),
                        Math.round((cryptoMonitor.getSellingRate()-monitor.getBuyingRate())*100)/100.00);}
            }
        }

        Map<List<CryptoMonitorResult>, Double> topThree =
                last.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return topThree;
    }
}
