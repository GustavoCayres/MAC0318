import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

public class MasterNav {
	private static final byte ADD_POINT = 0; //adds waypoint to path
	private static final byte TRAVEL_PATH = 1; // enables slave to execute the path
	private static final byte STATUS = 2; // enquires about slave's position 
	private static final byte SET_START = 3; // set initial waypoint
	private static final byte STOP = 4; // closes communication

	private NXTComm nxtComm;
	private DataOutputStream dos;
	private DataInputStream dis;	

	private static final String NXT_ID = "NXT07"; // NXT BRICK ID

	private float sendCommand(byte command, float paramX, float paramY) {
		try {
			dos.writeByte(command);
			dos.writeFloat(paramX);
			dos.writeFloat(paramY);
			dos.flush();
			return dis.readFloat();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}

	private boolean sendCommand(byte command) {
		try {
			dos.writeByte(command);
			dos.flush();
			return dis.readBoolean();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return false;
		}
	}

	private void connect() {
		try {
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Bluetooth communication */
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(MasterNav.NXT_ID);
			
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream());
			dos = new DataOutputStream(nxtComm.getOutputStream());
			
		} catch (NXTCommException e) {
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}		

	private void close() {
		try {
			dos.writeByte(STOP);
			dos.writeFloat(0f);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	public static void main(String[] args) {
		byte cmd = 0; float param = 0f; float ret=0f; float addX = 0f; float addY = 0f; boolean boolRet = false;
		MasterNav master = new MasterNav();
		master.connect();
	    Scanner scan = new Scanner( System.in );

	    
	    while(true) {
	    	
	    	System.out.print("Enter command [0:ADD_POINT 1:TRAVEL_PATH 2:STATUS 3:SET_START 4:STOP]: ");
	    	cmd = (byte) scan.nextFloat(); 
	    	if (cmd == 0 || cmd == 3){
	    		System.out.println("Enter coordinate X: ");
	    		addX = scan.nextFloat();
	    		System.out.println("Enter coordinate Y: ");
	    		addY = scan.nextFloat();
	    	} else {
	    		addX = -1;
	    		addY = -1;	    		
	    	}
	    	if (cmd == 2){
	    		boolRet = master.sendCommand(cmd);
	    		System.out.println("cmd: " + " return: " + boolRet);
	    	}else{
	    		cmd = 3;
	    		master.sendCommand(cmd, 10.0f, 81.3f);
	    		cmd = 0;
	   			master.sendCommand(cmd, 114.0f,88.5f);
	    		master.sendCommand(cmd, 111.7f,43.2f);
	    		master.sendCommand(cmd, 98.6f,16.6f);

	    		addX = -1;
	    		addY = -1;	
	    		cmd = 1;
	    		ret = master.sendCommand(cmd, addX, addY); // return 0 when Slave successfully recieved the dos
	    		System.out.println("cmd: " + addX + " X: " + "Y: " + addY +" return: " + ret);
	    	}
	    }
	}
}
