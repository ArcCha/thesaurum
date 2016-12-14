package pl.edu.uj;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessagesProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@EnableVaadinManagedSecurity
@EnableJpaRepositories
@EnableTransactionManagement
public class ThesaurumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThesaurumApplication.class, args);
	}

	// make sure app is reloaded when session expires
	@Bean
	SystemMessagesProvider systemMessagesProvider() {
		return (SystemMessagesProvider) systemMessagesInfo -> {
            CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
            systemMessages.setSessionExpiredNotificationEnabled(false);
            return systemMessages;
        };
	}

	// authentication manager config
	@Configuration
	static class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("user").password("user").roles("USER")
					.and()
					.withUser("admin").password("admin").roles("ADMIN");
		}
	}
}
