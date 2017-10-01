import lejos.geom.*;
import lejos.robotics.mapping.LineMap;

public class Mapa {

    public static void main(String[] args){
        Line[] lines = {
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
                new Line(502,155,700,225),
                new Line(700,225, 725,490),
                new Line(725,490,480,525),
                new Line(480,525,335,345)

        };
        //Rectangle(int x, int y, int width, int height)  -- always integer coordinates
        //Creates a rectangle with top left corner at (x,y) and with specified width and height.
        //Rectangle bounds = new Rectangle(0, -841, 1189, 841);
        Rectangle bounds = new Rectangle(0, 0, 1195, 920);
        LineMap mymap = new LineMap(lines, bounds);

        Point[] points = {
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
        try{
            mymap.createSVGFile("mapa.svg");
            mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
        }catch (Exception e){
            System.out.print("Exception caught: ");
            System.out.println(e.getMessage());
        }
    }
}