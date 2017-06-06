package hu.mik.MZ8AEX.Messenger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hu.mik.MZ8AEX.Chat.Message.Message;

import com.vaadin.shared.Registration;

public final class ChatService {
	public static final int MAX_LOG_SIZE = 50;

	interface MessageListener {
		void messageReceived(Message message);
	}

	List<Message> messageLog = new CopyOnWriteArrayList<>();
	List<MessageListener> listeners = new CopyOnWriteArrayList<>();

	public Registration addMessageListener(MessageListener e) {
		listeners.add(e);
		return () -> listeners.remove(e);
	}

	public void addMessage(Message message) {
		messageLog.add(message);
		for (MessageListener messageListener : listeners) {
			messageListener.messageReceived(message);
		}
		while (messageLog.size() > MAX_LOG_SIZE) {
			messageLog.remove(0);
		}
	}

	public List<Message> getMessageLog() {
		return messageLog;
	}
	
}