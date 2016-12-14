package pl.edu.uj.operations;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.backend.MyBackend;

@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "User operation", order = 0)
@FontAwesomeIcon(FontAwesome.ANCHOR)
public class UserOperation implements Runnable {

    private final MyBackend backend;

    @Autowired
    public UserOperation(MyBackend backend) {
        this.backend = backend;
    }

    @Override
    public void run() {
        Notification.show(backend.echo("Hello World"));
    }
}
