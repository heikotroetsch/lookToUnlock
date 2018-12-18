package application;

import org.opencv.core.Core;
import videoCapture.Camera;

public class Main {

  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Camera c = new Camera();
  }
}
