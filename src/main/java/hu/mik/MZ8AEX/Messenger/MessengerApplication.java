package hu.mik.MZ8AEX.Messenger;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.mik.MZ8AEX.Chat.Message.Message;

@Configuration
@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}
	
	
	@SuppressWarnings("serial")
	@Theme("mytheme")
	@SpringUI
	@Repository
	public static class MyUI extends UI {
		
		@Autowired
		Login login;
		
		@Override
		protected void init(VaadinRequest request) {
			setContent(login);
		}
		
	}
	
	@SuppressWarnings("serial")
	@SpringComponent
	@UIScope
	@Repository
	public static class Login extends LoginUI {
			
		@Autowired
		Register register;
		
		@PostConstruct
		public void init() {
			btnLogin.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(txtUsername!=null){
						ChatView chatView = new ChatView(txtUsername.getValue());
						MyUI.getCurrent().setContent(chatView);	
						
						
					}
				}
			});
			
			btnRegister.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
							
						MyUI.getCurrent().setContent(register);					
						
					
				}
			});
		}		
	}
	
	
	@SuppressWarnings("serial")
	@SpringComponent
	@UIScope
	@Repository
	public static class Register extends RegisterUI {
		
		//@Autowired
		//Login login;
		
		@PostConstruct
		public void init() {
			btnCancel.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
							
						//MyUI.getCurrent().setContent(login);					
						
					
				}
			});
		}
		
	}
}