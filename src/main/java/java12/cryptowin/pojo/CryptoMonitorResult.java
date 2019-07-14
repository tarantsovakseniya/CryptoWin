package java12.cryptowin.pojo;

import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
public class CryptoMonitorResult {

    private long id;

    private CryptCoinType coinType;

    private CryptoExchange exchange;

    private double buyingRate;

    private LocalDateTime date;

    private double sellingRate;

    public CryptoMonitorResult() {
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CryptCoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CryptCoinType coinType) {
        this.coinType = coinType;
    }

    public CryptoExchange getExchange() {
        return exchange;
    }

    public void setExchange(CryptoExchange exchange) {
        this.exchange = exchange;
    }

    public double getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(double buyingRate) {
        this.buyingRate = Math.round(buyingRate*100)/100.00;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(double sellingRate) {
        this.sellingRate = Math.round(sellingRate*100)/100.00;
    }
}
