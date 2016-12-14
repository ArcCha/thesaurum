package pl.edu.uj.operations;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.backend.MyBackend;
import pl.edu.uj.bo.User;
import pl.edu.uj.service.UserService;

@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "Admin operation", order = 1)
@FontAwesomeIcon(FontAwesome.WRENCH)
public class AdminOperation implements Runnable {

    private final MyBackend backend;
    @Autowired
    private UserService userService;

    @Autowired
    public AdminOperation(MyBackend backend) {
        this.backend = backend;
    }

    @Override
    public void run() {
        User user = new User();
        user.setName("TEST");
        user.setSurname("NAJPRAWDZIWSZY");
        userService.add(user);
        Notification.show(backend.adminOnlyEcho("Hello Admin World"));
    }
}
