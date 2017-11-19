/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 **/

package cs6301.g50;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SP6Q4 extends GraphAlgorithm<SP6Q4.primVertex> {


    static class primVertex {
        boolean seen;
        Graph.Vertex parent;
        primVertex(Graph.Vertex u) {
            seen = false;
            parent = null;

        }
    }

    private Graph.Vertex src;

    SP6Q4(Graph g){
        super(g);
        this.src = src;
        node = new primVertex[g.size()];
        for(Graph.Vertex u: g) {
            node[u.getName()] = new primVertex(u);
        }
    }

    public int Prim1(Graph g,Graph.Vertex src) {

        Comparator<Graph.Edge> edgeComparator = new Comparator<Graph.Edge>() {
            @Override
            // Overriding the default comparator in java'stack Priority Queue
            // by custom comparator to compare our graph edges.
            public int compare(Graph.Edge e1, Graph.Edge e2) {

                if(e1.weight<e2.weight) {
                    return -1;
                }
                if(e1.weight>e2.weight){
                    return 1;
                }
                else return 0;

            }
        };

        // creating a new priority queue to store all the edges
        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(edgeComparator);

        for (Graph.Vertex u : g) {
            node[u.getName()] = new primVertex(u);
        }
        // Creating a variable to add all the weights in MST.
        int wmst = 0;
        Graph.Vertex u, v;
        primVertex source = node[src.getName()];
        System.out.print(src);

        // Making all the vertices as false in the beginning.
        for (Graph.Vertex everyU : g) {
            primVertex pu = getVertex(everyU);
            pu.seen = false;
            pu.parent = null;
        }
        visitU(src);
        wmst = 0;
        for (Graph.Edge e : src.adj) {
            pq.add(e);
        }
        while (!pq.isEmpty()) {
            Graph.Edge e = pq.remove();
            if (seen(e.from) && seen(e.to)) continue;
            if(seen(e.from)){      //u is already in the MST
                u = e.from;         //v is not in the MST
                v = e.to;

            }
            else{
                u = e.to;
                v = e.from;
            }

            visitV(u, v); // will also set the parent of v as u apart from marking v as visited.
            wmst += e.weight;  // Counting the total weight of MST to be returned later.

            for (Graph.Edge e2 : v.adj) {
                if (!seen(e2.otherEnd(v))) {
                    pq.add(e2);
                }
            }
        }
        return wmst;
    }

    /*
    Only used to mark vertex " u " as visited.
     */
    void visitU(Graph.Vertex u) {
        primVertex pu = getVertex(u);
        pu.seen = true;
    }

    /*
    Only used to mark vertex " v " as visited and also set its parent as "u".
     */

    void visitV(Graph.Vertex u, Graph.Vertex v) {
        primVertex pv = getVertex(v);
        pv.seen = true;
        pv.parent = u;
    }

    /*
    Method helps in keeping code simple by
     */
    boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

    Graph.Vertex getParent(Graph.Vertex u) {
        return getVertex(u).parent;
    }



    /*
       Picture for the graph used for this example is included in the submission.
     */
    public static void main(String [] args){
        
        // MST weight for the below Graph is 42 (picture included in submission).
        String s ="7 11\n" +
                "1 2 5\n" +
                "2 3 13\n" +
                "3 4 6\n" +
                "4 5 14\n" +
                "5 6 4\n" +
                "3 5 11\n" +
                "5 2 8\n" +
                "5 7 8\n" +
                "6 7 10\n" +
                "7 2 12\n" +
                "1 7 17";
        Scanner in = new Scanner(s);
        Graph g = Graph.readGraph(in);
        SP6Q4 q4 = new SP6Q4(g);
        Graph.Vertex src = g.getVertex(3); // Change the source if needed by putting in a different vertex number.
        int mstWeight=q4.Prim1(g,src);
        System.out.println(" Minimum Spanning Tree Weight is : "+mstWeight);
    }
}
