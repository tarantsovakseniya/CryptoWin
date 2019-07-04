package java12.cryptowin.listener;


import java12.cryptowin.entity.Role;
import java12.cryptowin.entity.User;
import java12.cryptowin.service.jpa.RoleService;
import java12.cryptowin.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserInitListener {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @EventListener(ApplicationReadyEvent.class)
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
