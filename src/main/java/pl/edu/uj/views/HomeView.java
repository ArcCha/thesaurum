package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.managed.VaadinManagedSecurity;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Application;
import pl.edu.uj.bo.User;
import pl.edu.uj.dao.ApplicationDao;
import pl.edu.uj.dao.UserDao;
import pl.edu.uj.views.forms.ApplicationForm;

import java.util.Set;

@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends CustomComponent implements View {
    private VaadinManagedSecurity vaadinSecurity;
    private UserDao userDao;
    private ApplicationDao applicationDao;

    @Autowired
    public HomeView(VaadinManagedSecurity vaadinSecurity, UserDao userDao, ApplicationDao applicationDao) {
        this.vaadinSecurity = vaadinSecurity;
        this.userDao = userDao;
        this.applicationDao = applicationDao;

        MLabel header = new MLabel("Welcome to Thesaurum!")
                .withStyleName(ValoTheme.LABEL_H1);

        MLabel body = new MLabel("<p>This application is being developed currently</p>")
                .withContentMode(ContentMode.HTML);

        String currentUsername = vaadinSecurity.getAuthentication().getName();
        User currentUser = userDao.findByUsername(currentUsername);
        Set<Application> applications = currentUser.getApplications();

        MTable<Application> applicationTable = new MTable<>(applications)
                .withProperties("name", "beginDate", "endDate", "state")
                .withColumnHeaders("Name", "Begin", "End", "State");
        applicationTable.setPageLength(applicationTable.size());
        applicationTable.setCaption("Your applications");

        applicationTable.addRowClickListener(event -> {
            if (event.isDoubleClick()) {
                ApplicationForm form = new ApplicationForm();
                form.setEntity(event.getRow());
                form.setSavedHandler(application -> {
                    applicationDao.update(application);
                    applicationTable.removeAllItems();
                    applicationTable.addItems(currentUser.getApplications());
                    form.closePopup();
                });
                form.openInModalPopup();
            }
        });

        MVerticalLayout root = new MVerticalLayout()
                .withSpacing(true)
                .withMargin(true)
                .with(header, body, applicationTable);
        setCompositionRoot(root);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
