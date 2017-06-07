package hu.mik.MZ8AEX.Messenger;


import org.springframework.stereotype.Repository;

import com.vaadin.annotations.Push;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

import hu.mik.MZ8AEX.Chat.Message.Message;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
@Repository
@Push
public class ChatView extends ChatUI {
	
	private static ChatService chatService = new ChatService();
	//private ChatView chatView;
	private Registration registration;
	//private String Username;
	
	//public void setName(String name) {
	//	this.Username = name;
	//}

	public ChatView(String Username) {
		lblNev.setValue("You are logged in as "+Username);
		txtMessage.setPlaceholder("Message");
			
		for (Message message : chatService.getMessageLog()) {
			appendMessage(message);
		}

		registration = chatService.addMessageListener(message -> {
			getUI().access(() -> {
				appendMessage(message);
			});
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
		
		


		addShortcutListener(new ShortcutListener("Send", KeyCode.ENTER, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				btnSend.click();
			}
		});
	}

	public void appendMessage(Message message) {
		String messages = txtMessenger.getValue();
		txtMessenger.setValue(messages+"\n"+message.getName()+"\t\t"+message.getMessage());
	}
}