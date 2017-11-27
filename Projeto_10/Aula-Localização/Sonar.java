import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.navigation.DifferentialPilot;

class Scanner extends Thread {
	DataOutputStream output;
	boolean run;
	public boolean scann;

	Scanner(DataOutputStream output) {
		super();
		this.output = output;
		run = true;
		scann = false;
	}

	public void scann() {
		scann = true;
	}
	

	public void stop() {
		run = false;
	}

	public void run() {
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		while (run) {
			if (scann) {
				int distance = sensor.getDistance();
				try {
					output.write('@');
					output.write(distance);
					output.flush();
					scann = false;
					Thread.yield();
				} catch (IOException e) {
				}
			}
		}
	}
}

public class Sonar {

	public static final byte EXIT = 10;
	public static final byte READ = 2;
	public static final byte MOVE = 3;

	public static void main(String[] args) throws Exception {

		BTConnection btc = Bluetooth.waitForConnection();
		// USBConnection btc = USB.waitForConnection();

		DataInputStream input = btc.openDataInputStream();
		DataOutputStream output = btc.openDataOutputStream();

		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.A, false);
		pilot.setRotateSpeed(5);
		pilot.setTravelSpeed(20);

		Scanner scan = new Scanner(output);
		scan.start();
		int input_byte;
		boolean run = true;

		Sound.twoBeeps();

		while (run) {
			if (input.available() <= 0) {
				Thread.yield();
				continue;
			}
			input_byte = input.readByte();

			System.out.println(input_byte);

			switch (input_byte) {
			case READ:
				scan.scann();
				break;
			case EXIT:
				run = false;
				break;
			case MOVE:
				float d = input.readFloat();
				pilot.travel(d);
				break;
			default:
				pilot.stop();
				break;
			}
		}
		Sound.beep();
		scan.stop();
		scan.join();
	}
}