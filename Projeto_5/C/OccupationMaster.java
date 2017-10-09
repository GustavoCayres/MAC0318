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

public class OccupationMaster {
    private DataOutputStream dos;
    private DataInputStream dis;

    private static int grid_size = 50;

    private static int width = 920 / grid_size + 1;
    private static int height = 1195 / grid_size + 1;
    private int[][] occupationMap = new int[height][width];

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
                occupationMap[i][j] = 0;

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
                            occupationMap[i][j] = -1;
                            break loop;
                        }
                    }
                }
            }
        }
    }

    private void mapThickening() {
        LinkedList<Cell> to_thick = new LinkedList<>();
        Cell current;

        for (int i = 0; i < height ; i++ ) {
            for (int j = 0; j < width ; j++) {
                current = new Cell(i, j);
                if (occupationMap[i][j] == -1) {
                    for (Cell neighbor : current.getNeighbors_8()) {
                        if (isValid(neighbor) && occupationMap[neighbor.i][neighbor.j] != -1)
                            to_thick.add(neighbor);
                    }
                }
            }
        }

        while (!to_thick.isEmpty()) {
            Cell cell = to_thick.poll();
            occupationMap[cell.i][cell.j] = -1;
        }
  }

    private boolean isValid(Cell c) {
        return c.i >= 0 && c.i < height && c.j >= 0 && c.j < width;
    }

    private void populateDistanceToTarget(Point source, Point target) {
        int i_t = (int) target.x / grid_size;
        int j_t = (int) target.y / grid_size;
        int i_s = (int) source.x / grid_size;
        int j_s = (int) source.y / grid_size;

        occupationMap[i_t][j_t] = -2;
        LinkedList<Cell> queue = new LinkedList<>();

        Cell[] neighbors = {
            new Cell(i_t, j_t + 1),
            new Cell(i_t + 1, j_t),
            new Cell(i_t - 1, j_t),
            new Cell(i_t, j_t - 1),
        };

        for (Cell cell : neighbors) {
            if (isValid(cell)) {
                queue.add(cell);
                occupationMap[cell.i][cell.j] = 1;
            }
        }

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int i = cell.i, j = cell.j;

            if (i == i_s && j == j_s) {
                break;
            }

            Cell[] cell_neighbors = {
                new Cell(i, j + 1),
                new Cell(i + 1, j),
                new Cell(i - 1, j),
                new Cell(i, j - 1),
            };

            for (Cell neighbor: cell_neighbors) {
                if (isValid(neighbor) && occupationMap[neighbor.i][neighbor.j] == 0) {
                    occupationMap[neighbor.i][neighbor.j] = occupationMap[i][j] + 1;
                    queue.add(neighbor);
                }
            }
        }
    }

    private List<Cell> findPath(Point origin, Point target) {
        return findPath(new Cell(origin), new Cell(target));
    }

    private List<Cell> findPath(Cell origin, Cell target) {
        System.out.println("Finding path...");
        ArrayList<Cell> path = new ArrayList<>();

        Cell current = origin;


        while (!current.equals(target)) {
            int min_dist = Integer.MAX_VALUE;
            Cell min = current;
            int actual = occupationMap[current.i][current.j];

            for (Cell neighbor : current.getNeighbors_8()) {
                if (isValid(neighbor) &&
                        occupationMap[neighbor.i][neighbor.j] != 0 &&
                        occupationMap[neighbor.i][neighbor.j] != -1 &&
                        occupationMap[neighbor.i][neighbor.j] < min_dist) {
                    min = neighbor;
                    min_dist = occupationMap[neighbor.i][neighbor.j];
                }
            }
            path.add(min);
            current = min;
        }

        System.out.println("Found!");
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
            new Line(502,155,700,225),
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
            NXTInfo[] nxtInfo = nxtComm.search(OccupationMaster.NXT_ID);

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
        buildOccupationMap();
//        mapThickening();
        populateDistanceToTarget(points[11], points[1]);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(String.format("%3d", occupationMap[i][j]));
            }
            System.out.print("\n");
        }


        List<Cell> path = findPath(new Cell(points[11]), new Cell(points[1]));
        List<Point> linearized = linearizePath(path);

        System.out.println("Map");



        System.out.println("Done");

        for (Cell c : path) {
            System.out.println(c);
        }

        for (Point c : linearized) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        byte cmd;
        float ret = 0, addX = 0f, addY = 0f;
        int start, end;

        OccupationMaster master = new OccupationMaster();
//        master.connect();
//        Scanner scan = new Scanner( System.in );

//        master.buildOccupationMap();
//        master.mapThickening();

//        while (true) {
//            System.out.print("Enter command [0:ADD_START_AND_STOP 1:TRAVEL_PATH 2:STATUS 3:STOP]: ");
//            cmd = scan.nextByte();
//            if (cmd == 0){
//                System.out.println("Enter start point: ");
//                start = scan.nextInt();
//                System.out.println("Enter end point: ");
//                end = scan.nextInt();
//
//                master.populateDistanceToTarget(points[start], points[end]);
//                List<Cell> path = master.findPath(points[start], points[end]);
//                List<Point> linearized = master.linearizePath(path);
//
//                for (Point p: linearized) {
//                    ret = master.sendCommand(cmd, p.x / 10.0f, p.y / 10.0f); // return 0 when Slave successfully received the dos
//
//                    System.out.println(String.format("cmd: X: %f, Y: %f, ret: %f", p.x, p.y, ret));
//                }
//            }
//            if (cmd == 1 || cmd == 2) {
//                ret = master.sendCommand(cmd, (float) -1, (float) -1);
//                System.out.println("cmd: " + " return: " + ret);
//            } else if (cmd == 3) {
//                ret = master.sendCommand(cmd, addX, addY); // return 0 when Slave successfully received the dos
//                System.exit(0);
//            }
//        }
        master.testPath();
    }
}

