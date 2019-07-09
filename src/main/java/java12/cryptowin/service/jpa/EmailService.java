package java12.cryptowin.service.jpa;

import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.Subscription;
import java12.cryptowin.entity.User;
import java12.cryptowin.service.jpa.CryptoMonitorService;
import java12.cryptowin.service.jpa.SubscriptionService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CryptoMonitorService cryptoMonitorService;

    @Autowired
    private UserService userService;

    public void sendEmail() throws FileNotFoundException {
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

    public Map<User, StringBuilder> mailing() {
        Map<User, StringBuilder> result = new HashMap<>();


        List<CryptoMonitor> cryptoMonitorList = cryptoMonitorService.getAll();
        List<Subscription> subscriptionList = subscriptionService.getAll();

        for (CryptoMonitor cryptoMonitor : cryptoMonitorList) {
            for (Subscription subscription : subscriptionList) {
                StringBuilder stringBuilder = new StringBuilder();
                result.put(subscription.getUser(), stringBuilder);
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        && cryptoMonitor.getBuyingRate() == subscription.getMinResult()) {
                    result.get(subscription.getUser()).append("Buying min rate has reached the result: " + cryptoMonitor.getBuyingRate() + " on " + cryptoMonitor.getExchange() + " market.");
                }
                if (cryptoMonitor.getCoinType() == subscription.getCryptCoinType()
                        && cryptoMonitor.getSellingRate() == subscription.getMaxResult()) {
                    result.get(subscription.getUser()).append("Selling min rate has reached the result: " + cryptoMonitor.getSellingRate() + " on " + cryptoMonitor.getExchange() + " market.");
                }
            }
        }
        return result;
    }
}