package pl.edu.uj.operations;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.managed.VaadinManagedSecurity;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Application;
import pl.edu.uj.bo.User;
import pl.edu.uj.dao.ApplicationDao;
import pl.edu.uj.dao.UserDao;
import pl.edu.uj.views.forms.ApplicationForm;

@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "Create application")
@FontAwesomeIcon(FontAwesome.PLUS)
public class CreateApplication implements Runnable {
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private VaadinManagedSecurity vaadinSecurity;

    @Override
    public void run() {
        String currentUsername = vaadinSecurity.getAuthentication().getName();
        User currentUser = userDao.findByUsername(currentUsername).get();
        ApplicationForm applicationForm = new ApplicationForm();
        Application application = new Application();
        applicationForm.setEntity(application);
        applicationForm.setSavedHandler(app -> {
            app.setOwner(currentUser);
            currentUser.getApplications().add(app);
            applicationDao.insert(app);
            applicationForm.closePopup();
        });

        applicationForm.openInModalPopup();
    }
}
