package faceRecognition;

import org.opencv.core.Mat;
import org.opencv.face.FaceRecognizer;


public class FaceRecognition {

  Mat testImage;
  FacesTraining trainedRecognitionModel;

  public FaceRecognition(String trainingDir) {
    this.trainedRecognitionModel = new FacesTraining(trainingDir);
  }

  public FaceRecognizer getFaceRecognizer() {
    return this.trainedRecognitionModel.faceRecognizer;
  }


  public int predict(Mat testImage) {
    int[] label = new int[1];
    double[] confidence = new double[1];
    this.trainedRecognitionModel.faceRecognizer.predict(testImage, label, confidence);
    System.out.println(label[0]);
    System.out.println(confidence[0]);
    return label[0];
  }



}
