package java12.cryptowin.service.mailing;

import java12.cryptowin.entity.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
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

    @Scheduled(cron = "0 0 9 * * *", zone = "Europe/London")
    public void sendEmail() {
        Map<Long, StringBuilder> result = getNeedEmailUsers();

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
                        "Сейчас самое время совершить сделку, ведь мы нашли для Вас варианты, соответствующие Вашим желаниям в пределах 5%: \n\n" +
                        /*+*/ result.get(user.getId()).toString());
                emailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }));
    }

    private Map<Long, StringBuilder> getNeedEmailUsers() {
        Map<Long, StringBuilder> result = new HashMap<>();

        //list of cryptoMonitors for last 24 h
        List<CryptoMonitor> cryptoMonitorList = withCheckDateList();

        List<Subscription> subscriptionList = subscriptionService.getAll();

        mergeMap(result, checkMinResult(result, cryptoMonitorList, subscriptionList));
        mergeMap(result, checkMaxResult(result, cryptoMonitorList, subscriptionList));
        mergeMap(result, checkProfit(result, cryptoMonitorList, subscriptionList));

        return result;
    }

    private void mergeMap(Map<Long, StringBuilder> result, Map<Long, StringBuilder> afterCheckMap) {
        afterCheckMap.forEach((keyAfterCheck, valueAfterCheck) -> {
            result.merge(keyAfterCheck, valueAfterCheck, (keyResult, valueResult) -> keyResult).append(valueAfterCheck);
        });
    }

    private Map<Long, StringBuilder> checkMinResult(Map<Long, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        && cryptoMonitor.getSellingRate() >= (subscription.getMinResult() * 0.9)
                        && cryptoMonitor.getSellingRate() <= (subscription.getMinResult() * 1.1)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    result.put(subscription.getUser().getId(), stringBuilder
                            .append("\nМинимальная цена (в соответствии с запросом), по которой Вы можете совершить покупку в размере: $")
                            .append(cryptoMonitor.getSellingRate())
                            .append(" была за последние 24 часа выставлена на бирже ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ")
                            .append(cryptoMonitor.getExchange().getUrl())
                            .append("\n"));
                }
            }
        }
        return result;
    }

    private Map<Long, StringBuilder> checkMaxResult(Map<Long, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        & cryptoMonitor.getBuyingRate() >= (subscription.getMaxResult() * 0.9)
                        & cryptoMonitor.getBuyingRate() <= (subscription.getMaxResult() * 1.1)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    result.put(subscription.getUser().getId(), stringBuilder
                            .append("\nМаксимальная цена (в соответствии с запросом), по которой Вы можете совершить продажу в размере: $")
                            .append(cryptoMonitor.getBuyingRate())
                            .append(" была за последние 24 часа выставлена на бирже ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ")
                            .append(cryptoMonitor.getExchange().getUrl())
                            .append("\n"));
                }
            }
        }
        return result;
    }

    private Map<Long, StringBuilder> checkProfit(Map<Long, StringBuilder> result, List<CryptoMonitor> cryptoMonitorList, List<Subscription> subscriptionList) {
        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (CryptoMonitor monitor : cryptoMonitorList) {
                for (Subscription subscription : subscriptionList) {
                    if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                            && (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) >= (subscription.getProfit() * 0.9)
                            && (cryptoMonitor.getBuyingRate() - monitor.getSellingRate()) <= (subscription.getProfit() * 1.1)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        result.put(subscription.getUser().getId(), stringBuilder
                                .append("\nЖелаемый профит ")
                                .append(subscription.getProfit())
                                .append(" может быть получен благодаря:\n     покупке по цене: ")
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
                                .append(cryptoMonitor.getExchange().getUrl())
                                .append("\n"));
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
            if (cryptoMonitor.getDate().isAfter(LocalDateTime.now().minusHours(24))) {
                last24h.add(cryptoMonitor);
            }
        });
        return last24h;
    }
}