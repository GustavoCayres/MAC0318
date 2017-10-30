import lejos.geom.*;
import lejos.robotics.mapping.LineMap;

public class mapaMedido{

  public static void main(String[] args){
    Line[] lines = {

new Line(350,294,385,301),
new Line(528,211,548,245),
new Line(564,281,573,327),
new Line(363,295,506,150),
new Line(506,150,533,173),
new Line(533,173,528,211),
new Line(528,211,579,239),
new Line(579,239,564,281),
new Line(564,281,573,327),
new Line(573,327,604,333),
new Line(604,333,550,506),
new Line(539,180,528,211),
new Line(528,211,579,239),
new Line(579,239,564,281),
new Line(564,281,573,327),
new Line(573,327,604,333),
new Line(604,333,561,492),
new Line(350,294,353,294),
new Line(369,296,506,150),
new Line(506,150,527,167),
new Line(527,167,545,187),
new Line(545,187,544,238),
new Line(544,238,579,239),
new Line(579,239,589,263),
new Line(589,263,573,327),
new Line(573,327,604,333),
new Line(604,333,604,367),
new Line(604,367,599,403),
new Line(599,403,589,437),
new Line(589,437,570,477),
new Line(570,477,539,520),
new Line(539,520,477,570),
new Line(477,570,350,605),
new Line(350,294,384,300),
new Line(384,300,506,150),
new Line(506,150,533,173),
new Line(533,173,528,211),
new Line(528,211,579,239),
new Line(579,239,564,281),
new Line(564,281,573,327),
new Line(573,327,604,333),
new Line(604,333,556,499),
new Line(545,513,539,520),
new Line(499,556,350,605),
new Line(350,294,371,297),
new Line(371,297,506,150),
new Line(506,150,527,167),
new Line(527,167,545,187),
new Line(545,187,544,238),
new Line(544,238,579,239),
new Line(579,239,589,263),
new Line(589,263,573,327),
new Line(573,327,604,333),
new Line(604,333,604,367),
new Line(604,367,599,403),
new Line(599,403,589,437),
new Line(589,437,570,477),
new Line(570,477,539,520),
new Line(539,520,477,570),
new Line(477,570,350,605),
new Line(350,294,377,298),
new Line(377,298,506,150),
new Line(506,150,545,187),
new Line(545,187,528,211),
new Line(528,211,579,239),
new Line(579,239,571,319),
new Line(604,333,604,367),
new Line(604,367,599,403),
new Line(599,403,589,437),
new Line(589,437,570,477),
new Line(570,477,539,520),
new Line(539,520,477,570),
new Line(477,570,350,605),
new Line(350,294,361,295),
new Line(548,245,579,239),
new Line(579,239,564,281),
new Line(564,281,573,327),
new Line(573,327,604,333),
new Line(604,333,570,477),
new Line(556,499,513,545),
new Line(513,545,350,605),
new Line(355,294,379,299),
new Line(379,299,506,150),
new Line(506,150,539,180),
new Line(539,180,528,211),
new Line(528,211,579,239),
new Line(579,239,592,428),
new Line(545,513,492,561),
new Line(492,561,350,605),
new Line(350,294,377,298),
new Line(377,298,506,150),
new Line(506,150,545,187),
new Line(545,187,528,211),
new Line(528,211,579,239),
new Line(579,239,564,281),
new Line(564,281,603,376),
new Line(592,428,570,477),
new Line(513,545,350,605),
new Line(350,294,379,299),
new Line(379,299,506,150),
new Line(506,150,533,173),
new Line(492,561,350,605),
new Line(350,294,377,298),
new Line(377,298,513,155),
new Line(485,566,350,605),
new Line(350,294,377,298),
new Line(377,298,506,150),
new Line(506,150,545,187),
new Line(545,187,536,225),
new Line(533,527,453,582),
new Line(453,582,350,605),
new Line(350,294,384,300),
new Line(384,300,506,150),
new Line(506,150,545,187),
new Line(540,231,579,239),
new Line(579,239,564,281),
new Line(564,281,604,358),
new Line(550,506,477,570),
new Line(411,597,350,605),
new Line(350,294,377,298),
new Line(377,298,506,150),
new Line(506,150,545,187),
new Line(545,187,528,211),
new Line(528,211,579,239),
new Line(579,239,564,281),
new Line(564,281,602,385),
new Line(520,539,350,605),
new Line(350,294,377,298),
new Line(377,298,520,161),
new Line(589,263,564,281),
new Line(564,281,573,327),
new Line(573,327,604,333),
new Line(604,333,586,445),
new Line(506,550,350,605),
new Line(350,294,381,300),
new Line(381,300,506,150),
new Line(506,150,539,180),
new Line(469,575,350,605),

      
    };
    //Rectangle(int x, int y, int width, int height)  -- always integer coordinates
    //Creates a rectangle with top left corner at (x,y) and with specified width and height.
    //Rectangle bounds = new Rectangle(0, -841, 1189, 841);  
    Rectangle bounds = new Rectangle(0, 0, 1000, 1000); 
    LineMap mymap = new LineMap(lines, bounds);

    try{
        mymap.createSVGFile("mapa.svg");
        mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
    }catch (Exception e){
        System.out.print("Exception caught: ");
        System.out.println(e.getMessage());
    }
  }
}

