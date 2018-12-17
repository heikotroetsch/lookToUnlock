package videoCapture;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
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

public class Camera {

  VideoCapture capture = new VideoCapture(0);
  Mat frame = new Mat();
  JFrame jFame = new JFrame();
  ImageIcon imageFrame = new ImageIcon();
  
  public Camera(){
    this.createWindow();
    while(true) {
      this.update();
    }
  }

  public VideoCapture getVideoCapture() {
    return this.capture;
  }
  
  public Mat faceDetection() {
    CascadeClassifier faceDetector = new CascadeClassifier(); 
    faceDetector.load("haarcascade_frontalface_alt.xml"); 
    
 // Detecting faces 
    MatOfRect faceDetections = new MatOfRect(); 
    faceDetector.detectMultiScale(frame, faceDetections); 

    // Creating a rectangular box showing faces detected 
    for (Rect rect : faceDetections.toArray()) 
    { 
        Imgproc.rectangle(frame, new Point(rect.x, rect.y), 
         new Point(rect.x + rect.width, rect.y + rect.height), 
                                       new Scalar(0, 255, 0)); 
    } 

    return frame;
  }

  
  public BufferedImage getImage(Mat img) {
    Imgproc.resize(img, img, new Size(640, 480));
    MatOfByte matOfByte = new MatOfByte();
    Imgcodecs.imencode(".jpg", img, matOfByte);
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
  
  public void update() {
    capture.read(frame);
    this.faceDetection();
    imageFrame.setImage(getImage(frame));
    this.jFame.repaint();
  }
  
  public void createWindow() {
    this.update();
    jFame.getContentPane().add(new JLabel(imageFrame));
    jFame.pack();
    jFame.setVisible(true);
    }
}
