import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 사용자가 작성한 메시지를 서버로 보낸다.
 */
public class MessageSender extends SenderSubThread {
	/**
	 * ChatSession으로부터 온 메시지를 저장한다.
	 */
	private ConcurrentLinkedQueue<String> ingoingBuffer;

	public MessageSender(ReentrantLock lock, OutputStream outputStream) {
		super(lock, outputStream);
	}

	/**
	 * 이 메소드는 무한루프를 돌며 ingoingBuffer를 체크한다.
	 * 만약 내용이 있다면 하나 꺼내서 SenderSubThread의 write()를 이용하여 데이터를 보낸다.
	 * 데이터 내용은 프로토콜 명세에 기술되어 있다.
	 * killFlag를 체크하는 것을 잊지 말자.
	 */
	public void run() {
		// TODO
	}

	/**
	 * ingoingBuffer에 받은 메시지를 추가한다.
	 */
	public void sendMessage(String message) {
		// TODO
	}
}
