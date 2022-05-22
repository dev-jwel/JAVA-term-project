import java.lang.Thread;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 이 클래스는 Lock과 OutputStream으로 상대 호스트와 통신하는 스레드이다.
 * 실제로 데이터를 보내는 클래스들이 이를 상속하여 구현된다.
 */
public class SenderSubThread extends Thread {
	/**
	 * kill() 메소드가 실행되면 true로 세팅된다.
	 * run() 메소드는 이를 수시로 확인하여 스스로 종료해야 한다.
	 */
	protected boolean killFlag = false;

	/**
	 * outputStream에 안전한 접근을 보장하는 lock이다.
	 */
	protected ReentrantLock lock;

	/**
	 * 상대 호스트측의 소켓의 OutputStream이다.
	 */
	private OutputStream outputStream;

	public SenderSubThread(OutputStream outputStream) {
		this(new ReentrantLock(), outputStream);
	}

	public SenderSubThread(ReentrantLock lock, OutputStream outputStream) {
		this.lock = lock;
		this.outputStream = outputStream;
	}

	public void kill() {
		this.killFlag = true;
	}

	/**
	 * 락을 사용하여 입력받은 데이터를 outputStream에 적는다.
	 */
	protected void write(byte[] b) {
		lock.lock();
		try{
			outputStream.write(b);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			lock.unlock();
		}
	}
}
