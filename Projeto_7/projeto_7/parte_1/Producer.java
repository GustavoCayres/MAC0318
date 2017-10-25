import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Motor;
import lejos.util.Delay;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import java.util.Scanner;
import java.io.*;
import java.lang.Math;


class Producer extends Thread {
  static DifferentialPilot pilot;
  static UltrasonicSensor sonar;

  private static final byte TRAVEL = 0; 
  private static final byte ROTATE = 1;
  private static final byte FULL_SCAN = 2; 
  private static final byte SINGLE_SCAN = 3; 
  private static final byte EXIT = 4;

  private Buffer buffer;
  private Buffer original;
  private Float[] pose;

  public Producer(Buffer b) {
      buffer = b;
      original = b;
  }

  public void reinitialize(){
      buffer = original;
  }

  public void update_pose(float distance, float angle) {
      if (angle < 0) {
        angle += 360;
      }
      pose[2] = (pose[2] + angle)%360;
      pose[0] += distance * (float)Math.cos(Math.toRadians(pose[2]));
      pose[1] += distance * (float)Math.sin(Math.toRadians(pose[2]));
  }

  public void run() {
      BufferedWriter writer = null;
      try {
        writer = new BufferedWriter(new FileWriter("robot_data.txt"));}
      catch (IOException e) {
        e.printStackTrace();
      }

      Delay.msDelay(2000);
      Scanner scan = new Scanner ( System.in );
      byte cmd = 0; int param = 0;
      pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.A);
      sonar = new UltrasonicSensor(SensorPort.S1);
      pilot.setTravelSpeed(10);
      pilot.setRotateSpeed(40);

      pose = new Float[3];
      pose[0] = 0f;
      pose[1] = 0f;
      pose[2] = 0f;

      while(true) {
          System.out.print("Enter command [0:TRAVEL 1:ROTATE 2:FULL_SCAN 3:SINGLE_SCAN 4:EXIT]: ");
          cmd = (byte) scan.nextFloat(); 
          if (cmd == 0 || cmd == 1){
              System.out.println("Enter integer parameter: ");
              param = scan.nextInt();
          }
          switch (cmd) {
              case TRAVEL: 
                  pilot.travel(param);
                  update_pose(param, 0);
                  break;
              case ROTATE: 
                  pilot.rotate(param);
                  update_pose(0, param);
                  break;
              case FULL_SCAN:
                  try {
                    fullscan(writer);
                  }
                  catch (IOException e) {
                    e.printStackTrace();
                  }
                  break;  
              case SINGLE_SCAN:
                  int val = sonar.getDistance();
                  System.out.println("Sonar Single Scan(cm): "+ val);
                  break;
              case EXIT:
                  System.out.println("close");
                  try {writer.close();} catch (IOException e) {e.printStackTrace();}
                  System.exit(0);
              default:
                  System.out.println("default");
          }
      }
  }

  public void fullscan(BufferedWriter writer) throws IOException{
      int val = 0;
      int i=0;
      double ang = 0;
      Motor.C.setSpeed(200);
      Motor.C.rotate(450);
      Motor.C.setSpeed(100);
      writer.write("" + pose[0] + " " + pose[1] + " " + pose[2] + " ");
      for (int j=0; j <= 900; j+=10){
          Motor.C.rotate(-10);
          val = sonar.getDistance();
          ang = j/900.0 * 180.0;
          this.buffer.put((int)ang,val);
          writer.write("" + val + " ");
      }
      writer.write("\n");
      
      Motor.C.setSpeed(200);
      Motor.C.rotate(460);
      reinitialize();
    }

}
