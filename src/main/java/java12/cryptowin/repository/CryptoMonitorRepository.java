package java12.cryptowin.repository;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.pojo.CryptoMonitorResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface CryptoMonitorRepository extends JpaRepository<CryptoMonitor, Long> {

    @Query("select new java12.cryptowin.pojo.CryptoMonitorResult(cm.id, cm.coinType, cm.exchange, max(cm.date), cm.buyingRate, cm.sellingRate)" +
            "from CryptoMonitor as cm " +
            "group by cm.coinType, cm.exchange "+
            "order by max(cm.date) desc"
    )
    List<CryptoMonitorResult> findAllNew();
}