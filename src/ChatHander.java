import java.lang.Thread;
import java.util.ArrayList;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 클리이언트의 요청을 처리하는 역할을 맡는다.
 */
public class ChatHander extends Thread {
	/**
	 * 클리이언트의 닉네임이다.
	 */
	private String clientName = "";

	/**
	 * Message를 대신 받아주는 스레드이다.
	 */
	private ChatReceiver receiver;

	/**
	 * 클라이언트로부터 서버로 데이터가 전송되는 스트림이다.
	 */
	private ObjectOutputStream outputStream;

	/**
	 * ChatHander로부터 HandlerPoolManager로 가는 버퍼이다.
	 */
	private ConcurrentLinkedQueue<Message> ingoingBuffer = new ConcurrentLinkedQueue<Message>();

	/**
	 * HandlerPoolManager로부터 ChatHander로 가는 버퍼이다.
	 */
	private ConcurrentLinkedQueue<Message> outgoingBuffer = new ConcurrentLinkedQueue<Message>();

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

	public ChatHander(Socket client) throws IOException {
		this.outputStream = new ObjectOutputStream(client.getOutputStream());
		this.receiver = new ChatReceiver(client.getInputStream());
		this.receiver.start();
	}

	/**
	 * 이 메소드는 무한루프를 돌며 아래 작업들을 한다.
	 * 1. timeout을 체크하고 문제가 있으면 receiver를 죽이고 이 스레드도 죽는다.
	 * 2. recentlySentTime이 너무 오래되었으면 ALIVE Message를 보낸다.
	 * 3. ingoingBuffer에 Message가 있으면 클라이언트에 보낸다.
	 * 4. receiver가 받은 Message가 있으면 outgoingBuffer에 넣는다.
	 */
		public void run() { 
		int sendTimeout = 50; //전송 시 timeout을 위한 변수
		int receiveTimeout = 100; // 수신 시 timeout을 위한 변수
		int currentTime = 0; //현재시간 측정을 위한 변수
		
		while(true){
			Message ingoingBufferMessage = ingoingBuffer.poll(); //ingoingBuffer에서 Message를 꺼내 ingoingBufferMessage에 저장
			Message receiverMessage = receiver.getMessage(); //receiver에서 받은 Message를 receiverMessage에 저장
			
			//1
			if(receiverMessage == null){ //수신 받은 Message가 없을때(즉, 상대가 응답이 없을 때)
				if(recentlySentTime+sendTimeout == currentTime){
					receiver.interrupt();
					break;
				}
				if(recentlyReceivedTime+receiveTimeout == currentTime){
					receiver.interrupt();
					break;
				}
			}
	
			//2		
			if(recentlySentTime+30 == currentTime){ //recentlySentTime 30time이 지났으면(recentlySentTime 오래되었으면) 실행
				Object object = (Object)MessageType.ALIVE;
				try {
					outputStream.writeObject(object);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//3
			if(ingoingBufferMessage != null){ 
				Object object = (Object)ingoingBufferMessage;
				try {
					recentlySentTime = currentTime;
					outputStream.writeObject(object);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//4
			if(receiverMessage != null){ 
				recentlyReceivedTime = currentTime;
				outgoingBuffer.offer(receiverMessage);
			}
			
			currentTime += 1;
		}
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
		ingoingBuffer.offer(message);
	}

}
