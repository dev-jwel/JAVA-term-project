import java.lang.Thread;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.*;

/**
 * 채팅 클라이언트의 메인 클래스이다.
 * 이 클래스는 GUI로 사용자로부터 서버 정보를 입력받고 서버와 연결된 새 세션을 스레드로 생성한다.
 */
public class ChatClient {
	/**
	 * 사용자 닉네임을 받는 필드이다.
	 */
	private JTextField nameField = new JTextField();

	/**
	 * 서버 주소를 받는 필드이다.
	 */
	private JTextField hostField = new JTextField();

	/**
	 * 서버 포트 주소를 받는 필드이다.
	 */
	private JTextField portField = new JTextField();

	/**
	 * 연결 버튼이며, 새 세션을 생성하는 스레드를 생성한는 핸들러가 붙어있다.
	 */
	private JButton connectButton = new JButton();

	public static void main(String[] args) {
		new ChatClient();
	}

	/**
	 * 새로운 채팅 서버에 참여할 수 있는 GUI를 생성한다.
	 * 여기에서 GUI 구조가 정의되고 button hander가 등록된다.
	 * 새로운 ChatSession 스레드를 생성하되, 저장하지는 않는다.
	 */
	public ChatClient() {
		// TODO
	}
}
