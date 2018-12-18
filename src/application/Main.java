package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import faceRecognition.FaceRecognition;
import videoCapture.Camera;

public class Main {

  private static Camera camera;
  private static FaceRecognition faceRecognition;

  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    // creating Camera object and running camera thread
    camera = new Camera();
    Thread cameraThread = new Thread(camera);
    cameraThread.start();

    faceRecognition = new FaceRecognition("trainingImages");

    System.out.println("Identifying face");
    System.out.println(faceRecognition.getFaceRecognizer()
        .getLabelInfo(faceRecognition.predict(resize(converGrayScale(camera.getFrame())))));
    System.out.println("Done");
  }



  private static Mat resize(Mat source) {
    Mat resizeimage = new Mat();
    Size sz = new Size(640, 480);
    Imgproc.resize(source, resizeimage, sz);
    return resizeimage;
  }


  public static Mat converGrayScale(Mat source) {
    Mat mGray = new Mat();
    Imgproc.cvtColor(source, mGray, Imgproc.COLOR_BGR2GRAY);
    return mGray;
  }
}
