import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;

/**
 * 직렬화가 가능한 Image 클래스이다.
 * 이와 같은 직렬화 가능한 객체를 스트림으로 주고받을때 Exception을 잘 확인하자.
 */
public class SerializableImage extends Image implements Serializable {
	private transient BufferedImage bufferedImage;

	public SerializableImage(Image image) {
		this.loadImage(image);
	}

	public int getWidth(ImageObserver observer) {
		return this.bufferedImage.getWidth(observer);
	}

	public int getHeight(ImageObserver observer) {
		return this.bufferedImage.getHeight(observer);
	}

	public Graphics getGraphics() {
		return this.bufferedImage.getGraphics();
	}

	public ImageProducer getSource() {
		return this.bufferedImage.getSource();
	}

	public Object getProperty(String name, ImageObserver observer) {
		return this.bufferedImage.getProperty(name, observer);
	}

	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}

	private void loadImage(Image image) {
		if (image instanceof BufferedImage) {
			this.bufferedImage = (BufferedImage)image;
			return;
		}

		this.bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = this.bufferedImage.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.loadImage(ImageIO.read(in));
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		ImageIO.write(this.bufferedImage, "png", out);
	}
}
