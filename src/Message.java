import java.io.Serializable;

/**
 * 채팅 프로그램에서 사용되는 메시지이다.
 * type은 메시지의 타입을 나타내며, 항상 null이 아니다.
 * 그 이외에는 클라이언트가 서버로 보내는 경우는 하나만 null이 아니다.
 * 서버가 클라이언트로 보내는 경우는 name은 항상 null이 아니고, 그 이외중 하나만 null이 아니다.
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

	/**
	 * 클라이언트가 보낸 이미지이다.
	 */
	public SerializableImage image;
}
