package pl.edu.uj.views;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.User;
import pl.edu.uj.service.UserService;

@Secured("ROLE_ADMIN")
@SpringView(name = "userList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "User List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class UserListView extends CustomComponent implements View {

    private final UserService userService;

    @Autowired
    public UserListView(UserService userService) {
        this.userService = userService;
        init();
    }

    private void init() {
        Table table = new Table("User List");
        BeanItemContainer<User> itemContainer = new BeanItemContainer<>(User.class);
        itemContainer.addAll(userService.fetchAll());
        table.setContainerDataSource(itemContainer);
        table.setPageLength(table.size());
        table.setEditable(true);
        table.setVisibleColumns("name", "surname", "username", "email", "enabled");

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
