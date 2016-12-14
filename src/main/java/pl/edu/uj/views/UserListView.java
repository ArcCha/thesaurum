package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.backend.UserManagement;
import pl.edu.uj.bo.User;
import pl.edu.uj.service.UserService;

@Secured("ROLE_ADMIN")
@SpringView(name = "userList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "User List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class UserListView extends CustomComponent implements View {

    private final UserManagement userManagement;
    private final UserService userService;

    @Autowired
    public UserListView(UserManagement userManagement, UserService userService) {
        this.userManagement = userManagement;
        this.userService = userService;
        init();
    }

    private void init() {
        Table table = new Table("User List");

        table.addContainerProperty("Username", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Surname", String.class, null);
        table.addContainerProperty("Email", String.class, null);
        table.addContainerProperty("Enable", Button.class, null);

        int i = 1;
        for (User user : userService.fetchAllUsers()) {
            String enablePrompt = "Enable";
            String disablePrompt = "Disable";
            Button toggle = new Button(user.getEnabled() ? disablePrompt : enablePrompt);
            toggle.addStyleName(user.getEnabled() ? ValoTheme.BUTTON_DANGER : ValoTheme.BUTTON_FRIENDLY);
            toggle.setData(user);
            Button.ClickListener enableListener = event -> {
                User innerUser = (User)event.getButton().getData();
                userManagement.enable(innerUser);
                Page.getCurrent().reload();
            };
            Button.ClickListener disableListener = event -> {
                User innerUser = (User)event.getButton().getData();
                userManagement.disable(innerUser);
                Page.getCurrent().reload();

            };
            toggle.addClickListener(user.getEnabled() ? disableListener : enableListener);

            table.addItem(new Object[]{
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                toggle
            }, i);
            ++i;
        }
        table.setPageLength(table.size());

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.addComponent(table);
        rootLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
