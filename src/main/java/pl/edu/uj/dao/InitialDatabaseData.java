package pl.edu.uj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDatabaseData {
    @Autowired
    RoleDao roleDao;
    @Autowired
    UserDao userDao;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void initialize() {
        roleDao.ensureRolesExist();
        userDao.ensureAdminExists();
    }
}
