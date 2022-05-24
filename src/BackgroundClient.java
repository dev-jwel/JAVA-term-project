import java.lang.Thread;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 서버와의 통신을 처리하는 스레드이다.
 */
public class BackgroundClient extends Thread {
	/**
	 * 클리이언트 메인스레드이다.
	 */
	private ChatClient chatClient;

	/**
	 * Message를 대신 받아주는 스레드이다.
	 */
	private ChatReceiver receiver;

	/**
	 * 서버부터 클라이언트로 데이터가 전송되는 스트림이다.
	 */
	private ObjectOutputStream outputStream;

	/**
	 * Message 객체 임시저장소이다.
	 */
	private ConcurrentLinkedQueue<Message> messageBuffer = new ConcurrentLinkedQueue<Message>();

	/**
	 * 이 멤버변수는 가장 최근에 데이터를 전송한 시간을 기록한다.
	 * 클라이언트의 timeout을 막기 위해 사용된다.
	 */
	private int recentlySentTime;

	/**
	 * 이 멤버변수는 가장 최근에 받은 데이터의 수신 시간을 기록한다.
	 * timeout을 확인하기 위해 사용한다.
	 */
	private int recentlyReceivedTime;

	public BackgroundClient(Socket server, ChatClient chatClient) throws IOException {
		this.outputStream = new ObjectOutputStream(server.getOutputStream());
		this.receiver = new ChatReceiver(server.getInputStream());
		this.receiver.start();
	}

	/**
	 * 이 메소드는 무한루프를 돌며 아래 작업들을 한다.
	 * 1. timeout을 체크하고 문제가 있으면 receiver를 죽이고 이 스레드도 죽는다.
	 * 2. recentlySentTime이 너무 오래되었으면 ALIVE Message를 보낸다.
	 * 3. messageBuffer에 Message가 있으면 클라이언트에 보낸다.
	 * 4. receiver로부터 Message를 하나 얻어오고 null이 아니면 ChatClient의 GUI를 lock과 함께 조작한다.
	 */
	public void run() {
		// TODO
	}
}