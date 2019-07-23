package java12.cryptowin.controller;

import java12.cryptowin.service.mailing.SubscriberMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Properties;

@RestController
public class EmailController {
    @Autowired
    private SubscriberMailSender subscriberMailSender;

    @RequestMapping(value = "/sendemail")
    public String sendEmail() {
        subscriberMailSender.sendEmail();
        return "Email sent successfully";
    }

}
