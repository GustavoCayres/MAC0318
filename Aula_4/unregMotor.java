import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

public class unregMotor { //left-right
	
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
			if(light_measurement < 50){
				mB.setPower(10);
				mC.setPower(40);
			}

			if(light_measurement >= 50) {
				mB.setPower(40);
				mC.setPower(10);
			}
			RConsole.println(""+light_measurement);
		}
		mB.stop();
		mC.stop();
    }	
}

