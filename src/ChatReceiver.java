import java.lang.Thread;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 클리이언트의 요청을 받는 역할을 맡는다.
 */
public class ChatReceiver extends Thread {
	/**
	 * 클라이언트로부터 서버로 데이터가 전송되는 스트림이다.
	 */
	private ObjectInputStream inputStream;

	/**
	 * Message 객체 임시저장소이다.
	 */
	private ConcurrentLinkedQueue<Message> messageBuffer = new ConcurrentLinkedQueue<Message>();

	/**
	 * 이 값이 true이면 run()은 스스로 종료한다.
	 */
	private boolean killFlag = false;

	public ChatReceiver(InputStream inputStream) throws IOException {
		this.inputStream = new ObjectInputStream(inputStream);
	}

	/**
	 * 이 메소드는 무한루프를 돌며 inputStream에서 받은 Message를 messageBuffer에 저장한다.
	 * killFlag 또한 확인한다.
	 */
	public void run() {
		while(true){
			Message message = (Message)inputStream.readObject();
			this.messageBuffer.add(message);
			if(!killFlag){
				break;
			}
		}
	}

	public void kill() {
		this.killFlag = true;
	}

	public Message getMessage() {
		if (this.messageBuffer.isEmpty()) {
			return null;
		}

		return this.messageBuffer.poll();
	}

}
