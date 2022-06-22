public class Utils {
	static void logMessage(String prefix, Message message) {
		String debugText = prefix + " ";
		switch (message.type) {
			case ALIVE: debugText += "ALIVE"; break;
			case CHANGENAME: debugText += "CHANGENAME " + message.name; break;
			case SENDTEXT: debugText += "SENDTEXT " + message.message; break;
			case SENDIMAGE: debugText += "SENDIMAGE"; break;
			case SENDMESSAGE: debugText += "SENDMESSAGE"; break;
		}
		System.out.println(debugText);

	}
}
