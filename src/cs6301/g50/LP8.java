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


// Sample driver for LP8
package cs6301.g00;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


public class LP8 {
    static int VERBOSE = 0;
    public static void main(String[] args) throws Exception {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        if(args.length > 1) {
            VERBOSE = Integer.parseInt(args[1]);
        }

        Graph g = Graph.readDirectedGraph(in);
        int s = in.nextInt();
        int t = in.nextInt();
        HashMap<Edge,Integer> capacity = new HashMap<>();
        HashMap<Edge,Integer> cost = new HashMap<>();
        int[] arr = new int[1 + g.edgeSize()];
        for(int i=1; i<=g.edgeSize(); i++) {
            arr[i] = 1;   // default capacity
        }
        while(in.hasNextInt()) {
            int i = in.nextInt();
            int cap = in.nextInt();
            arr[i] = cap;
        }

        Vertex src = g.getVertex(s);
        Vertex target = g.getVertex(t);

        for(Vertex u: g) {
            for(Edge e: u) {
                capacity.put(e, arr[e.getName()]);
                cost.put(e, e.getWeight());
            }
        }

        System.out.println("Printing Original graph");
        Iterator it =g.iterator();
        while (it.hasNext()){
            Graph.Vertex u = (Graph.Vertex) it.next();
            System.out.print(u.adj);
            System.out.print(u.revAdj);
            System.out.println();
        }
        Timer timer = new Timer();
        // Find max-flow first and then a min-cost max-flow
        Flow f = new Flow(g, src, target, capacity);
        int value = f.relabelToFront();
        //MinCostFlow min = new MinCostFlow(g,src,target,capacity,cost);

        System.out.println("Value of Max flow");
        System.out.println(value);// Prints result of max flow
        MinCostFlow mcf = new MinCostFlow(g, src, target, capacity, cost);
        //System.out.println(mcf.maxCost(cost));   // Prints value of intial value of epsilon
        int minCost = mcf.costScalingMinCostFlow(value);
        System.out.println("Printing value computed from minCost routine");
        System.out.println(minCost);



        if(VERBOSE > 0) {
            for(Vertex u: g) {
                System.out.print(u + " : ");
                for(Edge e: u) {
                       if(mcf.flow(e) != 0) { System.out.print(e + ":" + mcf.flow(e) + "/" + mcf.capacity(e) + "@" + mcf.cost(e) + "| "); }
                }
                System.out.println();
            }
        }

        System.out.println(timer.end());
        System.out.println("Value of min cost max flow is :");
        System.out.println(minCost);
    }
}

