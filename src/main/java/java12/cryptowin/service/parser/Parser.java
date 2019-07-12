package java12.cryptowin.service.parser;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    BitfinexParser bitfinexParser;
    @Autowired
    CryptoMonitorService cryptoMonitorService;

    @Scheduled(fixedDelay=1000*5*60)
    public void update()  {
        Set<CryptoMonitor> parsers = new HashSet<>();
        try {
            parsers.addAll(exmoParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parsers.addAll(binanceParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parsers.addAll(cryptoBridgeParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parsers.addAll(graviexParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parsers.addAll(poloniexParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parsers.addAll(bitfinexParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cryptoMonitorService.saveAll(parsers);
    }
}
