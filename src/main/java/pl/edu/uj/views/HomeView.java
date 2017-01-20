package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;

@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {
    public HomeView() {
        setSpacing(true);
        setMargin(true);

        Label header = new Label("Welcome to Thesaurum!");
        header.addStyleName(ValoTheme.LABEL_H1);
        addComponent(header);

        Label body = new Label("<p>This application is being developed currently</p>");
        body.setContentMode(ContentMode.HTML);
        addComponent(body);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
