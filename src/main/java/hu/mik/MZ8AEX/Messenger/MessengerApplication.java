package hu.mik.MZ8AEX.Messenger;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.mik.MZ8AEX.Chat.Message.Message;

@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}
	
	
	@SuppressWarnings("serial")
	@Theme("mytheme")
	@SpringUI
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
	public static class Login extends LoginUI {
			
		@Autowired
		Chat chat;
		@Autowired
		Register register;
		
		@PostConstruct
		public void init() {
			btnLogin.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(txtUsername!=null){
						chat.setName(txtUsername.getValue());
						MyUI.getCurrent().setContent(chat);					
						
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
	public static class Chat extends ChatUI {
		
		private static ChatService chatService = new ChatService();
		private ChatView chatView;
		private Registration registration;
		
		String Username="TheAndras";
		
		public void setName(String name) {
			this.Username = name;
		}
		
		@PostConstruct
		public void init() {
			chatView = new ChatView(Username);


			for (Message message : chatService.getMessageLog()) {
				chatView.renderMessage(message);
			}

			registration = chatService.addMessageListener(message -> {
				chatView.getUI().access(() -> {
					chatView.renderMessage(message);
				});
			});

			
			chatView.btnSend.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					chatService.addMessage(new Message(Username, chatView.txtMessage.getValue()));
					chatView.txtMessage.setValue("");
					chatView.txtMessage.focus();				
				}
			});

			chatView.txtMessage.addValueChangeListener(new ValueChangeListener<String>() {
				public void valueChange(ValueChangeEvent<String> event) {
					boolean msgValid = chatView.txtMessage.getValue().length() > 1;
					chatView.btnSend.setEnabled(msgValid);
					chatView.txtMessage.setValueChangeTimeout(0);
				}
			});
			
			


			addShortcutListener(new ShortcutListener("Send", KeyCode.ENTER, null) {

				@Override
				public void handleAction(Object sender, Object target) {
					chatView.btnSend.click();
				}
			});

		}
		
	}
	
	@SuppressWarnings("serial")
	@SpringComponent
	@UIScope
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