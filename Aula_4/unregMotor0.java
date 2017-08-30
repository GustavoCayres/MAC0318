import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class unregMotor0 { //left-straight-right
	
	static LightSensor light;
	static NXTMotor mB;
	static NXTMotor mC;
	
	public static void main(String args[]){
    	RConsole.openAny(0);
		light = new LightSensor(SensorPort.S4);
		mB = new NXTMotor(MotorPort.B);
		mC = new NXTMotor(MotorPort.C);

		int light_measurement;

		Button.waitForAnyPress();
		while(!Button.ESCAPE.isDown()){
			light_measurement = light.getLightValue();
			if(light_measurement< 40){
				mB.setPower(25);
				mC.setPower(-25);
			}
			if(light_measurement >= 40 && light_measurement < 55) {
				mB.setPower(50);
				mC.setPower(50);
			}
			if(light_measurement >= 55) {
				mB.setPower(-25);
				mC.setPower(25);
			}
			RConsole.println(""+light_measurement);
		}
		mB.stop();
		mC.stop();
    }	
}

