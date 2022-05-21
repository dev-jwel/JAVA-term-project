import java.util.ArrayList;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 클리이언트의 요청을 처리하는 역할을 맡는다.
 */
public class ChatHander extends SenderSubThread {
	/**
	 * 클리이언트의 닉네임이다.
	 */
	private String clientName = "";

	/**
	 * 클라이언트로부터 오는 InputStream이다.
	 */
	private InputStream inputStream;

	/**
	 * inputStream으로부터 값을 저장하고 있는 임시버퍼이다.
	 * 이 멤버번수는 이 스레드에서만 사용되므로 스레드안정성의 고려대상이 아니다.
	 */
	private ArrayList<Byte> rawBuffer = new ArrayList<Byte>();

	/**
	 * ChatHander로부터 HandlerPoolManager로 가는 버퍼이다.
	 */
	private ConcurrentLinkedQueue<Message> ingoingBuffer = new ConcurrentLinkedQueue<Message>();

	/**
	 * HandlerPoolManager로부터 ChatHander로 가는 버퍼이다.
	 */
	private ConcurrentLinkedQueue<Message> outgoingBuffer = new ConcurrentLinkedQueue<Message>();

	/**
	 * alive 시그널을 보내주는 객체이다.
	 * timeout이 걸리면 kill()을 호출하자.
	 */
	private AliveSignalSender aliveSignalSender;

	/**
	 * 이 멤버변수는 가장 최근에 받은 Alive 시그널의 수신 시간을 기록한다.
	 * timeout을 확인하기 위해 사용한다.
	 */
	private int recentlyRecievedAliveSignalTime;

	public ChatHander(InputStream inputStream, OutputStream outputStream) {
		super(outputStream);
		this.inputStream = inputStream;
		this.aliveSignalSender = new AliveSignalSender(this.lock, outputStream, true);
	}

	/**
	 * 이 메소드는 무한루프를 돌며 inputStream으로부터 클라이언트가 보낸 데이터를 읽는다.
	 * 우선 rawBuffer에 기록하며, rawBuffer의 내용이 프로토콜 명세에 부합하게 완전히 도착하면
	 * 데이터를 처리한다.
	 * alive 시그널은 recentlyRecievedAliveSignalTime을 업데이트하고,
	 * 클리이언트의 메시지는 outgoingBuffer에 추가한다.
	 * ingoingBuffer 역시 체크하며, 만약 값이 있다면 super 클래스의 write()를 이용하여 처리한다.
	 * timeout을 체크하는 것을 잊지 말자.
	 */
	public void run() {
		// TODO
	}

	/**
	 * 이 메소드는 HandlerPoolManager에서 호출된다.
	 * outgoingBuffer로부터 메시지를 하나 꺼내온다.
	 * 값이 없으면 null을 리턴한다.
	 ㅓ*/
	public Message getMessage() {
		// TODO
		return null;
	}

	/**
	 * 이 메소드는 HandlerPoolManager에서 호출된다.
	 * ingoingBuffer에 메시지를 하나 채운다.
	 */
	public void sendMessage(Message message) {
		// TODO
	}
}
