package java12.cryptowin.service.parser;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@EnableScheduling
public class Parser {
    @Autowired
    private ExmoParser exmoParser;
    @Autowired
    private BinanceParser binanceParser;
    @Autowired
    private CryptoBridgeParser cryptoBridgeParser;
    @Autowired
    private PoloniexParser poloniexParser;
    @Autowired
    private GraviexParser graviexParser;
    @Autowired
    private BitfinexParser bitfinexParser;
    @Autowired
    private CryptoMonitorService cryptoMonitorService;

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
