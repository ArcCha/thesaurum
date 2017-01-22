package pl.edu.uj.dao;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class InitialDatabaseData {
    @Inject
    RoleDao roleDao;
    @Inject
    UserDao userDao;

    @EventListener(ContextRefreshedEvent.class)
    public void initialize() {
        roleDao.ensureRolesExist();
        userDao.ensureAdminExists();
    }
}
