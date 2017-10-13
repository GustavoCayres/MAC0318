import lejos.geom.*;
import lejos.robotics.mapping.LineMap;
import java.util.*;
public class mapaMedido{

  public static void main(String[] args){

    Point[] points = {
      new Point(0, 0),
      new Point(100,813),    /* P1 */
      new Point(426,873),   /* P2 */
      new Point(1140,885),  /* P3 */
      new Point(1117,432),  /* P4 */
      new Point(830,507),   /* P5 */
      new Point(690,571),   /* P6 */
      new Point(450,593),   /* P7 */
      new Point(263,350),   /* P8 */
      new Point(531,382),   /* P9 */
      new Point(986,166),    /* P10 */
      new Point(490,100)     /* P11 */
    };

    List<Line> line_list = new ArrayList<Line> (Arrays.asList(
      /* L-shape polygon */
      new Line(170,437,60,680),
      new Line(60,680,398,800),
      new Line(398,800,450,677),
      new Line(450,677,235,595),
      new Line(235,595,281,472),
      new Line(281,472,170,437),
      /* Triangle */
      new Line(1070,815,770,602),
      new Line(770,602,1060,516),
      new Line(1070,815,1060,516),
      /* Pentagon */
      new Line(335,345,502,155),
      //new Line(502,155,700,225),
      new Line(700,225, 725,490),
      new Line(725,490,480,525),
      new Line(480,525,335,345)
    ));

 
    //Rectangle(int x, int y, int width, int height)  -- always integer coordinates
    //Creates a rectangle with top left corner at (x,y) and with specified width and height.
    //Rectangle bounds = new Rectangle(0, -841, 1189, 841);
    Point[] goal_points = {
      new Point(100, 800),
      new Point(50, 750),
      new Point(0, 700),
      new Point(0, 650),
      new Point(0, 600),
      new Point(0, 550),
      new Point(0, 500),
      new Point(0, 450),
      new Point(0, 400),
      new Point(0, 350),
      new Point(50, 300),
      new Point(100, 250),
      new Point(150, 250),
      new Point(200, 200),
      new Point(250, 150),
      new Point(300, 100),
      new Point(350, 50),
      new Point(400, 50),
      new Point(450, 0),
      new Point(500, 50),
      new Point(550, 100),
      new Point(600, 150),
      new Point(550, 200),
      new Point(550, 250),
      new Point(550, 300),
      new Point(500, 350)
    };  



    Point previous_point = goal_points[0];
    for (Point point : goal_points) {
      line_list.add(new Line((float) previous_point.getX(), (float) previous_point.getY(), (float) point.getX(), (float) point.getY()));
      previous_point = point;
    };
    Line[] lines = new Line[line_list.size()];
    line_list.toArray(lines);
    Rectangle bounds = new Rectangle(0, 0, 1195, 920);
    LineMap mymap = new LineMap(lines, bounds);

    try{
        mymap.flip().createSVGFile("mapa.svg"); //creates a fliped version in the Y-axis of the orginal image
    }catch (Exception e){
        System.out.print("Exception caught: ");
        System.out.println(e.getMessage());
    }
  }
}

