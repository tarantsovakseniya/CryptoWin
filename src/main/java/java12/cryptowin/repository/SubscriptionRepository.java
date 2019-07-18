package java12.cryptowin.repository;

import java12.cryptowin.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("from Subscription as s where s.id = id")
    List<Subscription> getByUserId(@Param("id") long id);
}
