package java12.cryptowin.entity;

import java12.cryptowin.entity.enumClasses.CryptCoinType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name="subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cryptCoin")
    private CryptCoinType cryptCoinType;

    @Column (name = "minResult")
    private double minResult;

    @Column (name = "maxResult")
    private double maxResult;
}
