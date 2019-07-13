package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.DataForChart;
import java12.cryptowin.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataService {
    @Autowired
    private DataRepository dataRepository;

    public List<DataForChart> getAll(){return dataRepository.findAll();}

    public void save(DataForChart data){ dataRepository.save(data);}

    public DataForChart getById(long id){return dataRepository.getOne(id);}

    public void deleteById(long id){dataRepository.deleteById(id);}

    public void clear(){dataRepository.deleteAll();}
}
