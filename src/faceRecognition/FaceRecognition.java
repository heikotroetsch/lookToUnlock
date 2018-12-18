package faceRecognition;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class FaceRecognition {

  Mat testImage;
  FacesTraining trainedRecognitionModel;

  public FaceRecognition(String trainingDir, String identifyPath) {
    this.trainedRecognitionModel = new FacesTraining(trainingDir, identifyPath);
    this.testImage = Imgcodecs.imread(identifyPath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
  }



  public int predict(Mat testImage) {
    int[] label = new int[1];
    double[] confidence = new double[1];
    this.trainedRecognitionModel.faceRecognizer.predict(testImage, label, confidence);
    return label[0];
  }



}
