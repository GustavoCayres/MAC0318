import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class offset {

	public static void main(String[] args) {
		LCD.drawString("offset sonar", 0, 0);
		Button.waitForAnyPress();
		while(!Button.ESCAPE.isDown()){
			LCD.clear();
			if(Button.LEFT.isDown()){
				Motor.C.rotate(10); //motor do sonar na porta C
			}
			else if(Button.RIGHT.isDown()){
				Motor.C.rotate(-10);
			}
		}
	}
}

