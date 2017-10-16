import processing.core.PApplet;

public class Experiment extends PApplet
{
  private Buffer buffer;
  private Reading reading;
  float pixsDistance;

  @Override
  public void settings() {
      size(800, 600);
      this.buffer = new Buffer();
      Producer p = new Producer(this.buffer);
      p.start();
  }

  @Override
  public void setup()
  {
    background(0);
    fill(255);
  }

  public void drawRadar()
  {
    pushMatrix();
    translate((float)width/2,(float)(height-height*0.074));
    noFill();
    strokeWeight(1);
    stroke(98,245,31);
    textSize(10);
    // draws the arc lines
    for (int i = 25; i <= 200; i += 25) {
      float d = map(i, 0, 200, 0, width);
      arc(0,0,d,d,PI,TWO_PI);
      text(i,5,-d/2-5);
    }
    // draws the angle lines
    line(-width/2,0,width/2,0);
    line(0,0,(-width/2)*cos(radians(30)),(-width/2)*sin(radians(30)));
    line(0,0,(-width/2)*cos(radians(60)),(-width/2)*sin(radians(60)));
    line(0,0,(-width/2)*cos(radians(90)),(-width/2)*sin(radians(90)));
    line(0,0,(-width/2)*cos(radians(120)),(-width/2)*sin(radians(120)));
    line(0,0,(-width/2)*cos(radians(150)),(-width/2)*sin(radians(150)));
    line((-width/2)*cos(radians(30)),0,width/2,0);
    popMatrix();
  }

  void drawObject()
  {
    if (this.buffer.wasModified()) {
      this.reading = this.buffer.get();
      float distance = this.reading.getDistance();
      float angle = this.reading.getAngle();
      pushMatrix();
      translate((float)(width/2),(float)(height-height*0.074));
      strokeWeight(4);
      stroke(255,10,10);
      if(distance<250) // distance in cm
      {
        float x = map(distance*cos(radians(angle)),0,200,0,width/2);
        float y = -map(distance*sin(radians(angle)),0,200,0,width/2);
        ellipse(x, y, 4, 4);
      }
      popMatrix();
    }
  }
  
  public void keyPressed() {
    //  The system variable key always contains the value of the most recent key on the keyboard that was used (either pressed or released). 
    if (key == 'n' || key == 'N') 
      setup();
  }

  @Override
  public void draw() {
    drawRadar();
    drawObject();
  }

  public static void main(String[] args)
  {
    PApplet.main("Experiment");
  }
}
