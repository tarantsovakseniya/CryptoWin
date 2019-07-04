package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.repository.CryptoMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoMonitorService {

    @Autowired
    private CryptoMonitorRepository repository;

    public List<CryptoMonitor> getAll(){return repository.findAll();}

    public void save(CryptoMonitor cryptoMonitor){ repository.save(cryptoMonitor);}

    public CryptoMonitor getById(long id){return repository.getOne(id);}

    public void deleteById(long id){repository.deleteById(id);}


}
