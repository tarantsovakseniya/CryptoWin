package java12.cryptowin.service.mailing;

import java12.cryptowin.entity.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendEmail() {
        Map<User, StringBuilder> result = mailing();

        result.keySet().forEach((user -> {
            try {
                MimeMessage message = emailSender.createMimeMessage();

                boolean multipart = true;

                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

                helper.setTo(user.getEmail());
                helper.setSubject("Crypto-Benefit Report");
                helper.setText("Добрый!\n" +
                        "Вы получил это письмо, т.к. ранее подписались на обновления на нашем сайте : http://crypto-benefit.com/\n" +
                        "Сейчас самое время совершить сделку, ведь мы нашли для Вас варианты, соответствующие Вашим желаниям в пределах 5%: ");
                helper.setText(String.valueOf(result.get(user)));
                emailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }));
    }

    private Map<User, StringBuilder> mailing() {
        Map<User, StringBuilder> result = new HashMap<>();

        List<CryptoMonitor> cryptoMonitorList = withCheckDateList();
        List<Subscription> subscriptionList = subscriptionService.getAll();

        mergeMap(result, checkMinResult(result, cryptoMonitorList, subscriptionList));
        mergeMap(result, checkMaxResult(result, cryptoMonitorList, subscriptionList));
        mergeMap(result, checkProfit(result, cryptoMonitorList, subscriptionList));

        return result;
    }

    private void mergeMap(Map<User, StringBuilder> result, Map<User, StringBuilder> afterCheckMap) {
        afterCheckMap.forEach((keyAfterCheck, valueAfterCheck) -> {
            result.merge(keyAfterCheck, valueAfterCheck, (keyResult, valueResult) -> keyResult).append(valueAfterCheck);
        });
    }

    private Map<User, StringBuilder> checkMinResult(Map<User, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        & cryptoMonitor.getSellingRate() == subscription.getMinResult() * 0.95
                        & cryptoMonitor.getSellingRate() <= subscription.getMaxResult() * 1.05) {
                    StringBuilder stringBuilder = new StringBuilder();
                    result.put(subscription.getUser(), stringBuilder
                            .append("\nМинимальная цена (в соответствии с запросом), по которой Вы можете совершить покупку в размере: $")
                            .append(cryptoMonitor.getSellingRate())
                            .append(" была за последние 24 часа выставлена на бирже ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ").append(cryptoMonitor.getExchange().getUrl()).append("\n"));
                }
            }
        }
        return result;
    }

    private Map<User, StringBuilder> checkMaxResult(Map<User, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        & cryptoMonitor.getBuyingRate() >= subscription.getMaxResult() * 0.95
                        & cryptoMonitor.getBuyingRate() <= subscription.getMaxResult() * 1.05) {
                    StringBuilder stringBuilder = new StringBuilder();
                    result.put(subscription.getUser(), stringBuilder
                            .append("\nМаксимальная цена (в соответствии с запросом), по которой Вы можете совершить продажу в размере: $")
                            .append(cryptoMonitor.getBuyingRate())
                            .append(" была за последние 24 часа выставлена на бирже ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ").append(cryptoMonitor.getExchange().getUrl()).append("\n"));
                }
            }
        }
        return result;
    }

    private Map<User, StringBuilder> checkProfit(Map<User, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (CryptoMonitor monitor : cryptoMonitorList) {
                for (Subscription subscription : subscriptionList) {
                    if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                            & (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) >= subscription.getProfit() * 0.95
                            & (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) <= subscription.getProfit() * 1.1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        result.put(subscription.getUser(), stringBuilder
                                .append("\nЖелаемый профит ")
                                .append(subscription.getProfit())
                                .append(" может быть получен благодаря:\n покупке по цене: ")
                                .append(monitor.getSellingRate())
                                .append(" на бирже ")
                                .append(monitor.getExchange())
                                .append("\n продаже по цене: ")
                                .append(cryptoMonitor.getBuyingRate())
                                .append(" на бирже ")
                                .append(cryptoMonitor.getExchange()));
                    }
                }
            }
        }
        return result;
    }

    private List<CryptoMonitor> withCheckDateList() {
        List<CryptoMonitor> cryptoMonitorList = cryptoMonitorService.getAll();
        List<CryptoMonitor> last24h = new ArrayList<>();
        cryptoMonitorList.forEach(cryptoMonitor -> {
            if (cryptoMonitor.getDate().isBefore(LocalDateTime.now())
                    & cryptoMonitor.getDate().isAfter(LocalDateTime.now().minusHours(24))) {
                last24h.add(cryptoMonitor);
            }
        });
        return last24h;
    }
}