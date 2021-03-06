// package B;

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
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarOccupationMaster {
    private DataOutputStream dos;
    private DataInputStream dis;

    private static int grid_size = 50;

    private static int width = 920 / grid_size + 1;
    private static int height = 1195 / grid_size + 1;
    private double[][] occupationMap = new double[height][width];
    private double[][] occupationMapProb = new double[height][width];
    private Cell[][] parentMap = new Cell[height][width];
    private Cell target;
    private double maximumDistance = Math.sqrt(Math.pow(920,2) + Math.pow(1195,2));
    private double maximumCost = width * height;

    public class Cell {
        int i, j;

        Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Cell(Point p) {
            this.i = (int) p.x / grid_size;
            this.j = (int) p.y / grid_size;
        }

        public Cell[] getNeighbors() {
            return new Cell[]{
                new Cell(i, j + 1),
                new Cell(i + 1, j),
                new Cell(i - 1, j),
                new Cell(i, j - 1),
            };
        }

        public Cell[] getNeighbors_8() {
            return new Cell[]{
                new Cell(i, j + 1),
                new Cell(i + 1, j),
                new Cell(i - 1, j),
                new Cell(i, j - 1),
                new Cell(i + 1, j + 1),
                new Cell(i + 1, j -1),
                new Cell(i - 1, j + 1),
                new Cell(i - 1, j - 1),
            };
        }

        public Line lineTo(Cell that) {
            return new Line(this.i * grid_size, this.j * grid_size, that.i * grid_size, that.j * grid_size);
        }

        public boolean equals(Cell that) {
            return this.i == that.i && this.j == that.j;
        }

        public Point toPoint() {
            return new Point(i * grid_size, j*grid_size);
        }

        @Override
        public String toString() {
            return String.format("%d %d", this.i, this.j);
        }
    }

    private void buildOccupationMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                occupationMap[i][j] = 1000;
                occupationMapProb[i][j] = 0;
                int x = grid_size * i;
                int y = grid_size * j;

                Line[] limits = {
                    new Line(x, y, x+grid_size, y),
                    new Line(x, y, x,y+grid_size),
                    new Line(x, y+grid_size, x+grid_size, y+grid_size),
                    new Line(x+grid_size, y, x+grid_size, y+grid_size)
                };

                loop:
                for (Line limit : limits) {
                    for (Line line : lines) {
                        if (limit.intersectsAt(line) != null) {
                            occupationMapProb[i][j] = 1;
                            break loop;
                        }
                    }
                }
            }
        }
    }


    private boolean isValid(Cell c) {
        return c.i >= 0 && c.i < height && c.j >= 0 && c.j < width;
    }

    private void convolute(int convolutions) {
        double[][] occupationMapCopy = new double[height][width];

        for (int c = 0; c < convolutions; c++) {
            for (int i = 0; i < height; i++) {
            
               for (int j = 0; j < width; j++) {
                    occupationMapCopy[i][j] = occupationMapProb[i][j];
                }
            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Cell[] neighbors = new Cell(i, j).getNeighbors_8();
                    double value = 0.4 * occupationMapCopy[i][j];

                    for (int k = 0; k < 8; k++) {
                        Cell neighbor = neighbors[k];
                        if(isValid(neighbor)) {
                            if (k < 4) {
                                value += 0.1 * occupationMapCopy[neighbor.i][neighbor.j];
                            } else {
                                value += 0.05 * occupationMapCopy[neighbor.i][neighbor.j];
                            }
                        }
                    }
                    occupationMapProb[i][j] = value;
                }
            }
        }
    }

    private List<Cell> findPath(Point origin, Point targ) {
        return findPath(new Cell(origin), new Cell(targ));
    }

    private double fScore(Cell cell) {
        double alfa = 0.8;
        double heuristic = cell.toPoint().distance(target.toPoint());

        return alfa * occupationMap[cell.i][cell.j] / maximumCost + (1 - alfa) / 2 * occupationMapProb[cell.i][cell.j] + (1 - alfa) / 2 * heuristic / maximumDistance;
    }

    private class CellComparator implements Comparator<Cell> {
        //@Override
        public int compare(Cell x, Cell y) {
            if (fScore(x) < fScore(y)) {
                return -1;
            }
            if (fScore(x) > fScore(y)) {
                return 1;
            }
            return 0;
        }
    }

    private List<Cell> findPath(Cell origin, Cell targ) {
        System.out.println("Finding path...");

        target = targ;

        Cell current, i;
        ArrayList<Cell> path = new ArrayList<>();
        PriorityQueue<Cell> edge = new PriorityQueue<>(10, new CellComparator());
        parentMap[origin.i][origin.j] = origin;
        occupationMap[origin.i][origin.j] = 0;
        edge.add(origin);


        while (edge.size() != 0) {
            double min_f = Double.MAX_VALUE;
            current = edge.poll();
            Cell min;
            if (current.equals(target)) {
                i = current;
                path.add(0, i);
                while (parentMap[i.i][i.j] != i){
                    i = parentMap[i.i][i.j];
                    path.add(0, i);
                }

                System.out.println("Found!");
                return path;
            }
            if(occupationMap[current.i][current.j] != -1) {
                for (Cell neighbor : current.getNeighbors_8()) {
                    if (isValid(neighbor) &&
                            occupationMap[neighbor.i][neighbor.j] != -1 &&
                            occupationMap[neighbor.i][neighbor.j] > occupationMap[current.i][current.j] + 1) {
                        occupationMap[neighbor.i][neighbor.j] = occupationMap[current.i][current.j] + 1;
                        parentMap[neighbor.i][neighbor.j] = current;
                        edge.add(neighbor);
                    }
                }
            }

            occupationMap[current.i][current.j] = -1;

        }
        return path;
    }

    public List<Point> linearizePath(List<Cell> path) {
        LinkedList<Point> linearized = new LinkedList<>();
        int i = 0;

        System.out.println("Started linearize");

        Cell source = path.get(0);

        linearized.add(source.toPoint());

        while (i < path.size() - 1) {
            boolean intersects = false;

            for (int j = path.size() - 1; j != i; j--) {
                Cell c = path.get(j);
                intersects = false;
                Line line = source.lineTo(c);

                for (Line l : lines) {
                    if (l.intersectsAt(line) != null) {
                        intersects = true;
                        break;
                    }
                }

                if (!intersects) {
                    if (!c.equals(source)) {
                        linearized.add(c.toPoint());
                    }
                    source = c;
                    break;
                }
            }
            if (intersects) {
                source = path.get(++i);
                linearized.add(source.toPoint());
            }
            i++;
        }

        return linearized;
    }

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
            new Line(700,225, 725,490),
            new Line(725,490,480,525),
            new Line(480,525,335,345)
    };

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
            NXTInfo[] nxtInfo = nxtComm.search(AStarOccupationMaster.NXT_ID);

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

    private void testPath() {
        //buildOccupationMap();
        //master.convolute(3);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(String.format("%6.2f ", occupationMapProb[i][j]));
            }
            System.out.print("\n");
        }

        List<Cell> path = findPath(new Cell(points[1]), new Cell(points[9]));
        for (Cell c : path) {
            System.out.println(c.toPoint());
        }

        List<Point> linearized = linearizePath(path);

        System.out.println("Done");

       
        for (Point c : linearized) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        byte cmd;
        float ret = 0, addX = 0f, addY = 0f;
        int start, end;

        AStarOccupationMaster master = new AStarOccupationMaster();
        //master.connect();
        //Scanner scan = new Scanner( System.in );

        master.buildOccupationMap();
        master.convolute(3);

        

/*
        while (true) {
            System.out.print("Enter command [0:ADD_START_AND_STOP 1:TRAVEL_PATH 2:STATUS 3:STOP]: ");
            cmd = scan.nextByte();
            if (cmd == 0){
                System.out.println("Enter start point: ");
                start = scan.nextInt();
                System.out.println("Enter end point: ");
                end = scan.nextInt();

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        System.out.print(String.format("%6.2f ", master.occupationMap[i][j]));
                    }
                    System.out.print("\n");
                }

                List<Cell> path = master.findPath(points[start], points[end]);
                List<Point> linearized = master.linearizePath(path);

                for (Point p: linearized) {
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
*/
        master.testPath();
    }
}

