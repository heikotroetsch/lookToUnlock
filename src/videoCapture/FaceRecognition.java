package videoCapture;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;


public class FaceRecognition {

  String trainingDir;
  String idetifyPath;
  Mat testImage;
  Mat trainingImages;

  FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
  // FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
  // FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

  public FaceRecognition(String trainingDir, String identifyPath) {
    this.trainingDir = trainingDir;
    this.idetifyPath = identifyPath;
    this.testImage = Imgcodecs.imread(this.idetifyPath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
    this.train();
  }


  private void train() {
    faceRecognizer.train(getImages(), trainingImages);
  }

  public int predict(Mat testImage) {
    int[] label = new int[1];
    double[] confidence = new double[1];
    faceRecognizer.predict(testImage, label, confidence);
    return label[0];
  }


  private List<Mat> getImages() {


    File root = new File(trainingDir);

    FilenameFilter imgFilter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
        name = name.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
      }
    };

    File[] imageFiles = root.listFiles(imgFilter);

    List<Mat> images = new ArrayList<Mat>();
    List<Integer> labelsBuf = new ArrayList<Integer>();

    this.trainingImages = new Mat(imageFiles.length, 1, CvType.CV_32SC1);
    int counter = 0;

    for (File image : imageFiles) {
      Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

      int label = Integer.parseInt(image.getName().split("\\-")[0]);

      images.add(counter, img);

      labelsBuf.add(counter, label);
      counter++;
    }
    return images;
  }
}
