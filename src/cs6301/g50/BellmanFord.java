package cs6301.g50;

import cs6301.g00.Graph;

import java.util.Deque;
import java.util.LinkedList;

/*
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 *
 */

public class BellmanFord {
    private Graph graph;
    private Graph.Vertex source;
    private Integer[] dist;
    private Deque<Graph.Edge>[] lastEdge;
    private Deque<Graph.Vertex> queue;
    private int cost;
    private boolean[] present;
    private boolean cycle;
    private boolean nochange;
    private Long[] vertexmem;

    // Private function that does a comparison if either one of the number is INF, here treated as null
    private int compareTo(Integer n1, Integer n2){
        if(n1==null&&n2==null){
            return 0;
        }
        if(n1==null){
            return 1;
        }
        if(n2==null){
            return -1;
        }
        return n1.compareTo(n2);
    }

    // Private function that can be used to add a value if the other is INF
    private Integer add(Integer n1, Integer n2){
        if(n1==null&&n2==null){
            return null;
        }
        if(n1==null){
            return n2;
        }
        if(n2==null){
            return n1;
        }
        return n1+n2;
    }

    // Constructor for the Bellman Ford algo
    public BellmanFord(Graph g, Graph.Vertex s){
        graph = g;
        source = s;
        dist = new Integer[graph.size()];
        lastEdge = new Deque[graph.size()];
        dist[s.getName()] = 0;
        queue = new LinkedList<>();
        present = new boolean[graph.size()];
        vertexmem = new Long[graph.size()];
        cycle = false;
        nochange = false;
    }

    // Function that implements a shortest path search
    public void findSP(){
        queue.addLast(source);
        queue.addLast(null);
        while (!queue.isEmpty()){
            if(cost>=graph.size()){
                cycle = true;
                return;
            }
            while (!queue.isEmpty()) {
                Graph.Vertex v = queue.removeFirst();
                if (null == v) break;
                present[v.getName()] = false;
                for (Graph.Edge e : v.adj) {
                    Graph.Vertex d = e.otherEnd(v);
                    if (compareTo(dist[d.getName()], add(dist[v.getName()], e.getWeight())) == 1) {
                        dist[d.getName()] = add(dist[v.getName()], e.getWeight());
                        Deque<Graph.Edge> ndeque = new LinkedList<>();
                        ndeque.addLast(e);
                        lastEdge[d.getName()] = ndeque;
                        if (!present[d.getName()]) {
                            queue.addLast(d);
                            present[d.getName()] = true;
                        }
                    } else if (compareTo(dist[d.getName()], add(dist[v.getName()], e.getWeight())) == 0) {
                        Deque<Graph.Edge> ndeque = lastEdge[d.getName()];
                        if (ndeque == null) {
                            ndeque = new LinkedList<>();
                            lastEdge[d.getName()] = ndeque;
                        }
                        if (!ndeque.contains(e)) ndeque.addLast(e);
                        if (!present[d.getName()]) {
                            queue.addLast(d);
                            present[d.getName()] = true;
                        }
                    }
                }
            }
            cost++;
            if (!queue.isEmpty()) {
                queue.addLast(null);
            }
        }
    }

    // Function that does a constrained shortest path check
    public void findSP(Integer maxCost){
        queue.addLast(source);
        while (!queue.isEmpty()&&cost<maxCost&&!nochange){
            if(cost==graph.size()){
                cycle = true;
                return;
            }
            nochange = true;
            queue.addLast(null);
            while (!queue.isEmpty()){
                Graph.Vertex v = queue.removeFirst();
                if(null==v)break;
                present[v.getName()] = false;
                for(Graph.Edge e:v.adj){
                    Graph.Vertex d = e.otherEnd(v);
                    if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==1){
                        dist[d.getName()] = add(dist[v.getName()],e.getWeight());
                        Deque<Graph.Edge> ndeque = new LinkedList<>();
                        ndeque.addLast(e);
                        lastEdge[d.getName()] = ndeque;
                        if(!present[d.getName()]){
                            queue.addLast(d);
                            present[d.getName()] = true;
                        }
                        nochange = false;
                    }
                }
            }
            cost++;
        }
    }

    // Function that counts the paths
    public long countPaths(Graph.Vertex v) {
        vertexmem = new Long[graph.size()];
        return countPathsRec(v);
    }

    // Function that counts the paths
    public long countPathsRec(Graph.Vertex v) {
        if (v == source) {
            return 1;
        }
        if (null != vertexmem[v.getName()]) {
            return vertexmem[v.getName()];
        }
        long count = 0;
        Deque<Graph.Edge> ndeque = lastEdge[v.getName()];
        for (Graph.Edge e : ndeque) {
            count = count + countPathsRec(e.otherEnd(v));
        }
        vertexmem[v.getName()] = count;
        return count;
    }

    // Function that prints the path
    public void printPaths(Graph.Vertex target){
        if(cycle) return;
        Deque<Graph.Vertex> deque = new LinkedList<>();
        printPaths(target, deque);
    }

    // Recursive function to print the paths
    private void printPaths(Graph.Vertex v, Deque<Graph.Vertex> deque){
        deque.addFirst(v);
        if(v==source){
            for(Graph.Vertex vertex:deque){
                System.out.print(vertex.toString());
                System.out.print(" ");
            }
            System.out.println();
            deque.removeFirst();
            return;
        }
        Deque<Graph.Edge> ndeque =lastEdge[v.getName()];
        for(Graph.Edge e:ndeque){
            printPaths(e.otherEnd(v),deque);
        }
        deque.removeFirst();
    }

    // Function that returns whethe this is cycle
    public boolean isCycle(){
        return cycle;
    }

    // Function that checks if there is a path post findSP()
    public boolean hasPath(Graph.Vertex v){
        return null != lastEdge[v.getName()];
    }

    // Recursive funciton to return weight of edges
    public int getWeight(Graph.Vertex v){
        if(v == source) return 0;
        Graph.Edge e = lastEdge[v.getName()].getFirst();
        return e.getWeight() + getWeight(e.otherEnd(v));
    }
}