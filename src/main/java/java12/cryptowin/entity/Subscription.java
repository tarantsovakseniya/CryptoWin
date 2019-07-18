package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table (name="subscription")
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated (value = EnumType.STRING)
    @Column(name = "cryptCoin")
    private CryptCoinType cryptCoinType;

    @Column (name = "min_result")
    private double minResult;

    @Column (name = "max_result")
    private double maxResult;

    @Column (name = "profit")
    private double profit;

    public Subscription(long id, User user, CryptCoinType cryptCoinType, double minResult, double maxResult, double profit) {
        this.id = id;
        this.user = user;
        this.cryptCoinType = cryptCoinType;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.profit = profit;
    }

    public Subscription(User user, CryptCoinType cryptCoinType, double minResult, double maxResult, double profit) {
        this.user = user;
        this.cryptCoinType = cryptCoinType;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.profit = profit;
    }
}
