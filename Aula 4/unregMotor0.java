import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class unregMotor0 { //desregulado
	
	static LightSensor light;
	static NXTMotor mB;
	static NXTMotor mC;
	
	public static void main(String args[]){
    	RConsole.openAny(0);
		light = new LightSensor(SensorPort.S4);
		mB = new NXTMotor(MotorPort.B);
		mC = new NXTMotor(MotorPort.C);

		int u_straight = 50;
		int turn;
		int light_measurement;
		int kp = 1;
		int var;

		Button.waitForAnyPress();
		while(!Button.ESCAPE.isDown()){
			if(light.getLightValue() < 35){
				mB.setPower(25);
				mC.setPower(-25);
			}
			if(light.getLightValue() >= 35 && light.getLightValue() < 50) {
				mB.setPower(50);
				mC.setPower(50);
			}
			if(light.getLightValue() >= 50) {
				mB.setPower(-25);
				mC.setPower(25);
			}
		}
		mB.stop();
		mC.stop();
    }	
}

