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


/*
    Min Cost Max flow using the Push Relabel Framework.
 */


package cs6301.g00;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MinCostFlow {

    private HashMap<Graph.Edge,Integer> edgeFlow=new HashMap<>();
    private HashMap<Graph.Edge, Integer> capacity = new HashMap<>();
    private HashMap<Graph.Edge, Integer> cost=new HashMap<>();
    private HashMap<Graph.Vertex, Integer> p=new HashMap<>();
    private int[] excess;
    private int delta;
    private int residualCapacity;
    private Graph g;
    private Graph gf;
    private Graph.Vertex source;
    private Graph.Vertex sink;


    private int epsilon;
    private int[] heights;
    private int[] excessFlows;


    public MinCostFlow(Graph g, Graph.Vertex s, Graph.Vertex t, HashMap<Graph.Edge, Integer> capacity, HashMap<Graph.Edge, Integer> cost) {
        this.g=g;
        this.source=s;
        this.sink=t;
        this.capacity=capacity;
        this.cost=cost;
        System.out.println(source);
        System.out.println(sink);
        System.out.println(capacity);
        System.out.println(cost);
      


    }

    // Return cost of d units of flow found by cycle cancellation algorithm
    int cycleCancellingMinCostFlow(int d) {
        return 0;
    }

    // Return cost of d units of flow found by successive shortest paths
    int successiveSPMinCostFlow(int d) {
        return 0;
    }

    // Return cost of d units of flow found by cost scaling algorithm
    int costScalingMinCostFlow(int d) {
        return 0 ;
    }


/*
    Performs push operation.
     @param Edge is the input where excess from the edge.from vertex flow to edge.to Vertex.

 */

    public void push(Graph.Edge e){
        Graph.Vertex u=e.from;
        Graph.Vertex v=e.to;
        residualCapacity=capacity(e)-flow(e);
        delta=Math.min(excess[u.getName()],residualCapacity); //  residualCapacity is Cf.
        excess[u.getName()]=excess[u.getName()]-delta;
        excess[v.getName()]=excess[v.getName()]+delta;

    }

    /*
        Relabel Operation
        @param : input is Vertex u to be relabeled
     */

    public void relabel(Graph.Vertex u){
          int max=0;
         for(Graph.Edge e:u){
              Graph.Vertex v=e.otherEnd(u);
              if(p.get(v)-cost(e)-epsilon>max){
                  max=p.get(v)-cost(e)-epsilon;
              }
          }
          p.put(u,max);
    }

    /*
       Main function of refine operation is to cut down the value of epsilon by half.
       We currently  DO NOT use First Active Method as suggested in the Goldber-Tarjan paper.

       Also saturates all the edges with negative reduced cost. Helps in Maintaining the Consistent theme : NO negative cycles
     */
    public void refine() {
      epsilon=epsilon/2;
        for(Graph.Vertex u :gf){
            for(Graph.Edge e:u){
                if(RC(e)<0){
                    edgeFlow.put(e,cost(e));    // Saturating edges with negative reduced costs
                }
            }
        }

        // Currently naive implementation hoping to include First Active method in  it.
        for(Graph.Vertex u:gf){
            if(excess[u.getName()]>0){
                discharge(u);
            }
        }
    }

    /*
       Computes Reduced Cost
       @param input : Edge whose reduced cost needs to be computed.
       @param returns the computed Reduced cost of the edge.
    */

    public int RC(Graph.Edge e){
        return (cost.get(e)+p.get(e.from)+p.get(e.to));

    }

    /*
       Discharge method primarily handles push and relabel operations.
       @param input : Vertex to be discharged.

     */


    public void discharge(Graph.Vertex u){
        while(excess[u.getName()]>0){
           for(Graph.Edge e:u.adj){
                if(RC(e)<0){
                    push(e);
                }
           }
           if(excess[u.getName()]>0){
               relabel(u);
           }
        }
        }

    /*
         Method used to initialize epsilon value with the maximum cost amongst all edges and then
         successively apply refine operation until an epsilon optimal solution is found.

     */
    public void minCostCirculation() {

        epsilon = maxCost(cost); // setting highest cost value for epsilon
        while(epsilon>0)   /// epsilon    We initially started with the idea of using >1/n (n==|V|) but we couldn't port everything in time.
         {
            refine();
        }


    }

    /*
       Method used to compute the highest cost amongst all the edge costs.
       @param : return int value of highest cost.

     */
    public static int maxCost(Map mpp){
         Integer maximum=0;
         Iterator it=mpp.entrySet().iterator();
         while(it.hasNext()) {
             Map.Entry keyval = (Map.Entry)it.next();
             if(((int) keyval.getValue()>maximum)){
                maximum=(int) keyval.getValue();
             }

         }
         return maximum.intValue();
    }



    // flow going through edge e
    public int flow(Graph.Edge e) {
        return edgeFlow.get(e);
    }

    // capacity of edge e
    public int capacity(Graph.Edge e) {

        return capacity.get(e);
    }

    // cost of edge e
    public  int cost(Graph.Edge e) {
        return cost.get(e);
    }


}

