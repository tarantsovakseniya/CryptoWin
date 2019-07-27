package java12.cryptowin.listener;

import java12.cryptowin.entity.*;
import java12.cryptowin.service.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInitListener {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Bean
    public void onAppStarter(){
        validateRoles();
        validateUser();
    }

    private void validateRoles(){
        if(roleService.getRoleByName("USER")==null){
            Role role=new Role();
            role.setRole("USER");
            role.setDescription("Regular user");
            roleService.save(role);
        }
        if(roleService.getRoleByName("ADMIN")==null){
            Role role=new Role();
            role.setRole("ADMIN");
            role.setDescription("Administrator role");
            roleService.save(role);
        }
    }

    private void validateUser(){
        if(userService.getByEmail("admin")==null){
            User user = new User();
            user.setEmail("admin");
            user.setPassword("admin");
            user.setName("Admin");
            user.setLastName("Admin");
            user.getRoles().add(roleService.getRoleByName("ADMIN"));

            userService.addNewUser(user);
        }
    }

}
