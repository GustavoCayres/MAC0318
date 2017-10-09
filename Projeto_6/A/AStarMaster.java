package A;

import lejos.geom.Point;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import java.util.List;
import java.util.Scanner;
import java.io.*;

public class AStarMaster {
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

    private static Graph G;

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
            NXTInfo[] nxtInfo = nxtComm.search(AStarMaster.NXT_ID);

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

    private void addUndirectedEdgeBetweenPoints(int source, int dest) {
        G.addUndirectedEdge(source, dest, points[source].distance(points[dest]));
    }

    private void initGraph() {
        G = new Graph(12);

        addUndirectedEdgeBetweenPoints(1,2);
        addUndirectedEdgeBetweenPoints(2,3);
        addUndirectedEdgeBetweenPoints(2,6);
        addUndirectedEdgeBetweenPoints(3,4);
        addUndirectedEdgeBetweenPoints(4,5);
        addUndirectedEdgeBetweenPoints(4,10);
        addUndirectedEdgeBetweenPoints(5,6);
        addUndirectedEdgeBetweenPoints(5,10);
        addUndirectedEdgeBetweenPoints(6,7);
        addUndirectedEdgeBetweenPoints(7,8);
        addUndirectedEdgeBetweenPoints(8,11);
        addUndirectedEdgeBetweenPoints(10,11);
    }

    public static void main(String[] args) {
        byte cmd;
        float ret = 0, addX = 0f, addY = 0f;
        int start, end;

        AStarMaster master = new AStarMaster();
        master.connect();
        Scanner scan = new Scanner( System.in );

        master.initGraph();

        while (true) {
            System.out.print("Enter command [0:ADD_START_AND_STOP 1:TRAVEL_PATH 2:STATUS 3:STOP]: ");
            cmd = scan.nextByte();
            if (cmd == 0){
                System.out.println("Enter start point: ");
                start = scan.nextInt();
                System.out.println("Enter end point: ");
                end = scan.nextInt();

                for (int i = 0; i < G.V; i++) {
                    G.setVertexHeuristic(i, points[i].distance(points[end]));
                }

                System.out.println(G);
                List<Integer> path = G.aStarBetween(start, end);

                System.out.println("path: ");

                for (int v : path) {
                    Point p = points[v];
                    System.out.println("" + v);
 
                    ret = master.sendCommand(cmd, p.x / 10.0f, p.y / 10.0f); // return 0 when Slave successfully received the dos

                    System.out.println(String.format("cmd: X: %f, Y: %f, ret: %f", p.x, p.y, ret));
                }

            }
            if (cmd == 1 || cmd == 2) {
                ret = master.sendCommand(cmd, (float) -1, (float) -1);
                System.out.println("cmd: " + " return: " + ret);
            } else if (cmd == 3) {
                ret = master.sendCommand(cmd, addX, addY); // return 0 when Slave successfully received the dos
                System.exit(0);
            }
        }
    }
}

