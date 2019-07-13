package java12.cryptowin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "data_chart")
@NoArgsConstructor
public class DataForChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private long id;

    @Column (name = "date_time")
    private LocalDateTime date;

    @Column(name = "price")
    private double price;
}
