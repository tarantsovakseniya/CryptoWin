package java12.cryptowin.entity;

import java12.cryptowin.entity.enam.CryptCoinType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity()
@Table(name = "crypto_monitor")
public class CryptoMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRYPTO_M_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_COIN")
    private CryptCoinType coinType;

    @Column(name = "BUYING_RATE")
    private double buyingRate;

    @Column(name = "CLICK_DATE")
    private LocalDate date;

    @Column(name = "SELLING_RATE")
    private double sellingRate;
}
