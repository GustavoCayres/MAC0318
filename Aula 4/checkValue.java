import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.comm.RConsole;
 
public class checkValue{
        public static void main(String [] args){
          RConsole.openAny(0);
          LightSensor light = new LightSensor(SensorPort.S4);
          LCD.drawString("Teste sensor optico", 0, 0);
          Button.waitForAnyPress();
          LCD.clear();
          LCD.drawString("Preto", 0, 0); 
          LCD.drawInt(light.getLightValue(),  0, 4); 
          Button.waitForAnyPress();
          LCD.clear();
          LCD.drawString("Branco", 0, 0); 
          Motor.A.rotate(80, true);
          Motor.B.rotate(-80);
          LCD.drawInt(light.getLightValue(), 0, 4); 
          Button.waitForAnyPress();
          LCD.clear();
          LCD.drawString("Continuo...", 0, 0);
          
          while(!Button.ESCAPE.isDown()){
            Motor.A.rotate(-160, true);
            Motor.B.rotate(160,true);
            while(Motor.A.isMoving() || Motor.B.isMoving()) {
               Delay.msDelay(20);
               RConsole.println(""+light.getLightValue());
            }
            Delay.msDelay(400);
            Motor.A.rotateTo(80, true);
            Motor.B.rotateTo(-80,true);
            while(Motor.A.isMoving() || Motor.B.isMoving()) {
               Delay.msDelay(20);
               RConsole.println(""+light.getLightValue());
            }
            Delay.msDelay(400);
          }
          RConsole.close();
       }
}

