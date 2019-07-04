package java12.cryptowin.repository;

import java12.cryptowin.entity.CryptoMonitor;
import org.hibernate.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CryptoMonitorRepository extends JpaRepository<CryptoMonitor,Long> {
    //@Query("")
   // List<CryptoMonitor> getByCryptoAndExchangeName(String cryptoName, String exchangeName);


}
