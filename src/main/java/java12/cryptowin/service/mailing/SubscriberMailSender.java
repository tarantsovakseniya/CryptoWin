package java12.cryptowin.service.mailing;

import java12.cryptowin.entity.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
                helper.setText("Hello!\nYou've got this email because you subscribed to changes of crypto markets.");
                helper.setText(String.valueOf(result.get(user)));
                emailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }));
    }

    private Map<User, StringBuilder> mailing() {
        Map<User, StringBuilder> result = new HashMap<>();

        List<CryptoMonitor> cryptoMonitorList = cryptoMonitorService.getAll();
        List<Subscription> subscriptionList = subscriptionService.getAll();

        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                StringBuilder stringBuilder = new StringBuilder();
                result.put(subscription.getUser(), stringBuilder);
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        && cryptoMonitor.getBuyingRate() == subscription.getMinResult()) {
                    result.get(subscription.getUser())
                            .append("\nBuying min rate has reached the result: ")
                            .append(cryptoMonitor.getBuyingRate())
                            .append(" on ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ")
                            .append(cryptoMonitor.getExchange().getUrl())
                            .append(" exchange.");
                }
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        && cryptoMonitor.getSellingRate() == subscription.getMaxResult()) {
                    result.get(subscription.getUser())
                            .append("\nSelling min rate has reached the result: ")
                            .append(cryptoMonitor.getSellingRate())
                            .append(" on ")
                            .append(cryptoMonitor.getExchange())
                            .append(" - ")
                            .append(cryptoMonitor.getExchange().getUrl())
                            .append(" exchange.");
                }
            }
        }
        return result;
    }
}