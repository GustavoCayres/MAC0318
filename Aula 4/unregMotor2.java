import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class unregMotor2 { //derivativo, diferencial
	
	static LightSensor light;
	static NXTMotor mB;
	static NXTMotor mC;
	
	public static void main(String args[]){
    	RConsole.openAny(0);
		light = new LightSensor(SensorPort.S4);
		mB = new NXTMotor(MotorPort.B);
		mC = new NXTMotor(MotorPort.C);

		int u_straight = 35;
		int turn;
		int light_measurement;
		int kp = 3;
		int var;
		int previous_error = 0;
		int error;
		int kd = 1;

		Button.waitForAnyPress();
		while(!Button.ESCAPE.isDown()){
			light_measurement = light.getLightValue();
			error = (45 - light_measurement);
			turn = kp * error + kd * (previous_error - error);
			var = u_straight - turn;
			if(var > 99) {
				var = 100;
			}
			if(var < -100) {
				var = -100;
			} 
			mB.setPower(var);
			var = u_straight + turn;
			if(var > 99) {
				var = 100;
			}
			if(var < -100) {
				var = -100;
			} 
			mC.setPower(var);
			RConsole.println(""+light_measurement);
			previous_error = error;
		}
		mB.stop();
		mC.stop();
    }	
}

