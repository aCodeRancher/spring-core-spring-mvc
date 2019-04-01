package guru.springframework.services.security;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
@Component
public class LoginSuccessEventHandler implements ApplicationListener<LoginSuccessEvent> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {

        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent Success for: " + authentication.getPrincipal());
        updateUserAccount(authentication);
    }

    public void updateUserAccount(Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());

        if (user != null) { //user found
            user.setFailedLoginAttempts(0);
            user.setEnabled(true);
            }
            System.out.println("Login success");
            userService.saveOrUpdate(user);
        }

}
