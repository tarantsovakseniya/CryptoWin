package java12.cryptowin.repository;

import java12.cryptowin.entity.CryptoMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface CryptoMonitorRepository extends JpaRepository<CryptoMonitor, Long> {

    @Query(nativeQuery = true, value = "SELECT * from crypto_monitor WHERE date_time IN (\n" +
            "SELECT MAX(date_time) FROM crypto_monitor\n" +
            "GROUP BY type_coin,  crypto_monitor.exchange)\n" +
            "ORDER BY date_time desc;")
    List<CryptoMonitor> findAllNew();


//    SELECT * from crypto_monitor WHERE date_time IN (
//            SELECT MAX(date_time) FROM crypto_monitor
//    GROUP BY type_coin,  crypto_monitor.exchange)
//    ORDER BY date_time desc;
}