package java12.cryptowin.pojo;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@Data
public class CryptoMonitorResult {

    private long id;

    private CryptCoinType coinType;

    private CryptoExchange exchange;

    private double buyingRate;

    private LocalDateTime date;

    private double sellingRate;

    public CryptoMonitorResult(long id, CryptCoinType coinType, CryptoExchange exchange, LocalDateTime date, double buyingRate, double sellingRate) {
        this.id = id;
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = Math.round(buyingRate*100)/100.00;
        this.date = date;
        this.sellingRate = Math.round(sellingRate*100)/100.00;
    }

    public CryptoMonitorResult(CryptCoinType coinType, CryptoExchange exchange, double buyingRate, double sellingRate) {
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = Math.round(buyingRate*100)/100.00;
        this.sellingRate = Math.round(sellingRate*100)/100.00;
    }
}
