import java.net.Socket;
import java.lang.Thread;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 클라이언트 핸들러들을 관리하는 pool이다.
 */
public class HandlerPoolManager extends Thread {
	/**
	 * 클라이언트 핸들러들을 담고있는 풀이다.
	 * 이 스레드와 ChatServer 스레드가 동시에 접근하므로
	 * 동시성 관리가 필요하다.
	 */
	private CopyOnWriteArrayList<ChatHander> handlerPool = new CopyOnWriteArrayList<ChatHander>();

	/**
	 * 무한루프를 돌며 pool에서 outgoingBuffer의 메시지를
	 * 모든 pool의 ChatHander에게 보내준다.
	 * 메시지를 받아온 ChatHander에게도 보낸다.
	 * isAlive()로 상태를 체크하고 pool에서 제거하는 책임도 가진다.
	 */
	public void run() {
		// TODO
	}

	/**
	 * ChatServer에서 호출하는 메소드이다.
	 * 받아온 소켓으로 ChatHander 스레드를 새로이 생성하고 pool에 넣어 관리한다.
	 */
	public void addClient(Socket client) {
		ChatHander ChatHanderT = new ChatHander(client);
		handlerPool.add(ChatHanderT);
	}
}
