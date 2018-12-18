# lookToUnlock | A secure smart home door lock

A triple factor authentication door lock based on Java, openCV face recognition, BlueTooth proximity and WiFi running on a Rasperry Pi. 

Goal of the tool is to design an effortless door entrance module for the smart home. The software recognizes faces usind a door bell camera and in return unlocks the door. To secure the entrance further the software is installed on a respberry pi which scans for the proximity of the Bluetooth MAC adresses. As another fallback the devices logged into the WiFi are also tracked. Only if the mobile device is locaed in the proximity of the door, logged into the WiFi and both match the face recognized the door will open.

Opening the door is done by using a standard actuator that is connected to home automation. The triggering of the door is then done by connecting to the smart home API.

## Getting Started

The project is based on Java. The project can be cloned and imported in eclipse.

```
git clone https://github.com/seife96/lookToUnlock
```

Then go to eclipse -> import -> from directory -> select the desired project -> finish

### Prerequisites

What things you need to install

```
Eclipse
Java JDK
Git
OpenCV Library
```

## Built With

* [Java](https://docs.oracle.com/javase/7/docs/api/) - Java framework
* [OpenCV](https://opencv.org/) - OpenCV Library

## Authors

* **Heiko Troetsch** - *Initial work* - [seife96](https://github.com/seife96)
