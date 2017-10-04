import lejos.geom.Line;
import lejos.geom.Point;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import lejos.geom.*;
import lejos.robotics.mapping.LineMap;
//import java.util.stream.Collectors;

public class MasterVisibilty {
    private DataOutputStream dos;
    private DataInputStream dis;

    private static final String NXT_ID = "NXT07"; // NXT BRICK ID

    private static final Point[] points = {
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

    private static final Line[] lines = {
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

    //private static Graph G;


    public static LinkedList<Line> map = new LinkedList<>();

    private void dilattedLines() {
        float epsilon = 5f;
        for(Line l : lines) {
            l.lengthen(epsilon);
            Point p1 = l.getP1();
            Point p2 = l.getP2();
            float m = 0;
            float inverse_m;
            try {
                m = (p1.y - p2.y) / (p1.x - p2.x);
            }
            catch(ArithmeticException e) {
                map.add(new Line(p1.x + epsilon, p1.y, p2.x + epsilon, p2.y));
                map.add(new Line(p1.x - epsilon, p1.y, p2.x - epsilon, p2.y));
            }
            try {
                inverse_m = - 1/m; 
                float c1 = (p1.y + inverse_m * p1.x); //constante
                float c2 = (p2.y + inverse_m * p2.x); // constante
                Line aux1 = new Line(p1.x, p1.y, p1.x + epsilon/100, inverse_m * c1);
                aux1.lengthen(epsilon);
                Line aux2 = new Line(p2.x, p2.y, p2.x + epsilon, inverse_m * c2);
                aux2.lengthen(epsilon);
                p1 = aux1.getP1();
                p2 = aux1.getP2();
                Point p3 = aux2.getP1();
                Point p4 = aux2.getP2(); 
                Line aux3 = new Line(p1.x, p1.y, p3.x, p3.y);
                Line aux4 = new Line(p2.x, p2.y, p4.x, p4.y);
                map.add(aux1);
                map.add(aux2);
                map.add(aux3);
                map.add(aux4);
            }
            catch(ArithmeticException e) {
                map.add(new Line(p1.x + epsilon, p1.y, p2.x + epsilon, p2.y));
            }

        }
    }

    // private void addUndirectedEdgeBetweenPoints(int source, int dest) {
        // G.addUndirectedEdge(source, dest, points[source].distance(points[dest]));
    // }
// 
    // private void initGraph() {
        // G = new Graph(12);
// 
        // addUndirectedEdgeBetweenPoints(1,2);
        // addUndirectedEdgeBetweenPoints(2,3);
        // addUndirectedEdgeBetweenPoints(2,6);
        // addUndirectedEdgeBetweenPoints(3,4);
        // addUndirectedEdgeBetweenPoints(4,5);
        // addUndirectedEdgeBetweenPoints(4,10);
        // addUndirectedEdgeBetweenPoints(5,6);
        // addUndirectedEdgeBetweenPoints(5,10);
        // addUndirectedEdgeBetweenPoints(6,7);
        // addUndirectedEdgeBetweenPoints(7,8);
        // addUndirectedEdgeBetweenPoints(8,11);
        // addUndirectedEdgeBetweenPoints(10,11);
    // }

    private float sendCommand(byte command, float paramX, float paramY) {
        try {
            dos.writeByte(command);
            dos.writeFloat(paramX);
            dos.writeFloat(paramY);
            dos.flush();
            return dis.readFloat();
        } catch (IOException ioe) {
            System.err.println("IO Exception");
            System.exit(1);
            return -1f;
        }
    }

    private boolean sendCommand(byte command) {
        try {
            dos.writeByte(command);
            dos.flush();
            return dis.readBoolean();
        } catch (IOException ioe) {
            System.err.println("IO Exception");
            System.exit(1);
            return false;
        }
    }

    private void connect() {
        try {
//            NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Bluetooth communication */
            NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
            NXTInfo[] nxtInfo = nxtComm.search(MasterVisibilty.NXT_ID);

            if (nxtInfo.length == 0) {
                System.err.println("NO NXT found");
                System.exit(1);
            }

            if (!nxtComm.open(nxtInfo[0])) {
                System.err.println("Failed to open NXT");
                System.exit(1);
            }

            dis = new DataInputStream(nxtComm.getInputStream());
            dos = new DataOutputStream(nxtComm.getOutputStream());

        } catch (NXTCommException e) {
            System.err.println("NXTComm Exception: "  + e.getMessage());
            System.exit(1);
        }
    }

    // private void addUndirectedEdgeBetweenPoints(int source, int dest) {
    //     G.addUndirectedEdge(source, dest, points[source].distance(points[dest]));
    // }

    // private void initGraph() {
    //     G = new Graph(12);

    //     addUndirectedEdgeBetweenPoints(1,2);
    //     addUndirectedEdgeBetweenPoints(2,3);
    //     addUndirectedEdgeBetweenPoints(2,6);
    //     addUndirectedEdgeBetweenPoints(3,4);
    //     addUndirectedEdgeBetweenPoints(4,5);
    //     addUndirectedEdgeBetweenPoints(4,10);
    //     addUndirectedEdgeBetweenPoints(5,6);
    //     addUndirectedEdgeBetweenPoints(5,10);
    //     addUndirectedEdgeBetweenPoints(6,7);
    //     addUndirectedEdgeBetweenPoints(7,8);
    //     addUndirectedEdgeBetweenPoints(8,11);
    //     addUndirectedEdgeBetweenPoints(10,11);
    // }

    public static void main(String[] args) {
        byte cmd;
        float ret = 0, addX = 0f, addY = 0f;
        boolean boolRet = false;
        int start, end;
        Rectangle bounds = new Rectangle(0, 0, 1195, 920); 
        LineMap mymap = new LineMap(map.toArray(), bounds);

        try{
            mymap.createSVGFile("mapa.svg");
            mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
        }
        catch (Exception e){
            System.out.print("Exception caught: ");
            System.out.println(e.getMessage());
        }

        MasterVisibilty master = new MasterVisibilty();
        master.connect();
        Scanner scan = new Scanner( System.in );

        //master.initGraph();

        // while (true) {
        //     System.out.print("Enter command [0:ADD_START_AND_STOP 1:TRAVEL_PATH 2:STATUS 3:STOP]: ");
        //     cmd = scan.nextByte();
        //     if (cmd == 0){
        //         System.out.println("Enter start point: ");
        //         start = scan.nextInt();
        //         System.out.println("Enter end point: ");
        //         end = scan.nextInt();

        //         System.out.println(G);
        //         List<Integer> path = G.shortestPathBetween(start, end);
        //         //String s = String.join(" -> ", path.stream().map(Object::toString).collect(Collectors.toList()));

        //         System.out.println("path: ");

        //         for (int v : path) {
        //             Point p = points[v];
        //             System.out.println("" + v);
 
        //             ret = master.sendCommand(cmd, p.x / 10.0f, p.y / 10.0f); // return 0 when Slave successfully received the dos

        //             System.out.println(String.format("cmd: X: %f, Y: %f, ret: %f", p.x, p.y, ret));
        //         }

        //     }
        //     if (cmd == 1 || cmd == 2) {
        //         ret = master.sendCommand(cmd, (float) -1, (float) -1);
        //         System.out.println("cmd: " + " return: " + ret);
        //     } else if (cmd == 3) {
        //         ret = master.sendCommand(cmd, addX, addY); // return 0 when Slave successfully received the dos
        //         System.exit(0);
        //     }
        // }
    }
}

