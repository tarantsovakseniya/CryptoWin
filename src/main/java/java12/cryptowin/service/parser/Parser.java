package java12.cryptowin.service.parser;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@EnableScheduling
public class Parser {
    @Autowired
    ExmoParser exmoParser;
    @Autowired
    BinanceParser binanceParser;
    @Autowired
    CryptoBridgeParser cryptoBridgeParser;
    @Autowired
    PoloniexParser poloniexParser;
    @Autowired
    GraviexParser graviexParser;
    @Autowired
    CryptoMonitorService cryptoMonitorService;

    @Scheduled(fixedDelay=1000*5*60)
    public void update() throws IOException {
        List<CryptoMonitor> exmoList = exmoParser.parse();
        exmoList.forEach(cryptoMonitor -> cryptoMonitorService.save(cryptoMonitor));
        List<CryptoMonitor> binanceList = binanceParser.parse();
        binanceList.forEach(cryptoMonitor -> cryptoMonitorService.save(cryptoMonitor));
        List<CryptoMonitor> bridgeList = cryptoBridgeParser.parse();
        bridgeList.forEach(cryptoMonitor -> cryptoMonitorService.save(cryptoMonitor));
        List<CryptoMonitor> poloniexList = poloniexParser.parse();
        poloniexList.forEach(cryptoMonitor -> cryptoMonitorService.save(cryptoMonitor));
        List<CryptoMonitor> graviexList = graviexParser.parse();
        graviexList.forEach(cryptoMonitor -> cryptoMonitorService.save(cryptoMonitor));
    }
}
