
import lejos.nxt.comm.RConsole;
import lejos.nxt.*;
import lejos.util.Delay;

public class bug2 {
	private static Double[] position = {0.0, 0.0, 0.0};
	private static Integer[] tacho = {0, 0};
	private static double r = 2.8;
	private static double axis = 11.2;
	private static NXTMotor mb = new NXTMotor(MotorPort.B); //create instance of an unregulated motor in motor port B 
	private static NXTMotor mc = new NXTMotor(MotorPort.C); //create instance of an unregulated motor in motor port C 
	private static NXTRegulatedMotor rb = new NXTRegulatedMotor(MotorPort.B); //create instance of a regulated motor in motor port B 
	private static NXTRegulatedMotor rc = new NXTRegulatedMotor(MotorPort.C); //create instance of a regulated motor in motor port C
	private static LightSensor light = new LightSensor(SensorPort.S4);
	private static int rotation = 0;

	public static void main(String args[])  
  	{
  		RConsole.openAny(0);

		Button.waitForAnyPress();
		
		/*rb.rotate(256,true);
		rc.rotate(-256);*/

		rb.suspendRegulation();
		rc.suspendRegulation();

		while(position[0] < 100) {
		//if(true){
			follow_line_until_obstacle();
			go_around_obstacle();
			rotate_to_initial_direction();
		}
	}	

	private static void update_position() {
		int new_tacho_C = Motor.C.getTachoCount();
		int new_tacho_B = Motor.B.getTachoCount();
		double delta_s_l = Math.toRadians(new_tacho_C - tacho[0]) * r;
		double delta_s_r = Math.toRadians(new_tacho_B - tacho[1]) * r;
		double delta_s = (delta_s_l + delta_s_r)/2;
		double delta_theta = (delta_s_r - delta_s_l)/axis;

		position[0] += delta_s * (Math.cos(position[2] + delta_theta/2));
		position[1] += delta_s * (Math.sin(position[2] + delta_theta/2));
		position[2] += delta_theta; /*theta em radianos*/
		tacho[0] = new_tacho_C;
		tacho[1] = new_tacho_B;
	}
	private static void print_position(){
		LCD.drawString(Double.toString(position[0]),0,0);
		LCD.drawString(Double.toString(position[1]),0,1);
		LCD.drawString(Double.toString(position[2]),0,2);
		Delay.msDelay(200);
		LCD.clear();
	}
	private static void print_position_console(){
		RConsole.println("x = "+position[0]+" y = "+position[1]);
		//LCD.drawString(Double.toString(position[0]),0,0);
		//LCD.drawString(Double.toString(position[1]),0,1);
		//LCD.drawString(Double.toString(position[2]),0,2);
		//Button.waitForAnyPress();
		//LCD.clear();
	}
	private static void follow_line_until_obstacle(){
		mb.setPower(25);
		mc.setPower(25);
		mb.forward();
		mc.forward();
		while(light.getLightValue() > 45 && position[0] < 100) {
			update_position();
		}
	}
	private static void go_around_obstacle(){
		int u_straight = 20;
		int turn;
		int light_measurement;
		int kp = 5;
		int var;
		int previous_error = 0;
		int error;
		int kd = 2;
		boolean stop_cycling = false;

		long start_time = System.currentTimeMillis();
		
		while(!stop_cycling) {
			if (System.currentTimeMillis() - start_time > 2000 && Math.abs(position[1]) < 1) {
				stop_cycling = true;
			}

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
			mb.setPower(var);
			var = u_straight + turn;
			if(var > 99) {
				var = 100;
			}
			if(var < -100) {
				var = -100;
			} 
			mc.setPower(var);
			previous_error = error;
			update_position();
		}
	}

	private static void rotate_to_initial_direction() {
		rb.stop(true);
		rc.stop();
		if(rotation == 0) {
			rb.rotate(-250,true);
			rc.rotate(250);
		}
		if(rotation == 1) {
			rb.rotate(-120,true);
			rc.rotate(120);
		}
		rotation = rotation + 1;
		rb.stop(true);
		rc.stop();
		//regulated to unregulated
		rb.suspendRegulation(); 
		rc.suspendRegulation();

	}


}