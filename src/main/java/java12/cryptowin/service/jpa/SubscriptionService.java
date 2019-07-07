package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.Subscription;
import java12.cryptowin.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAll(){return subscriptionRepository.findAll();}

    public void save(Subscription cryptoMonitor){ subscriptionRepository.save(cryptoMonitor);}

    public Subscription getById(long id){return subscriptionRepository.getOne(id);}

    public void deleteById(long id){subscriptionRepository.deleteById(id);}

}
