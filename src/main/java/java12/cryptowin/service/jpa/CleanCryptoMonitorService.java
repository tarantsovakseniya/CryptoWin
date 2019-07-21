package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class CleanCryptoMonitorService {
    @Autowired
    CryptoMonitorService cryptoMonitorService;

    // executed every day at 2:01 am
    @Scheduled(cron = "0 1 2 * * *", zone = "Europe/Rome")
    public void clean() {
        List<CryptoMonitor> cryptoMonitors = cryptoMonitorService.getAll();
        cryptoMonitors.forEach(cryptoMonitor -> {
            LocalDateTime cryptoMonitorDate = cryptoMonitor.getDate();

            LocalDateTime minusTwoWeeks = LocalDateTime.now().minusWeeks(2);
            if (cryptoMonitorDate.isBefore(minusTwoWeeks)) {
                //System.out.println("deleted monitor " + cryptoMonitor);
                cryptoMonitorService.deleteById(cryptoMonitor.getId());
            }
        });
    }
}
