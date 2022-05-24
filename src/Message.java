import java.io.Serializable;

/**
 * 클라이언트간의 메시지이다.
 */
public class Message implements Serializable {
	/**
	* 메시지 타입이다.
	*/
	public MessageType type;

	/**
	* 클라이언트의 닉네임이다.
	*/
	public String name;

	/**
	* 클라이언트의 메세지이다.
	*/
	public String message;
}
