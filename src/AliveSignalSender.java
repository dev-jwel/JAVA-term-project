import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 이 클래스는 주기적으로 상대 호스트에게 alive 시그널을 보낸다.
 */
public class AliveSignalSender extends SenderSubThread {
	/**
	 * 이 클래스를 이용하는 곳이 서버인지 여부를 나타낸다.
	 * 이 값에 따라 alive 시그널 데이터가 달라진다.
	 */
	private boolean isServer;
	
	public AliveSignalSender(ReentrantLock lock, OutputStream outputStream, boolean isServer) {
		super(lock, outputStream);
		this.isServer = isServer;
	}

	/**
	 * 이 메소드는 무한루프를 돌며 alive 시그널을 보낼 때가 되었는지 체크한다.
	 * 만약 보낼 시간이 되었다면 SenderSubThread의 write()를 이용하여 데이터를 보낸다.
	 * 데이터 내용은 프로토콜 명세에 기술되어 있다.
	 * killFlag를 체크하는 것을 잊지 말자.
	 */
	public void run() {
		// TODO
	}
}
