import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class unregMotor1 { //Proporcional
	
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
			light_measurement = light.getLightValue();
			turn = kp * (45 - light_measurement);
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
		}
		mB.stop();
		mC.stop();
    }	
}

