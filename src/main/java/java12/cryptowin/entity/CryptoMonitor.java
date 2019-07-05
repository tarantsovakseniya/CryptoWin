package java12.cryptowin.entity;

import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity()
@Table(name = "crypto_monitor")
@NoArgsConstructor
public class CryptoMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRYPTO_M_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_COIN")
    private CryptCoinType coinType;

    //there will be enum for exchange
    @Enumerated(EnumType.STRING)
    @Column(name = "EXCHANGE")
    private CryptoExchange exchange;

    @Column(name = "BUYING_RATE")
    private double buyingRate;

    @Column(name = "CLICK_DATE")
    private LocalDate date;

    @Column(name = "SELLING_RATE")
    private double sellingRate;

    public CryptoMonitor(CryptCoinType coinType, CryptoExchange exchange, double buyingRate, double sellingRate) {
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }

    public CryptoMonitor( CryptCoinType coinType, CryptoExchange exchange, double buyingRate, LocalDate date, double sellingRate) {
        this.coinType = coinType;
        this.exchange = exchange;
        this.buyingRate = buyingRate;
        this.date = date;
        this.sellingRate = sellingRate;
    }
}
