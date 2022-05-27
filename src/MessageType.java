/**
 * 이 enum은 Message의 타입을 알려준다.
 */
public enum MessageType {
	/**
	 * timeout을 막기 위해 보내는 메시지이다.
	 * 이 타입으로 Message를 보낼 때, Message의 type을 제외한 모든 멤버는 null이다.
	 */
	ALIVE,

	/**
	 * 클라이언트가 요청한 이름 변경이다.
	 * 이 타입으로 Message를 보낼 때, Message의 type과 name을 제외한 모든 멤버는 null이다.
	 */
	CHANGENAME,

	/**
	 * 클라이언트가 요청한 텍스트 전송이다.
	 * 이 타입으로 Message를 보낼 때, Message의 type과 text을 제외한 모든 멤버는 null이다.
	 */
	SENDTEXT,

	/**
	 * 클라이언트가 요청한 이미지 전송이다.
	 * 이 타입으로 Message를 보낼 때, Message의 type과 image를 제외한 모든 멤버는 null이다.
	 */
	SENDIMAGE,

	/**
	 * 서버가 보내온 메세지이다.
	 * 이 타입으로 Message를 보냃 때, Message의 type과 name은 null이 아니고, 그 이외의 멤버들 중에서 하나만 null이 아니다.
	 * 현재는 text만 고려한다.
	 */
	SENDMESSAGE,
}
