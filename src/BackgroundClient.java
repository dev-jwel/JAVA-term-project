import java.lang.Thread;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 서버와의 통신을 처리하는 스레드이다.
 * ChatReceiver 서브스레드를 주기적으로 확인하다가 받은 메시지가 있다면 GUI에 반영한다.
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
	 * 클라이언트가 서버로 메시지를 보내기 위해 사용되는 스트림이다.
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
	LocalDateTime recentlySentTime;

	/**
	 * 이 멤버변수는 가장 최근에 받은 데이터의 수신 시간을 기록한다.
	 * timeout을 확인하기 위해 사용한다.
	 */
	LocalDateTime recentlyReceivedTime;

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
	 * 4. receiver로부터 Message를 하나 얻어오고 null이 아니면 ChatClient에 appendMessage()를 통해 보낸다.
	 */
	public void run() { 
		Message MessageObject = new Message();
		int sendTimeout = 50; //전송 시 timeout을 위한 변수. 50second동안 기다린다.
		int receiveTimeout = 100; //수신 시 timeout을 위한 변수. 100second동안 기다린다.
		recentlySentTime = LocalDateTime.now();
		recentlyReceivedTime = LocalDateTime.now();
				
		while(true){
			Message messageBufferMessage = messageBuffer.poll(); //messageBuffer에서 Message를 꺼내 ingoingBufferMessage에 저장
			Message receiverMessage = receiver.getMessage(); //receiver에서 받은 Message를 receiverMessage에 저장
			LocalDateTime currentTime = LocalDateTime.now(); //현재 시간측정을 위한 변수
			Duration betweenSentCurrentSecond = Duration.between(recentlySentTime, currentTime);//recentlySentTime과 currentTime사이의 초 차이
			Duration betweenReceivedCurrentSecond = Duration.between(recentlyReceivedTime, currentTime);//recentlyReceivedTime과 currentTime사이의 초 차이
			
			//1
			if(betweenReceivedCurrentSecond.getSeconds() >= receiveTimeout){
				receiver.interrupt();
				break;
			}
			
			//2
			if(betweenSentCurrentSecond.getSeconds() >= sendTimeout){
				recentlySentTime = LocalDateTime.now();
				MessageObject.type = MessageType.ALIVE;
				Object object = (Object)MessageObject;
				try {
					outputStream.writeObject(object);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
			
			//3
			if(messageBufferMessage != null){ 
				recentlySentTime = LocalDateTime.now();
				Object object = (Object)messageBufferMessage;
				try {
					outputStream.writeObject(object);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
			
			//4
			if(receiverMessage != null){ 
				recentlyReceivedTime = LocalDateTime.now();
				chatClient.appendMessage(receiverMessage);
			}
		}
	}
	
	/**
	 * 이 메소드는 ChatClient의 리스너에서 호출된다.
	 * messageBuffer에 메시지를 하나 채운다.
	 */
	public void sendMessage(Message message) {
		messageBuffer.offer(message);
	}
}
