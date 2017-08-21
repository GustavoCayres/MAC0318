import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;
/*
 * Master: Sends commands to NXT Slave application
 */
public class AulaMaster {
	private static final byte FORWARD = 0;
	private static final byte STOP = 1;
	private static final byte EXIT = 2;
	
	private NXTComm nxtComm; //NXTComm - PC initiator to connect to NXT 
	private DataOutputStream dos; //output data
	private DataInputStream dis; // input data
	
	private static final String NXT_ID = "NXT07"; // NXT BRICK NAME
	/**
	 * Send command to the robot
	 * @param command specifies command
	 * @param param argument
	 * @return value returned by the robot (float)
	 */
	private float sendCommand(byte command) { // try sending data to brick and returns a float ouputted by Slave
		try {
			dos.writeByte(command); // send command
			dos.flush(); // flush the output stream to be sure data is transmitted
			return dis.readFloat(); // return float sent by Slave
		} catch (IOException ioe) { // exception handling
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}
	private void connect() { //connects to NXT brick through USB or Bluetooth
		try {
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB); //USB initiator
			/* Uncomment next line for Bluetooth communication */
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH); // Bluetooth initiator	
			NXTInfo[] nxtInfo = nxtComm.search(AulaMaster.NXT_ID); //find brick with NXT_ID by doing a Bluetooth inquiry
			if (nxtInfo.length == 0) { // failed to find a brick with the ID
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) { // the brick was found but a connection could not be establish
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream()); // open data input stream 
			dos = new DataOutputStream(nxtComm.getOutputStream()); // open data output stream

		} catch (NXTCommException e) {  // exception handling
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}		
	public static void main(String[] args) {
		byte cmd = 0; float param = 0; float ret=0f; 
		AulaMaster master = new AulaMaster(); // create object AulaMaster
		master.connect(); // tries to connect with slave
	   	Scanner scan = new Scanner( System.in ); // create object to read from keyboard
	   	while(true) {
	    		System.out.print("Enter command [0:Forward 1:Backward 2:Right 3:Left 4:Stop 5:Exit]: "); //print available commands
	    		cmd = (byte) scan.nextFloat(); // read (command) float and converts it to byte
	    		ret = master.sendCommand(cmd); //call sendCommand
	    		System.out.println("Command: " + cmd + " return: " + ret); //print command, paramenter and returned data from Slave
	    	}
	}

}

