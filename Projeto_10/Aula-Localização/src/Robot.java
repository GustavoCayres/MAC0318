import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Robot {
	private String name;
	private NXTComm nxtComm;
	private MainProgram main;
	private Semaphore sendm;
	

	public static final byte EXIT = 10;
	public static final byte READ = 2;
	public static final byte MOVE = 3;
	
	public static final byte TYPE_INT = 1;
	public static final byte TYPE_CMD = 2;
	public static final byte TYPE_FLOAT = 4;
	
	private DataOutputStream output;
	private DataInputStream input;
	private Receiver receivethread;
	private Sender sendthread;
	
	private ArrayList<SendData> tosend;
		
	private class Receiver extends Thread {
		public boolean run = true;
		@Override
		public void run() {
			int bytes_valiable = -1;
			
			while(run) {
				try {
					bytes_valiable = input.available();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (bytes_valiable >= 0) {
					try {
						if (input.readByte() != '@') continue;
						int distance = input.readByte();
						System.out.println(distance);

						if (main != null)
							main.robotData(distance);
					} catch (IOException e1) {
						continue;
					}
				}
				Thread.yield();
			}
		}
	}

	private class Sender extends Thread {
		public boolean run = true;
		@Override
		public void run() {		
			while(run) {
				SendData value = null;
				if (sendm.tryAcquire()) {
					if (tosend.size() > 0) {
						value = tosend.get(0);
						tosend.remove(0);
					}
					sendm.release();
				}
				if (value != null)
					value.send(output);
				else
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {

					}
			}
		}
	}
	
	private class SendData {
		float f;
		int i;
		int cmd;
		int type;
		
		SendData(int cmd) {
			this.cmd = cmd;
			this.type = TYPE_CMD;
		}
		
		SendData(int cmd, float f) {
			this.cmd = cmd;
			this.f = f;
			this.type = TYPE_FLOAT;
		}
		
		public boolean send(DataOutputStream output) {
			try {
				switch (type) {
				case TYPE_CMD:
					output.write(cmd);
					break;
				case TYPE_INT:
					output.write(cmd);
					output.write(i);
					break;
				case TYPE_FLOAT:
					output.write(cmd);
					output.writeFloat(f);
					break;
				default:
					return false;
				}
				output.flush();
			} catch (IOException e) {
				return false;
			}
			return true;
		}
	}
	
	public Robot (String name) {		
		sendm = new Semaphore(1);
		tosend = new ArrayList<SendData>();
		
		receivethread = new Receiver();
		sendthread = new Sender();
		
		this.name = name;
	}
	
	public void send (SendData sd) {
		if (sendm.tryAcquire()) {
			tosend.add(sd);
			sendm.release();
		}
	}

	public boolean connect () {
		System.out.println("Conectando...");
		try {
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo[] nxtInfo = nxtComm.search(name); //find brick with NXT_ID by doing a Bluetooth inquiry
			if (nxtInfo.length == 0) { // failed to find a brick with the ID
				System.err.println("NO NXT found");
				return false;
			}
			if (!nxtComm.open(nxtInfo[0])) { // the brick was found but a connection could not be establish
				System.err.println("Failed to open NXT");
				return false;
			}
			System.out.println("Conectado.");
			input = new DataInputStream(nxtComm.getInputStream()); // open data input stream 
			output = new DataOutputStream(nxtComm.getOutputStream()); // open data output stream
		} catch (NXTCommException e) {
			System.out.println("Erro ao conectar.");
			return false;
		}
		
		receivethread.start();
		sendthread.start();
		
		return true;	
	}

	public void move(double x) {
		send(new SendData(MOVE, (float)x));
	}

	public void read(MainProgram r) {
		main = r;
		send(new SendData(READ));
	}

	public void disconnect() {
		send(new SendData(EXIT));
		if (receivethread == null) return;
		receivethread.run = false;
		try {
			receivethread.join();
		} catch (InterruptedException e1) {
			System.out.println("Nao foi possivel finalizar as threads...");
		}

		if (sendthread == null) return;
		sendthread.run = false;
		try {
			sendthread.join();
		} catch (InterruptedException e1) {
			System.out.println("Nao foi possivel finalizar as threads...");
		}
		
		try {
			nxtComm.close();
		} catch (IOException e) {
		}
	}
	
	@Override
	public String toString() {
		return "Bluetooth Mestre/Escravo";
	}

}
