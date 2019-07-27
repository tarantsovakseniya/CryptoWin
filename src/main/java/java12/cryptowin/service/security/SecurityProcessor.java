package java12.cryptowin.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class SecurityProcessor {

    public User getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null) {
            return null;
        }

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        if (token == null) {
            return null;
        }

        User user = (User) token.getPrincipal();

        if (user == null) {
            return null;
        }

        return user;
    }

    public String getCurrentUserEmail() {
        User user = getUser();
        return user == null ? null : user.getUsername();
    }
}
