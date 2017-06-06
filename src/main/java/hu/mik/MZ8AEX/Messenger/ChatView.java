package hu.mik.MZ8AEX.Messenger;

import hu.mik.MZ8AEX.Chat.Message.Message;

@SuppressWarnings("serial")
public class ChatView extends ChatUI {
	//private boolean evenRow = false;

	public ChatView(String Username) {
		lblNev.setValue("You are logged in as "+Username);
		txtMessage.setPlaceholder("Message");
	}

	public void renderMessage(Message message) {
		txtMessenger.setValue("\n"+message.getName()+"\t"+message.getMessage());
	}
}