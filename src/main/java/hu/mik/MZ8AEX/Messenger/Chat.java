package hu.mik.MZ8AEX.Messenger;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import hu.mik.MZ8AEX.Chat.Message.Message;
//import hu.mik.MZ8AEX.Messenger.MessengerApplication.Register;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@Theme("mytheme")
@Push
public class Chat extends UI {

	private static ChatService chatService = new ChatService();
	private ChatView chatView;
	private Registration registration;

	String Username="TheAndras";

	
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		chatView = new ChatView(Username);
		chatView.txtMessage.focus();

		chatView.txtMessage.addValueChangeListener(e -> {

			boolean nameValid = chatView.txtMessage.getValue().length() > 2;
			chatView.txtMessage.setEnabled(nameValid);
		});

		for (Message message : chatService.getMessageLog()) {
			chatView.renderMessage(message);
		}

		registration = chatService.addMessageListener(message -> {
			chatView.getUI().access(() -> {
				chatView.renderMessage(message);
			});
		});

		chatView.btnSend.addClickListener(e -> {
			chatService.addMessage(new Message(Username, chatView.txtMessage.getValue()));
			chatView.txtMessage.setValue("");
			chatView.txtMessage.focus();

		});

		chatView.txtMessage.addValueChangeListener(e -> {
			boolean bool = chatView.txtMessage.getValue().length() > 2;
			chatView.btnSend.setEnabled(bool);
			chatView.txtMessage.setValueChangeTimeout(0);
		});

		addShortcutListener(new ShortcutListener("Send", KeyCode.ENTER, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				chatView.btnSend.click();
			}
		});

		setContent(chatView);
	}

	@Override
	public void detach() {
		super.detach();

		registration.remove();
	}

	@WebServlet(urlPatterns = "/*", name = "ChatUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = Chat.class, productionMode = true)
	public static class ChatUIServlet extends VaadinServlet {

	}
}
