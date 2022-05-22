import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 사용자의 이름변경 요청을 서버로 보낸다.
 */
public class NameSender extends SenderSubThread {
	/**
	 * ChatSession으로부터 온 닉네임을 저장한다.
	 */
	private ConcurrentLinkedQueue<String> ingoingBuffer = new ConcurrentLinkedQueue<String>();

	public NameSender(ReentrantLock lock, OutputStream outputStream) {
		super(lock, outputStream);
	}

	/**
	 * 이 메소드는 무한루프를 돌며 ingoingBuffer를 체크한다.
	 * 만약 내용이 있다면 하나 꺼내서 SenderSubThread의 write()를 이용하여 데이터를 보낸다.
	 * 데이터 내용은 프로토콜 명세에 기술되어 있다.
	 * killFlag를 체크하는 것을 잊지 말자.
	 */
	public void run() {
		while(!killFlag){
			if(!ingoingBuffer.isEmpty()){
				byte[] stringData = {0x01, (byte)((CharSequence) ingoingBuffer).length()};
				write(stringData);
				write(ingoingBuffer.poll().getBytes());
			}
		}
	}

	/**
	 * ingoingBuffer에 받은 이름을 추가한다.
	 */
	public void appendName(String message) {
		// TODO
	}
}
