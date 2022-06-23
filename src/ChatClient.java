import java.net.Socket;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	 * 채팅 내용을 스크롤하기 위해 필요한 JScrollPane이다.
	 */
	private JScrollPane Scroller;

	/**
	 * 이름 관련 컴포넌트들을 배치시킬 영역이다.
	 */
	private JPanel namePanel;

	/**
	 * 채팅 보내기 관련 컴포넌트들을 배치시킬 영역이다.
	 */
	private JPanel sendPanel;

	/**
	 * chatPanel에 채팅친 내용을 보이게 하기위한 라벨이다.
	 */
	private JLabel textBox;

	/**
	 * chatPanel에 사용자의 이름을 보이게 하기위한 라벨이다.
	 */
	private JLabel nameBox;

	/**
	 * 사용자가 이름을 입력할 수 있는 필드이다.
	 */
	private JTextField nameField = new JTextField(10);

	/**
	 * 사용자가 채팅을 입력할 수 있는 필드이다.
	 */
	private JTextField textField = new JTextField(30);

	/**
	 * 이름 변경을 요청하는 버튼이다.
	 */
	private JButton nameChangeButton = new JButton("이름 변겅");

	/**
	 * 텍스트 메시지를 전송하는 버튼이다.
	 * 핸들러는 메시지를 outgoingBuffer에 추가한다.
	 * 보낸 메시지는 messageList에 반영하지 않으며,
	 * 서버에서 다시 돌려줄 때 까지 기다린다.
	 */
	private JButton textSendButton = new JButton("전송");

	/**
	 * 이미지 메시지를 보내는 기능을 갖는 버튼이다.
	 * swing의 파일 선택 기능을 이용하여 이미지 파일을 얻은 후, 이의 내용을 서버로 보내야 한다.
	 */
	private JButton imageSendButton = new JButton();

	private GridBagLayout gbl = new GridBagLayout();

	private GridBagConstraints gbc = new GridBagConstraints();

	private JFrame mainFrame;

	/**
	 * args의 첫번째 값은 서버의 주소이고 두번째 값은 포트 번호이다.
	 * 이를 이용해 ChatClient 객체를 생성한다.
	 */
	public static void main(String[] args) {
		Socket server = null;
		String URL = args[0];
		int Port = Integer.parseInt(args[1]);
		try {
			server = new Socket(URL, Port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ChatClient chatClient = new ChatClient(server);
	}

	/**
	 * 이곳에서 GUI생성, BackgroundClient 스레드 생성, 리스너 등록 등 모든 초기화를 처리한다.
	 * panel은 GridBagLayout이며, JScrollPane에 들어있어야 한다.
	 * panel에서 텍스트와 이미지의 gridheight는 각각 1, 5이다.
	 */
	public ChatClient(Socket server) {
		mainFrame = this;
		setTitle("채팅 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		chatPanel = new JPanel();
		namePanel = new JPanel();
		sendPanel = new JPanel();
		Scroller = new JScrollPane(chatPanel);
		chatPanel.setLayout(gbl);
		namePanel.add(new JLabel("이름: "));
		namePanel.add(nameField);
		namePanel.add(nameChangeButton);
		sendPanel.add(textField);
		sendPanel.add(textSendButton);
		c.add(Scroller, BorderLayout.CENTER);
		c.add(namePanel, BorderLayout.NORTH);
		c.add(sendPanel, BorderLayout.SOUTH);
		gbc.fill= GridBagConstraints.NONE ; //남는 여백 채우지 않고 컴포넌트 그대로 두기
		gbc.gridwidth = 1;

		Message nameMessage = new Message();
		Message textMessage = new Message();
		nameChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("[ChatClient.nameChangeButton] clicked");
				lock.lock();
				nameMessage.type = MessageType.CHANGENAME;
				nameMessage.name = nameField.getText();
				System.out.println("[ChatClient.nameChangeButton] " + nameMessage.name);
				backgroundClient.sendMessage(nameMessage);
				nameField.setText(nameMessage.name);
				lock.unlock();
			}
		});

		textSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("[ChatClient.textChangeButton] clicked");
				lock.lock();
				textMessage.type = MessageType.SENDTEXT;
				textMessage.message = textField.getText();
				System.out.println("[ChatClient.textChangeButton] " + textMessage.message);
				backgroundClient.sendMessage(textMessage);
				textField.setText("");
				lock.unlock();
			}
		});

		//BackgroundClient 스레드 생성
		try {
			backgroundClient = new BackgroundClient(server, this);
			backgroundClient.start();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		setSize(600, 600);
		setVisible(true);
	}

	 public void add(Component c, int x, int y, int h, JPanel chatPanel) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridheight = h;
		gbl.setConstraints(c, gbc);
		chatPanel.add(c, gbc);
		SwingUtilities.updateComponentTreeUI(mainFrame);
		System.out.println("[ChatClient.add] number of message is " + chatPanel.getComponentCount());
	 }

	/**
	 * BackgroundClient에서 호출되며, 메시지를 화면에 누적시켜 보이게 한다.
	 */
	public void appendMessage(Message message) {
		if(message.type == MessageType.CHANGENAME){
			System.out.println("[ChatClient.appendMessage] CHANGENAME " + message.name);
			lock.lock();
			nameBox = new JLabel(message.name + ": ");
			lock.unlock();
		}
		if(message.type == MessageType.SENDTEXT){
			System.out.println("[ChatClient.appendMessage] SENDTEXT " + message.message);
			lock.lock();
			textBox = new JLabel(message.message);
			add(nameBox, 0, layoutIndex, 1, chatPanel);
			add(textBox, 1, layoutIndex, 1, chatPanel);
			layoutIndex++;
			lock.unlock();
		}
	}
}
