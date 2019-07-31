package java12.cryptowin.service.mailing;

import java12.cryptowin.entity.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@EnableScheduling
public class SubscriberMailSender {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;

    private Map<Long, StringBuilder> result = new HashMap<>();

    @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Kiev")
    public void sendEmail() {
        getNeedEmailUsers();

        result.keySet().forEach((id -> {
            User user = userService.getById(id);
            try {
                MimeMessage message = emailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

                helper.setTo(user.getEmail());
                helper.setSubject("Crypto-Benefit Report");
                helper.setText("Добрый день!\n\n" +
                        "Вы получили это письмо, т.к. ранее подписались на обновления на нашем сайте : http://crypto-benefit.com/\n" +
                        "Сейчас самое время совершить сделку, ведь мы нашли для Вас варианты, соответствующие Вашим желаниям в пределах 3%: \n" +
                        /*+*/ result.get(user.getId()).toString());
                emailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }));
    }

    private void getNeedEmailUsers() {

        //list of cryptoMonitors for last 24 h
        List<CryptoMonitor> cryptoMonitorList = cryptoMonitorService.getAllWithMaxLocalDateTime();

        List<Subscription> subscriptionList = subscriptionService.getAll();

        checkMinResult(cryptoMonitorList, subscriptionList);
        result.keySet().forEach(key->result.get(key).append("\n"));
        checkMaxResult(cryptoMonitorList, subscriptionList);
        result.keySet().forEach(key->result.get(key).append("\n"));
        checkProfit(cryptoMonitorList, subscriptionList);
    }

    private void checkMinResult(List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (Subscription subscription : subscriptionList) {
            for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        & cryptoMonitor.getSellingRate() >= (subscription.getMinResult() * 0.97)
                        && cryptoMonitor.getSellingRate() <= (subscription.getMinResult() * 1.03)) {
                    result.put(subscription.getUser().getId(), new StringBuilder()
                            .append("\nМинимальная цена (в соответствии с запросом), по которой Вы можете совершить покупку " + subscription.getCryptCoinType().getNameOfCoin() + " в размере: $")
                            .append(cryptoMonitor.getSellingRate())
                            .append(" в настоящее время выставлена на бирже ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ")
                            .append(cryptoMonitor.getExchange().getUrl()));
                }
            }
        }
    }

    private void checkMaxResult(List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (Subscription subscription : subscriptionList) {
            for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        & cryptoMonitor.getBuyingRate() >= (subscription.getMaxResult() * 0.97)
                        & cryptoMonitor.getBuyingRate() <= (subscription.getMaxResult() * 1.03)) {
                    if (result.get(subscription.getUser().getId()).length() != 0) {
                        getMaxText(result.get(subscription.getUser().getId()), subscription,cryptoMonitor);
                    } else {
                        result.put(subscription.getUser().getId(), getMaxText(new StringBuilder(), subscription,cryptoMonitor));
                    }
                }
            }
        }
    }

    private StringBuilder getMaxText(StringBuilder stringBuilder, Subscription subscription, CryptoMonitor cryptoMonitor){
        stringBuilder
                .append("\nМаксимальная цена (в соответствии с запросом), по которой Вы можете совершить продажу ")
                .append(subscription.getCryptCoinType().getNameOfCoin())
                .append(" в размере: $")
                .append(cryptoMonitor.getBuyingRate())
                .append(" в настоящее время выставлена на бирже ")
                .append(cryptoMonitor.getExchange())
                .append(" - ")
                .append(cryptoMonitor.getExchange().getUrl());
        return stringBuilder;
    }

    private StringBuilder getProfitText(StringBuilder stringBuilder, Subscription subscription, CryptoMonitor cryptoMonitor, CryptoMonitor monitor){
        stringBuilder
                .append("\nЖелаемый профит криптовалюты: " + subscription.getCryptCoinType().getNameOfCoin())
                .append(subscription.getProfit())
                .append(" может быть получен в настоящее вермя благодаря:\n     покупке по цене: ")
                .append(monitor.getSellingRate())
                .append("$ на бирже ")
                .append(monitor.getExchange())
                .append(" - ")
                .append(monitor.getExchange().getUrl())
                .append("\n     продаже по цене: ")
                .append(cryptoMonitor.getBuyingRate())
                .append("$ на бирже ")
                .append(cryptoMonitor.getExchange())
                .append(" - ")
                .append(cryptoMonitor.getExchange().getUrl());
        return stringBuilder;
    }

    private void checkProfit(List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (CryptoMonitor monitor : cryptoMonitorList) {
                for (Subscription subscription : subscriptionList) {
                    if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                            && (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) >= (subscription.getProfit() * 0.97)
                            && (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) <= (subscription.getProfit() * 1.03)) {
                        if (result.get(subscription.getUser().getId()).length() != 0) {
                            getProfitText(result.get(subscription.getUser().getId()), subscription,cryptoMonitor, monitor);
                        }
                        else{
                        result.put(subscription.getUser().getId(), getProfitText(new StringBuilder(), subscription, cryptoMonitor, monitor));
                    }}
                }
            }
        }
    }
}