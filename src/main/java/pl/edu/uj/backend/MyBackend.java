package pl.edu.uj.backend;

import org.springframework.security.access.prepost.PreAuthorize;

public interface MyBackend {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    String adminOnlyEcho(String s);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    String echo(String s);
}
