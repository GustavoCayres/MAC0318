package A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private class Edge {
        int v, w;
        double length;

        public Edge(int v, int w, double length) {
            this.v = v;
            this.w = w;
            this.length = length;
        }
    }

    private class Node {
        ArrayList<Edge> edges = new ArrayList<>();
        double heuristic = Double.MAX_VALUE;
    }

    private Node[] nodes;
    public int V;

    public Graph(int V) {
        this.V = V;
        this.nodes = new Node[this.V];
        for (int i = 0; i < this.V; i++) {
            this.nodes[i] = new Node();
        }
    }

    public void setVertexHeuristic(int v, double h) {
        this.nodes[v].heuristic = h;
    }

    public void addEdge(int v, int w, double length) {
        nodes[v].edges.add(new Edge(v, w, length));
    }

    public void addUndirectedEdge(int v, int w, double length) {
        this.addEdge(v, w, length);
        this.addEdge(w, v, length);
    }

    public List<Integer> aStarBetween(int source, int target) {
        ArrayList<Integer> path = new ArrayList<>();

        boolean visited[] = new boolean[this.V];
        int parent[] = new int[this.V];
        double distance[] = new double[this.V];

        for (int i = 0; i < this.V; i++) {
            distance[i] = Double.MAX_VALUE;
        }
        distance[source] = 0;
        parent[source] = source;

        for (int count = 0; count < this.V; count++) {
            int v = minHeuristics(distance, visited);
            visited[v] = true;

            for (Edge e : nodes[v].edges) {
                int w = e.w;
                if (!visited[w]) {
                    double d = distance[v] + e.length;
                    if (d < distance[w]) {
                        distance[w] = d;
                        parent[w] = v;
                    }
                }
            }
        }

        path.add(target);
        for (int v = target; v != parent[v]; v = parent[v]) {
            path.add(parent[v]);
        }
        Collections.reverse(path);

        return path;
    }

    private int minHeuristics(double distance[], boolean visited[]) {
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && distance[v] + this.nodes[v].heuristic <= min) {
                min = distance[v] + this.nodes[v].heuristic;
                min_index = v;
            }
        }

        return min_index;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.V; i++) {
            str = str.concat(String.format("%d (%f): ", i, this.nodes[i].heuristic));

            for (Edge e : nodes[i].edges) {
                str = str.concat(String.format("%d-%d ", e.v, e.w));
            }

            str = str.concat("\n");
        }

        return str;
    }
}
