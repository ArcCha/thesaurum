package pl.edu.uj.backend;

import org.springframework.security.access.prepost.PreAuthorize;
import pl.edu.uj.bo.User;

public interface UserManagement {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void enable(User user);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void disable(User user);
}
