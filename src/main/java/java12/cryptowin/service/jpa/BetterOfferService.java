package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.FormCalcBetterOffer;
import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import java12.cryptowin.pojo.CryptoMonitorResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BetterOfferService {

    public Map<List<CryptoMonitorResult>, Double> getBetterOffer(List<CryptoMonitorResult> items, CryptCoinType cryptCoin) {
        List<CryptoMonitorResult> forResult = formListOfCryptoMonitors(items, cryptCoin);

        Map<List<CryptoMonitorResult>, Double> last = new HashMap<>();
        for (CryptoMonitorResult cryptoMonitor : forResult) {
            for (CryptoMonitorResult monitor : forResult) {
                if ((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) > 1) {
                    last.put(Arrays.asList(cryptoMonitor, monitor),
                            Math.round((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) * 100) / 100.00);
                }
            }
        }
        return formListOfThreeBetter(last);
    }

    public Map<List<CryptoMonitorResult>, Double> getBetterOfferNew(List<CryptoMonitorResult> items, CryptCoinType cryptCoin, double buy) {
        List<CryptoMonitorResult> forResult = formListOfCryptoMonitors(items, cryptCoin);

        Map<List<CryptoMonitorResult>, Double> last = new HashMap<>();
        for (CryptoMonitorResult cryptoMonitor : forResult) {
            for (CryptoMonitorResult monitor : forResult) {
                if ((cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) > 1) {

                    double coinsQuantity = buy / monitor.getSellingRate();

                    last.put(Arrays.asList(
                            new CryptoMonitorResult(
                                    cryptoMonitor.getCoinType(),
                                    cryptoMonitor.getExchange(),
                                    cryptoMonitor.getBuyingRate() * coinsQuantity,
                                    cryptoMonitor.getSellingRate() * coinsQuantity),
                            new CryptoMonitorResult(
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

    private Map<List<CryptoMonitorResult>, Double> formListOfThreeBetter(Map<List<CryptoMonitorResult>, Double> last) {
        Map<List<CryptoMonitorResult>, Double> topThree =
                last.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topThree;
    }

    private List<CryptoMonitorResult> formListOfCryptoMonitors(List<CryptoMonitorResult> items, CryptCoinType cryptCoin) {
        List<CryptoMonitorResult> forResult = new ArrayList<>();
        items.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getCoinType() == cryptCoin) {
                forResult.add(cryptoMonitor);
            }
        });
        return forResult;
    }

    public void getCalc(FormCalcBetterOffer formCalc, List<CryptoMonitorResult> cryptoMonitorResultList,
                                CryptCoinType cryptCoin, double pay) {

        List<CryptoMonitorResult> items = formListOfCryptoMonitors(cryptoMonitorResultList, cryptCoin);

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
