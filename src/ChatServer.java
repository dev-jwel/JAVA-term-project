import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버 프로그램을 실행하는 클래스이다.
 * args의 값은 서버의 포트 값이다.
 * 무한루프를 돌며 클라이언트와 연결을 생상하고 poolManager에게 맡긴다.
 */
public class ChatServer {
	public static void main(String[] args) {
		HandlerPoolManager poolManager = new HandlerPoolManager();
		poolManager.start();

		int Port = Integer.parseInt(args[0]);
		try {
			ServerSocket serverSock = new ServerSocket(Port);
			while(true){
				Socket socket = serverSock.accept();
				System.out.println("[ChatServer.main] new connection " + socket.getInetAddress() + ":" + socket.getPort());
				poolManager.addClient(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

}
