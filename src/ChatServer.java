/**
 * 서버 프로그램을 실행하는 클래스이다.
 * args의 값은 서버의 포트 값이다.
 * 무한루프를 돌며 클라이언트와 연결을 생상하고 poolManager에게 맡긴다.
 */
public class ChatServer {
	public static void main(String[] args) {
		HandlerPoolManager poolManager = new HandlerPoolManager();
		poolManager.start();
		
		// TODO
	}

}
