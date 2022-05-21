import javax.swing.*;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 클라이언트의 채팅 세션을 보여주는 GUI이다.
 */
public class ChatFrame extends JFrame implements Runnable {
	/**
	 * kill() 메소드가 실행되면 true로 세팅된다.
	 * run() 메소드는 이를 수시로 확인하여 스스로 종료해야 한다.
	 */
	private boolean killFlag = false;

	/**
	 * 사용자가 채팅을 치면, 이 큐에 메시지가 누적된다.
	 * sendButton의 핸들러가 메시지를 추가하며,
	 * ChatSession 스레드가 getMessage()를 통하여 가져간다.
	 */
	private ConcurrentLinkedQueue<String> outgoingBuffer = new ConcurrentLinkedQueue<String>();

	/**
	 * 사용자들의 채팅들을 담는 리스트이다.
	 * ChatSession이 호출한 appendMessage()를 통하여 관리된다.
	 * 이 멤버변수는 스레드 안전을 고려할 필요가 없다.
	 * appendMessage()에서만 사용되기 때문이다.
	 */
	private ArrayList<Message> messageList = new ArrayList<Message>();

	/**
	 * 채팅 내용을 보여주는 text area이다.
	 */
	private JTextArea textArea = new JTextArea();


	/**
	 * 사용자가 채팅을 입력할 수 있는 필드이다.
	 */
	private JTextField textField = new JTextField();

	/**
	 * 메시지를 전송하는 버튼이다.
	 * 핸들러는 메시지를 outgoingBuffer에 추가한다.
	 * 보낸 메시지는 messageList에 반영하지 않으며,
	 * 서버에서 다시 돌려줄 때 까지 기다린다.
	 */
	private JButton sendButton = new JButton();

	public void run() {
		this.buildFrame();
	}

	public void kill() {
		this.killFlag = true;
	}

	/**
	 * ChatSession이 이 메소드를 통하여 사용자가 입력한 메시지를 읽는다.
	 * 만약 작성된 메시지가 없으면 null을 리턴한다.
	 */
	public String getMessage() {
		// TODO
		return null;
	}

	/**
	 * 서버로부터 받은 메시지를 추가하고 textArea에 반영한다.
	 */
	public void appendMessage(Message message) {
		// TODO
	}

	/**
	 * GUI를 만든다.
	 */
	private void buildFrame() {
		// TODO
	}
}
