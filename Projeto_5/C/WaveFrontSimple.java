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

public class WaveFrontSimple {
    private DataOutputStream dos;
    private DataInputStream dis;
    private int grid_size;
    private int width; 
    private int height;
    private int[][] occupationMap;

    public class Cell {
        int i, j;

        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Cell(Point p) {
            this.i = (int) p.x / grid_size;
            this.j = (int) p.y / grid_size;
        }

        public Cell[] getNeighbors4Connected() {
            return new Cell[]{
                new Cell(i, j + 1),
                new Cell(i + 1, j),
                new Cell(i - 1, j),
                new Cell(i, j - 1),
            };
        }

        public Cell[] getNeighbors8Connected() {
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


        public boolean equals(Cell that) {
            return this.i == that.i && this.j == that.j;
        }

        @Override
        public String toString() {
            return String.format("%d %d", this.i, this.j);
        }
    }

    public WaveFrontSimple(int grid_size) {
        this.grid_size = grid_size;
        this.height = 1195 / grid_size + 1;
        this.width = 920 / grid_size + 1;
        this.occupationMap = new int[height][width];
        
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                occupationMap[i][j] = Integer.MAX_VALUE;

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

    private boolean isWithinBorders(Cell c) {
        return c.i >= 0 && c.i < this.height && c.j >= 0 && c.j < this.width;
    }

    private void calculateWaveFrontFromTarget(Point origin, Point target) {
        Cell origin_cell = new Cell(origin);
        Cell target_cell = new Cell(target);
        LinkedList<Cell> queue = new LinkedList<>();

        occupationMap[target_cell.i][target_cell.j] = 0; // target
        queue.add(target_cell);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            if (cell.i == origin_cell.i && cell.j == origin_cell.j) {
                break;
            }

            for (Cell neighbor: cell.getNeighbors4Connected()) {
                if (isWithinBorders(neighbor) && occupationMap[neighbor.i][neighbor.j] == Integer.MAX_VALUE) {
                    occupationMap[neighbor.i][neighbor.j] = occupationMap[cell.i][cell.j] + 1;
                    queue.add(neighbor);
                }
            }
        }
    }

    private List<Cell> findPath(Cell origin, Cell target) {
        ArrayList<Cell> path = new ArrayList<>();

        Cell current = origin;
        while (!current.equals(target)) {
            int min_dist = Integer.MAX_VALUE;
            Cell min = null;

            for (Cell neighbor : current.getNeighbors8Connected()) {
                if (isWithinBorders(neighbor) &&
                        occupationMap[neighbor.i][neighbor.j] != -1 &&
                        occupationMap[neighbor.i][neighbor.j] < min_dist) {
                    min = neighbor;
                    min_dist = occupationMap[neighbor.i][neighbor.j];
                }
            }

            path.add(min);
            current = min;
        }

        return path;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (occupationMap[i][j] == Integer.MAX_VALUE) {
                    s += "inf ";
                }
                else {
                    s += String.format("%3d ", occupationMap[i][j]);
                }
            }
            s += "\n";
        }
        return s;
    }

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

    private void printPath(Point origin, Point target) {
        calculateWaveFrontFromTarget(origin, target);

        System.out.println("Wave Front Grid:");
        System.out.println(this);

        System.out.println("Path:");
        for (Cell c : findPath(new Cell(origin), new Cell(target))) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        new WaveFrontSimple(50).printPath(points[1], points[10]);
        new WaveFrontSimple(50).printPath(points[11], points[1]);
    }
}
