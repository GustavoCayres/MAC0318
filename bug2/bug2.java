
import lejos.nxt.comm.RConsole;
import lejos.nxt.*;
import lejos.util.Delay;

public class bug2 {

	public static void main(String args[])  
  	{
		NXTMotor mB = new NXTMotor(MotorPort.B); //create instance of an unregulated motor in motor port B 
		NXTMotor mC = new NXTMotor(MotorPort.C); //create instance of an unregulated motor in motor port C 
		NXTRegulatedMotor rb = new NXTRegulatedMotor(MotorPort.B); //create instance of a regulated motor in motor port B 
		NXTRegulatedMotor rc = new NXTRegulatedMotor(MotorPort.C); //create instance of a regulated motor in motor port C
		LightSensor light = new LightSensor(SensorPort.S4);
		double r = 2.8;
		double axis = 11.2;
		Double[] position = new Double[3];
		Integer[] tacho = new Integer[2];
		int u_straight = 50;
		int turn;
		int light_measurement;
		int kp = 6;
		int var;
		int previous_error = 0;
		int error;
		int kd = 3;

  		RConsole.openAny(0);

		Button.waitForAnyPress();
		
		rb.rotate(256,true);
		rc.rotate(-256);

  		position[0] = 0.0;
  		position[1] = 0.0;
  		position[2] = 0.0;

		tacho[0] = 0;
		tacho[1] = 0;
		rb.suspendRegulation();
		rc.suspendRegulation();
		mB.setPower(30);
		mC.setPower(30);
		RConsole.println(""+light.getLightValue());
		while(true) {
			mB.forward();
			mC.forward();
			while(light.getLightValue() > 45) {
				RConsole.println(""+light.getLightValue());
				update_position(position, tacho, r, axis);
			}
			
			while(position[1] < -.5 && position[1] > +.5){
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
				previous_error = error;
				update_position(position, tacho, r, axis);
			}
		}
	}	

	private static void update_position(Double[] position, Integer[] tacho, double r, double axis) {
		int new_tacho_A = Motor.C.getTachoCount();
		int new_tacho_B = Motor.B.getTachoCount();
		double delta_s_r = Math.toRadians(new_tacho_A - tacho[0]) * r;
		double delta_s_l = Math.toRadians(new_tacho_B - tacho[1]) * r;
		double delta_s = (delta_s_l + delta_s_r)/2;
		double delta_theta = (delta_s_r - delta_s_l)/axis;
		position[0] += delta_s * (Math.cos(position[2] + delta_theta/2));
		position[1] += delta_s * (Math.sin(position[2] + delta_theta/2));
		position[2] += delta_theta; /*theta em radianos*/
		tacho[0] = new_tacho_A;
		tacho[1] = new_tacho_B;
	}
	private static void print_position(Double[] position){
		LCD.drawString(Double.toString(position[0]),0,0);
		LCD.drawString(Double.toString(position[1]),0,1);
		LCD.drawString(Double.toString(position[2]),0,2);
		Delay.msDelay(200);
		LCD.clear();
	}
		private static void print_position_console(Double[] position){
		RConsole.println("x = "+position[0]);
		//LCD.drawString(Double.toString(position[0]),0,0);
		//LCD.drawString(Double.toString(position[1]),0,1);
		//LCD.drawString(Double.toString(position[2]),0,2);
		//Button.waitForAnyPress();
		//LCD.clear();
	}


}