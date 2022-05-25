import java.net.Socket;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 이 클래스는 클라이언트의 채팅 세션을 보여주는 GUI이다.
 */
public class ChatClient extends JFrame {
	/**
	 * swing이 스레드안전이 보장되지 않아 사용하는 lock이다.
	 */
	private ReentrantLock lock = new ReentrantLock();

	/**
	 * 서버와의 통신을 처리하는 스레드이다.
	 */
	private BackgroundClient backgroundClient;

	/**
	 * 사용자들의 채팅들을 담는 리스트이다.
	 * ChatSession이 호출한 appendMessage()를 통하여 관리된다.
	 * 이 멤버변수는 스레드 안전을 고려할 필요가 없다.
	 * appendMessage()에서만 사용되기 때문이다.
	 */
	private ArrayList<String> textList = new ArrayList<String>();

	/**
	 * 채팅 내용을 보여주는 text area이다.
	 */
	private JTextArea textArea = new JTextArea();

	/**
	 * 사용자가 이름을 입력할 수 있는 필드이다.
	 */
	private JTextField nameField = new JTextField();

	/**
	 * 사용자가 채팅을 입력할 수 있는 필드이다.
	 */
	private JTextField textField = new JTextField();

	/**
	 * 이름 변경을 요청하는 버튼이다.
	 */
	private JButton nameChangeButton = new JButton();

	/**
	 * 메시지를 전송하는 버튼이다.
	 * 핸들러는 메시지를 outgoingBuffer에 추가한다.
	 * 보낸 메시지는 messageList에 반영하지 않으며,
	 * 서버에서 다시 돌려줄 때 까지 기다린다.
	 */
	private JButton sendButton = new JButton();

	/**
	 * args의 첫번째 값은 서버의 주소이고 두번째 값은 포트 번호이다.
	 * 이를 이용해 ChatClient 객체를 생성한다.
	 */
	public static void main(String[] args) {
		// TODO
	}

	/**
	 * 이곳에서 GUI생성, BackgroundClient 스레드 생성, 리스너 등록 등 모든 초기화를 처리한다.
	 */
	public ChatClient(Socket server) {
		// TODO
	}

	public void lock() {
		this.lock.lock();
	}

	public void unlock() {
		this.lock.unlock();
	}
}
