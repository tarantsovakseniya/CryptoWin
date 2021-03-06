package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity()
@Table(name = "crypto_monitor")
@NoArgsConstructor
public class CryptoMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crypto_m_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_coin")
    private CryptCoinType coinType;

    //there will be enum for exchange
    @Enumerated(EnumType.STRING)
    @Column(name = "exchange")
    private CryptoExchange exchange;

    @Column(name = "buying_rate")
    private double buyingRate;

    @Column(name = "date_time")
    private LocalDateTime date;

    @Column(name = "selling_rate")
    private double sellingRate;

    public CryptoMonitor(CryptCoinType coinType, CryptoExchange exchange, double buyingRate, double sellingRate) {
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = Math.round(buyingRate * 100) / 100.00;
        this.sellingRate = Math.round(sellingRate * 100) / 100.00;
    }

    public CryptoMonitor(CryptCoinType coinType, CryptoExchange exchange, double buyingRate, LocalDateTime date, double sellingRate) {
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = Math.round(buyingRate * 100) / 100.00;
        this.date = date;
        this.sellingRate = Math.round(sellingRate * 100) / 100.00;
    }
}
