package pl.edu.uj.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.bo.User;
import pl.edu.uj.service.UserService;

@Service
public class UserManagementBean implements UserManagement {

    private UserService userService;

    @Autowired
    public UserManagementBean(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void enable(User user) {
        userService.enable(user);
    }

    @Override
    public void disable(User user) {
        userService.disable(user);
    }
}
