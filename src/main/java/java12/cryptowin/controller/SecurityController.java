package java12.cryptowin.controller;

import java12.cryptowin.entity.User;
import java12.cryptowin.service.jpa.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView result = new ModelAndView("security/login");
        return result;
    }

    @GetMapping("/register")
    public ModelAndView getRegister(@RequestParam(value = "error", required = false) RegisterError error) {
        ModelAndView result = new ModelAndView("security/register");
        if (error != null) {
            result.addObject("error", error.description);
        }
        return result;
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam("email") String email,
                               @RequestParam("name") String name,
                               @RequestParam("lastname") String lastname,
                               @RequestParam("password") String password) {
        if(!isValidEmail(email)){
            return "redirect:/register?error="+RegisterError.invalidEmail.name();
        }
        if(name.isEmpty() || lastname.isEmpty()){
            return "redirect:/register?error="+RegisterError.invalidNameOrLastname.name();
        }

        List<User> users = userService.getAllUsers();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return "redirect:/register?error=" + RegisterError.invalidData.name();
            }
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastname);
        user.setPassword(password);

        userService.addNewUser(user);

        return "redirect:/register?error="+RegisterError.success.name();
    }

    enum RegisterError {
        success ("Вы успешно зарегистрированы"),
        invalidEmail("Неправильный email"),
        invalidNameOrLastname("Вы не ввели имя или фамилию"),
        invalidData("Пользователь с таким e-mail уже существует");

        String description;

        RegisterError(String description) {
            this.description = description;
        }
    }

    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}

