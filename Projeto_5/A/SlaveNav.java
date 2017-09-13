import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import lejos.robotics.RegulatedMotor; //maybe unnecessary
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;

public class SlaveNav {
	private static final byte ADD_POINT = 0; //adds waypoint to path
	private static final byte TRAVEL_PATH = 1; // enables slave to execute the path
	private static final byte STATUS = 2; // enquires about slave's position 
	private static final byte SET_START = 3; // set initial waypoint
	private static final byte STOP = 4; // closes communication

	public static void main(String[] args) throws Exception {
		// USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();

		DifferentialPilot p = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B); // (wheel diameter, dist between wheels, left motor, right motor )
		OdometryPoseProvider position = new OdometryPoseProvider (p);
    	Navigator nav = new Navigator(p,position);
    	Pose init = new Pose(0f, 0f, 0f);

		LCD.drawString("READY", 0, 10);
		while (true) {
			try {
				byte cmd = dis.readByte();
				LCD.drawInt(cmd,1,0,0);
				float addX = dis.readFloat();
				float addY = dis.readFloat();
				
				switch (cmd) {
				case ADD_POINT: 
					nav.addWaypoint(addX,addY); //adds a waypoint to path queue
					dos.writeFloat(0);
					break;
				case TRAVEL_PATH: 
					nav.followPath(); //initiates a path through waypoints
					dos.writeFloat(0);
					break;
				case STATUS:
					dos.writeBoolean(nav.pathCompleted()); // Returns true if the the final waypoint has been reached
					break;	
				case SET_START:
					init = new Pose(addX,addY,0f);
					position.setPose(init);
					dos.writeFloat(0);
					break;
				case STOP:
					System.exit(1);
				default:
					dos.writeFloat(-1);
				}
				dos.flush();
				
			} catch (IOException ioe) {
				System.err.println("IO Exception");
				Thread.sleep(2000);
				System.exit(1);
			}
		}
	}
}
