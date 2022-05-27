import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * 직렬화가 가능한 Image 클래스이다.
 * 이와 같은 직렬화 가능한 객체를 스트림으로 주고받을때 Exception을 잘 확인하자.
 */
public class SerializableImage extends BufferedImage implements Serializable {
	public SerializableImage(Image image) {
		super(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = this.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		SerializableImage image = new SerializableImage(ImageIO.read(in));
		WritableRaster raster = image.getRaster();
		this.setData(raster);
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		ImageIO.write(this, "png", out);
	}
}
