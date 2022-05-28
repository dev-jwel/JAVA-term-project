import java.net.Socket;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 이 클래스는 클라이언트의 채팅 세션을 보여주는 GUI이다.
 * 채팅 내용은 스크롤이 가능해야 하는 동시에 텍스트와 이미지를 모두 보여주어야 한다.
 * 따라서 이들을 GridBagLayout의 JPanel에 넣고 이 Panel을 JScrollPane에 넣음으로써 해결한다.
 * 버튼들에 등록되어야 하는 리스너들은 이 클래스의 필드에서 내용을 읽어 backgroundClient를 통하여
 * 메시지를 보내야 한다.
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
	 * 다음 메시지를 담을 GridBagLayout의 인덱스를 가리킨다.
	 */
	private int layoutIndex = 0;

	/**
	 * 채팅 내용을 보여주는 영역이다.
	 */
	private JPanel chatPanel;

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
	 * 텍스트 메시지를 전송하는 버튼이다.
	 * 핸들러는 메시지를 outgoingBuffer에 추가한다.
	 * 보낸 메시지는 messageList에 반영하지 않으며,
	 * 서버에서 다시 돌려줄 때 까지 기다린다.
	 */
	private JButton textSendButton = new JButton();

	/**
	 * 이미지 메시지를 보내는 기능을 갖는 버튼이다.
	 * swing의 파일 선택 기능을 이용하여 이미지 파일을 얻은 후, 이의 내용을 서버로 보내야 한다.
	 */
	private JButton imageSendButton = new JButton();

	/**
	 * args의 첫번째 값은 서버의 주소이고 두번째 값은 포트 번호이다.
	 * 이를 이용해 ChatClient 객체를 생성한다.
	 */
	public static void main(String[] args) {
		// TODO
	}

	/**
	 * 이곳에서 GUI생성, BackgroundClient 스레드 생성, 리스너 등록 등 모든 초기화를 처리한다.
	 * panel은 GridBagLayout이며, JScrollPane에 들어있어야 한다.
	 * panel에서 텍스트와 이미지의 gridheight는 각각 1, 5이다.
	 */
	public ChatClient(Socket server) {
		// TODO
	}

	/**
	 * BackgroundClient에서 호출되며, 메시지를 화면에 누적시켜 보이게 한다.
	 */
	public void appendMessage(Message message) {
		// TODO
	}
}
