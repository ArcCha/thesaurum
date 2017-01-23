package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.User;
import pl.edu.uj.dao.UserDao;
import pl.edu.uj.views.forms.UserForm;

//@Secured("ADMIN")
@SpringView(name = "userList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "User List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class UserListView extends CustomComponent implements View {

    private final UserDao userDao;

    @Autowired
    public UserListView(UserDao userDao) {
        this.userDao = userDao;
        init();
    }

    private void init() {
        MTable<User> table = new MTable<>(userDao.getAll())
                .withProperties("name", "surname", "username", "email", "enabled")
                .withColumnHeaders("Name", "Surname", "Username", "Email", "Enabled");
        table.setPageLength(table.size());

        table.addRowClickListener(event -> {
            if (event.isDoubleClick()) {
                UserForm form = new UserForm();
                form.setEntity(event.getRow());
                form.setSavedHandler(user -> {
                    userDao.update(user);
                    table.removeAllItems();
                    table.addItems(userDao.getAll());
                    form.closePopup();
                });
                form.openInModalPopup();
            }
        });

        MVerticalLayout rootLayout = new MVerticalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(table)
                .withAlign(table, Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
