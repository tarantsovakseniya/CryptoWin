package java12.cryptowin.repository;

import java12.cryptowin.entity.DataForChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataForChart, Long> {

}
