package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumeration.TimeType;
import java12.cryptowin.pojo.CryptoMonitorResult;
import java12.cryptowin.repository.CryptoMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class CryptoMonitorService {

    @Autowired
    private CryptoMonitorRepository repository;

    public List<CryptoMonitor> getAll() {
        return repository.findAll();
    }

    public void save(CryptoMonitor cryptoMonitor) {
        repository.save(cryptoMonitor);
    }

    public CryptoMonitor getById(long id) {
        return repository.getOne(id);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<CryptoMonitorResult> getAllWithMaxLocalDateTime() {
        return repository.findAllNew();
    }

    public void saveAll(Set<CryptoMonitor> set) {
        repository.saveAll(set);
    }

    public List<CryptoMonitor> fillListToUserRequest(String coinType, String timeType, String exchangeType) {
        List<CryptoMonitor> all = repository.findAll();

        CryptoMonitor cryptoMonitor;
        LocalDateTime localDate = LocalDateTime.now();
        if (timeType.equals(TimeType.TWO_WEEK.getName())) {
        localDate = localDate.minusWeeks(2);
        }
        if (timeType.equals(TimeType.WEEK.getName())) {
            localDate = localDate.minusWeeks(1);
        }
        if (timeType.equals(TimeType.TODAY.getName())) {
            localDate = localDate.minusHours(24);
        }

        int size = all.size();
        List<CryptoMonitor> result = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            cryptoMonitor = all.get(i);

            if (cryptoMonitor.getCoinType().getNameOfCoin().equals(coinType) &&
                    cryptoMonitor.getExchange().getName().equals(exchangeType) &&
                    cryptoMonitor.getDate().isAfter(localDate)) {
                result.add(cryptoMonitor);

            }
        }

        result.sort(new Comparator<CryptoMonitor>() {
            @Override
            public int compare(CryptoMonitor o1, CryptoMonitor o2) {
                if (o1.getDate().isBefore(o2.getDate())) {
                    return -1;
                } else {
                    if (o2.getDate().isAfter(o2.getDate())) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        return result;
    }
}
