//Pedro Marcondes 8941168
//Gustavo Cayres  8584323, movimento 1, 2 e 3
//Exercício Odometria

import lejos.nxt.*;
import lejos.util.Delay;
import java.lang.Math;


public class Odometria {
	public static void main (String[] args) {
		double r = 2.8;
		double axis = 11.2;
		Double[] position = new Double[3];
		for(int i = 0; i < 3; i++) {
			position[i] = 0.0;
		}
		Integer[] tacho = new Integer[2];
		tacho[0] = Motor.A.getTachoCount();
		tacho[1] = Motor.B.getTachoCount();
		move1();
		update_position(position, tacho, r, axis);
		print_position(position);
		
	}
	private static void move1 () {
		Motor.A.setSpeed(300);
		Motor.B.setSpeed(300);
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(2500);
		Motor.A.stop(true);
		Motor.B.stop();
	}
	private static void move2() {
		Motor.A.setSpeed(450);
		Motor.B.setSpeed(300);
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(2500);
		Motor.A.stop(true);
		Motor.B.stop();
	}
	private static void move3() {
		Motor.A.setSpeed(300);
		Motor.B.setSpeed(300);
		Motor.A.forward();
		Motor.B.backward();
		Delay.msDelay(1500);
		Motor.B.stop(true);
		Motor.A.stop();
	}

	private static void update_position(Double[] position, Integer[] tacho, double r, double axis) {
		int new_tacho_A = Motor.A.getTachoCount();
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
		Button.waitForAnyPress();
		LCD.clear();
	}
 }
