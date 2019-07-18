package java12.cryptowin.controller;

import com.google.gson.Gson;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
@RequestMapping("/userProfile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getProfile(){
        ModelAndView view = new ModelAndView("security/user-profile");
        view.addObject("user", userService.getCurrentUser());
        return view;

    }

}
