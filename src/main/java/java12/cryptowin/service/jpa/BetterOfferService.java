package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.FormCalcBetterOffer;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BetterOfferService {

    public Map<List<CryptoMonitor>, Double> getBetterOffer(List<CryptoMonitor> items, CryptCoinType cryptCoin) {
        List<CryptoMonitor> forResult = formListOfCryptoMonitors(items, cryptCoin);

        Map<List<CryptoMonitor>, Double> last = new HashMap<>();
        for (CryptoMonitor cryptoMonitor : forResult) {
            for (CryptoMonitor monitor : forResult) {
                if ((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) > 1) {
                    last.put(Arrays.asList(cryptoMonitor, monitor),
                            Math.round((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) * 100) / 100.00);
                }
            }
        }
        return formListOfThreeBetter(last);
    }

    public Map<List<CryptoMonitor>, Double> getBetterOfferNew(List<CryptoMonitor> items, CryptCoinType cryptCoin, double buy) {
        List<CryptoMonitor> forResult = formListOfCryptoMonitors(items, cryptCoin);

        Map<List<CryptoMonitor>, Double> last = new HashMap<>();
        for (CryptoMonitor cryptoMonitor : forResult) {
            for (CryptoMonitor monitor : forResult) {
                if ((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) > 1) {

                    double coinsQuantity = buy / monitor.getSellingRate();

                    last.put(Arrays.asList(
                            new CryptoMonitor(
                                    cryptoMonitor.getCoinType(),
                                    cryptoMonitor.getExchange(),
                                    cryptoMonitor.getBuyingRate() * coinsQuantity,
                                    cryptoMonitor.getSellingRate() * coinsQuantity),
                            new CryptoMonitor(
                                    monitor.getCoinType(),
                                    monitor.getExchange(),
                                    monitor.getBuyingRate() * coinsQuantity,
                                    monitor.getSellingRate() * coinsQuantity)),
                            Math.round((cryptoMonitor.getBuyingRate() * coinsQuantity - monitor.getSellingRate() * coinsQuantity) * 100) / 100.00);
                }
            }
        }
        return formListOfThreeBetter(last);
    }

    private Map<List<CryptoMonitor>, Double> formListOfThreeBetter(Map<List<CryptoMonitor>, Double> last) {
        Map<List<CryptoMonitor>, Double> topThree =
                last.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topThree;
    }

    private List<CryptoMonitor> formListOfCryptoMonitors(List<CryptoMonitor> items, CryptCoinType cryptCoin) {
        List<CryptoMonitor> forResult = new ArrayList<>();
        items.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getCoinType() == cryptCoin) {
                forResult.add(cryptoMonitor);
            }
        });
        return forResult;
    }

    public void getCalc(FormCalcBetterOffer formCalc, List<CryptoMonitor> cryptoMonitorResultList,
                                CryptCoinType cryptCoin, double pay) {

        List<CryptoMonitor> items = formListOfCryptoMonitors(cryptoMonitorResultList, cryptCoin);

        final double[] cryptoQuantity = new double[1];

        items.forEach(cryptoMonitorResult -> {
            if (cryptoMonitorResult.getExchange().equals(formCalc.getExchangeToBuy())) {
                cryptoQuantity[0] = (pay - (formCalc.getRangeBuy() / 100) * pay) / cryptoMonitorResult.getSellingRate();
                double payTotal = pay + formCalc.getFeeTaker() + formCalc.getAddFeeTaker();
                formCalc.setSumBuyTotal(Math.round(payTotal * 100) / 100.00);
            }
        });
        items.forEach(cryptoMonitorResult -> {
            if (cryptoMonitorResult.getExchange().equals(formCalc.getExchangeToSell())) {
                double sellQuantityCrypto = cryptoMonitorResult.getBuyingRate() * cryptoQuantity[0];
                double sumAfterMinusRangeSell = sellQuantityCrypto - (formCalc.getRangeSell() / 100) * sellQuantityCrypto;
                double sellTotal = sumAfterMinusRangeSell - formCalc.getFeeMaker() - formCalc.getAddFeeMaker();
                formCalc.setSumSellTotal(Math.round(sellTotal * 100) / 100.00);
            }
        });
        double benefit = formCalc.getSumSellTotal() - formCalc.getSumBuyTotal();
        formCalc.setProfitTotal(Math.round(benefit * 100) / 100.00);
    }
}
