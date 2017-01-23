package pl.edu.uj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Role;
import pl.edu.uj.dao.UserDao;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ThesaurumUserDetailsService implements UserDetailsService {
    private static final Logger log = Logger.getLogger(ThesaurumUserDetailsService.class.getSimpleName());

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<pl.edu.uj.bo.User> user = userDao.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.get().getRoles());
        return buildUserForAuthentication(user.get(), authorities);
    }

    private User buildUserForAuthentication(pl.edu.uj.bo.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(
            user.getUsername(),
            user.getPassword(),
            user.getEnabled(),
            true,
            true,
            true,
            authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> roles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (Role role : roles) {
            setAuths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(setAuths);
    }
}
