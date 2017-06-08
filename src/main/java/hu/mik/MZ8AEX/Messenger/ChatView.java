package hu.mik.MZ8AEX.Messenger;

import javax.servlet.annotation.WebServlet;

import org.springframework.stereotype.Repository;

import com.couchbase.client.core.config.refresher.Refresher;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import hu.mik.MZ8AEX.Chat.Message.Message;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
@Repository
@Widgetset("com.example.pushvaadinapp.MyAppWidgetset")
@Push
public class ChatView extends ChatUI {

	private static ChatService chatService = new ChatService();
	private Registration registration;

	@SuppressWarnings({ "deprecation", "static-access" })
	public ChatView(String Username) {
		//new RefreshThread().start();
		lblNev.setValue("You are logged in as " + Username);
		txtMessage.setPlaceholder("Message");

		updateMessage();

		registration = chatService.addMessageListener(message -> {
			try {
				getUI().access(() -> {
					updateMessage();
				});
			} catch (Exception e) {
				//Notification notification = new Notification("Error");
				//notification.show("Error", "Something happened", Notification.TYPE_ERROR_MESSAGE);
			}
		});

		btnSend.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				chatService.addMessage(new Message(Username, txtMessage.getValue()));
				txtMessage.setValue("");
				txtMessage.focus();
			}
		});

		txtMessage.addValueChangeListener(new ValueChangeListener<String>() {
			public void valueChange(ValueChangeEvent<String> event) {
				boolean msgValid = txtMessage.getValue().length() > 1;
				btnSend.setEnabled(msgValid);
				txtMessage.setValueChangeTimeout(0);
			}
		});

		txtMessage.addShortcutListener(new ShortcutListener("Send", KeyCode.ENTER, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				btnSend.click();
			}
		});
	}

	public void updateMessage() {
		String messages = "";
		boolean FirstRow=true;
		for (Message message : chatService.getMessageLog()) {
			if(FirstRow){
				messages +=message.getName() + "\t\t" + message.getMessage();
				FirstRow = false;
			}else{
			messages += "\n" + message.getName() + "\t\t" + message.getMessage();
			}
		}
		txtMessenger.setValue(messages);

	}
	
	/*public class RefreshThread extends Thread{

	    boolean run = true;

		@Override
		public void run() {
			try {
				 //Update the data for a while
				while (run) {
					Thread.sleep(1000);
					getUI().access(new Runnable() {
						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							updateMessage();
						}
					});
				}

			} catch (InterruptedException e) {
				 run = false;
			}
		}
	}*/

}
