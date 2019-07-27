package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.*;
import java12.cryptowin.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAll(){return subscriptionRepository.findAll();}

    public void save(Subscription cryptoMonitor){ subscriptionRepository.save(cryptoMonitor);}

    public Subscription getById(long id){return subscriptionRepository.getOne(id);}

    public void deleteById(long id){subscriptionRepository.deleteById(id);}

    public List<Subscription> getByUser(User user){
        List<Subscription> all = subscriptionRepository.findAll();
        List<Subscription> result = new ArrayList<>();

        all.forEach(subscription -> {
            if(subscription.getUser().equals(user)){
                result.add(subscription);
            }
        });

        return result.size()== 0 ? null : result ;
    }
}
