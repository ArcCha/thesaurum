package pl.edu.uj.operations;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Application;
import pl.edu.uj.dao.ApplicationDao;
import pl.edu.uj.views.forms.ApplicationForm;

@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "Create application")
@FontAwesomeIcon(FontAwesome.PLUS)
public class CreateApplication implements Runnable {
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public void run() {
        ApplicationForm applicationForm = new ApplicationForm();
        Application application = new Application();
        applicationForm.setEntity(application);
        applicationForm.setSavedHandler(app -> {
            applicationDao.insert(app);
            applicationForm.closePopup();
        });

        applicationForm.openInModalPopup();
    }
}
