package videoCapture;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Camera implements Runnable {

  VideoCapture capture = new VideoCapture(0);
  Mat frame = new Mat();
  JFrame jFame = new JFrame();
  ImageIcon imageFrame = new ImageIcon();


  public Camera() {
    this.createWindow();
  }

  public VideoCapture getVideoCapture() {
    return this.capture;
  }

  private Mat createFaceRecognitionFrame() {
    for (Rect faces : getFaceLocations()) {
      Imgproc.rectangle(frame, new Point(faces.x, faces.y),
          new Point(faces.x + faces.width, faces.y + faces.height), new Scalar(0, 255, 0));
    }
    return frame;
  }

  public Mat getImageFromFaces() {
    Rect[] face = getFaceLocations();
    if (face.length != 0) {
      Mat mGray = new Mat();
      Imgproc.cvtColor(frame.submat(face[0]), mGray, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
      return mGray;
    } else {
      return null;
    }

  }


  public Rect[] getFaceLocations() {
    CascadeClassifier faceDetector = new CascadeClassifier();
    faceDetector.load("haarcascade_frontalface_alt.xml");

    // Detecting faces
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(frame, faceDetections);

    // Creating a rectangular box showing faces detected
    return faceDetections.toArray();
  }

  public Mat getFrame() {
    return this.frame;
  }

  private BufferedImage getImage() {
    Imgproc.resize(frame, frame, new Size(640, 480));
    MatOfByte matOfByte = new MatOfByte();
    Imgcodecs.imencode(".jpg", frame, matOfByte);
    byte[] byteArray = matOfByte.toArray();
    BufferedImage bufImage = null;
    InputStream in = new ByteArrayInputStream(byteArray);
    try {
      bufImage = ImageIO.read(in);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return bufImage;
  }

  private void update() {
    capture.read(frame);
    this.createFaceRecognitionFrame();
    imageFrame.setImage(getImage());
    this.jFame.repaint();
  }

  private void createWindow() {
    this.update();
    jFame.getContentPane().add(new JLabel(imageFrame));
    jFame.pack();
    jFame.setVisible(true);
  }

  @Override
  public void run() {
    while (true) {
      this.update();
    }
  }
}
