package faceRecognition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FacesTraining {

  String trainingDir;
  Mat trainingImages;
  List<Mat> trainingFaces;

  // FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
  FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
  // FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

  public FacesTraining(String trainingDir) {
    this.trainingDir = trainingDir;
    try {
      this.train(trainingDir, "model");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // this.train();
  }

  public void train(String imageFolder, String saveFolder) throws IOException {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    CascadeClassifier faceCascade = new CascadeClassifier();
    faceCascade.load("/usr/share/OpenCV/haarcascades/haarcascade_frontalface_alt.xml");
    File[] files = new File(imageFolder).listFiles();
    Map<String, Integer> nameMapId = new HashMap<String, Integer>(10);
    List<Mat> images = new ArrayList<Mat>(files.length);
    List<String> names = new ArrayList<String>(files.length);
    List<Integer> ids = new ArrayList<Integer>(files.length);
    for (int index = 0; index < files.length; index++) {
      File file = files[index];
      String name = file.getName().split("\\_")[0];
      Integer id = nameMapId.get(name);
      if (id == null) {
        id = names.size();
        names.add(name);
        nameMapId.put(name, id);
        faceRecognizer.setLabelInfo(id, name);
        System.out.println("added ID  " + name + "\t" + id);
      }

      Mat mat = Imgcodecs.imread(file.getCanonicalPath());
      Mat gray = new Mat();

      // convert the frame in gray scale
      Imgproc.cvtColor(resize(mat), gray, Imgproc.COLOR_BGR2GRAY);
      images.add(gray);
      System.out.println("add total " + images.size() + "\t" + name + "\t" + id);
      ids.add(id);
    }
    int[] idsInt = new int[ids.size()];
    for (int i = 0; i < idsInt.length; i++) {
      idsInt[i] = ids.get(i).intValue();
    }
    MatOfInt labels = new MatOfInt(idsInt);

    faceRecognizer.train(images, labels);
    faceRecognizer.save(saveFolder + "/face_model.yml");
  }


  private Mat resize(Mat source) {
    Mat resizeimage = new Mat();
    Size sz = new Size(640, 480);
    Imgproc.resize(source, resizeimage, sz);
    return resizeimage;
  }


  // private void getImages() {
  //
  // FilenameFilter imgFilter = new FilenameFilter() {
  // public boolean accept(File dir, String name) {
  // name = name.toLowerCase();
  // return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
  // }
  // };
  //
  // File root = new File(trainingDir);
  // this.trainingFaces = new ArrayList<Mat>();
  //
  // File[] imageFiles = root.listFiles(imgFilter);
  // this.trainingImages = new Mat(imageFiles.length, 1, CvType.CV_32SC1);
  //
  // int counter = 0;
  // for (File image : imageFiles) {
  // Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
  // int label = Integer.parseInt(image.getName().split("\\_")[0]);
  //
  // this.trainingFaces.add(counter, resize(img));
  // this.trainingImages.put(counter, 0, label);
  // System.out.println("Trained image " + label + image.getName());
  // counter++;
  // }
  // }


}
