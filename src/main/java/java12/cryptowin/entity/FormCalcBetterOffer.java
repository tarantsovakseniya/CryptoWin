package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.CryptoExchange;
import lombok.Data;

@Data
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
}
