
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
	private static Double best_line_position = 0.0;

	public static void main(String args[])  
  	{
  		RConsole.openAny(0);
		Button.waitForAnyPress();
		
		/*rb.rotate(256,true);
		rc.rotate(-256);*/

		rb.suspendRegulation();
		rc.suspendRegulation();

		while(true) {
			follow_line_until_obstacle();
			if(position[0] > 122) {
				break;
			}
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

		RConsole.println(""+position[0]+" "+position[1]+" "+(position[2]*(180/3.14)));
	}
	
	private static void follow_line_until_obstacle(){
		mb.setPower(20);
		mc.setPower(20);
		mb.forward();
		mc.forward();
		while(light.getLightValue() > 45 && position[0] < 122) {
			update_position();
		}
		best_line_position = Math.max(position[0], best_line_position);
	}
	private static void go_around_obstacle(){
		int u_straight = 20;
		int turn;
		int light_measurement;
		int kp = 3;
		int kd = 3;
		int var;
		int previous_error = 0;
		int error;
		boolean stop_cycling = false;

		long start_time = System.currentTimeMillis();
		
		while(!stop_cycling) {
			if (System.currentTimeMillis() - start_time > 3500 && Math.abs(position[1]) < 1 && position[0] > best_line_position + 2) {
				stop_cycling = true;
				best_line_position = Math.max(position[0], best_line_position);
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
		int u = 20;

		mb.setPower(- (int) Math.signum(position[2]) * u);
		mc.setPower((int) Math.signum(position[2]) * u);
		while (Math.abs(position[2]) > 0.06) {
			update_position();
		}
	}
}