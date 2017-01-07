package pl.edu.uj;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.sidebar.components.ValoSideBar;
import org.vaadin.spring.sidebar.security.VaadinSecurityItemFilter;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.navigator.MNavigator;
import pl.edu.uj.views.AccessDeniedView;
import pl.edu.uj.views.ErrorView;

@UIScope
@SpringComponent
public class MainScreen extends CustomComponent {

    @Autowired
    public MainScreen(final VaadinSecurity vaadinSecurity,
                      SpringViewProvider springViewProvider,
                      ValoSideBar sideBar) {
        // By adding a security item filter, only views that are accessible
        // to the user will show up in the side bar.
        sideBar.setItemFilter(new VaadinSecurityItemFilter(vaadinSecurity));

        MCssLayout viewContainer = new MCssLayout()
                .withSize(MSize.FULL_SIZE);

        MHorizontalLayout layout = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(sideBar, viewContainer)
                .withExpand(viewContainer, 1f);
        setCompositionRoot(layout);
        setSizeFull();

        MNavigator navigator = new MNavigator(UI.getCurrent(), viewContainer);

        // Without an AccessDeniedView, the view provider would act like the
        // restricted views did not exist at all.
        springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        navigator.addProvider(springViewProvider);
        navigator.setErrorView(ErrorView.class);
        navigator.navigateTo(navigator.getState());
    }

}
