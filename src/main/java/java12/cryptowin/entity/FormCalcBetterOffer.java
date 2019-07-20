package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import java12.cryptowin.entity.enumeration.CryptoExchange;
import org.springframework.web.bind.annotation.RequestParam;

public class FormCalcBetterOffer {

    private CryptoExchange exchangeToBuy;
    private double feeTaker;
    private double addFeeTaker;
    private double rangeBuy;
    private CryptoExchange exchangeToSell;
    private double feeMaker;
    private double addFeeMaker;
    private double rangeSell;
    private double sumBuyTotal;
    private double sumSellTotal;
    private double profitTotal;

    public CryptoExchange getExchangeToBuy() {
        return exchangeToBuy;
    }

    public void setExchangeToBuy(CryptoExchange exchangeToBuy) {
        this.exchangeToBuy = exchangeToBuy;
    }

    public double getFeeTaker() {
        return feeTaker;
    }

    public void setFeeTaker(double feeTaker) {
        this.feeTaker = feeTaker;
    }

    public double getAddFeeTaker() {
        return addFeeTaker;
    }

    public void setAddFeeTaker(double addFeeTaker) {
        this.addFeeTaker = addFeeTaker;
    }

    public double getRangeBuy() {
        return rangeBuy;
    }

    public void setRangeBuy(double rangeBuy) {
        this.rangeBuy = rangeBuy;
    }

    public CryptoExchange getExchangeToSell() {
        return exchangeToSell;
    }

    public void setExchangeToSell(CryptoExchange exchangeToSell) {
        this.exchangeToSell = exchangeToSell;
    }

    public double getFeeMaker() {
        return feeMaker;
    }

    public void setFeeMaker(double feeMaker) {
        this.feeMaker = feeMaker;
    }

    public double getAddFeeMaker() {
        return addFeeMaker;
    }

    public void setAddFeeMaker(double addFeeMaker) {
        this.addFeeMaker = addFeeMaker;
    }

    public double getRangeSell() {
        return rangeSell;
    }

    public void setRangeSell(double rangeSell) {
        this.rangeSell = rangeSell;
    }

    public double getSumBuyTotal() {
        return sumBuyTotal;
    }

    public void setSumBuyTotal(double sumBuyTotal) {
        this.sumBuyTotal = sumBuyTotal;
    }

    public double getSumSellTotal() {
        return sumSellTotal;
    }

    public void setSumSellTotal(double sumSellTotal) {
        this.sumSellTotal = sumSellTotal;
    }

    public double getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(double profitTotal) {
        this.profitTotal = profitTotal;
    }
}
