package cs6301.g00;
import java.util.*;
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
    Relabel to front Implementation reused from our LP 7 submission. Relabel to front is used in the min cost max flow
    problem because we initially need an optimal flow and so we compute maximum flow first as suggested using the
    relabel to front algorithm.

 */


public class Flow {

    private Map<Graph.Edge, Integer> edgeFlow;
    private Map<Graph.Edge, Integer> edgeCapacity;
    private Graph.Vertex source;
    private Graph.Vertex sink;
    private int[] levels;
    private Map<Graph.Edge, Graph.Edge> reverseEdgeMap;
    private Graph graph;

    private int[] heights;
    private int[] excessFlows;


    public Flow(Graph g, Graph.Vertex s, Graph.Vertex t, Map<Graph.Edge, Integer> capacity) {
        this.source = s;
        this.sink = t;
        this.edgeCapacity = capacity;
        this.graph = g;
        levels = new int[g.size()];
        heights = new int[g.size()];
        excessFlows = new int[g.size()];
    }

    /*
     Initialization method for Preflow.
   */


    private void initNetworkForRelabel() {
        edgeFlow = new HashMap<>();
        for (Graph.Vertex vertex : graph) {
            for (Graph.Edge edge : vertex) {
                edgeFlow.put(edge, 0);
            }
        }
        Arrays.fill(heights, 0);
        Arrays.fill(excessFlows, 0);
        heights[source.getName()] = graph.size();
        for (Graph.Edge edge : source) {
            edgeFlow.put(edge, edgeCapacity.get(edge));
            Graph.Vertex otherEnd = edge.otherEnd(source);
            excessFlows[otherEnd.getName()] += flow(edge);
            makeBackEdge(source, otherEnd, edge.weight, flow(edge));
        }
    }



    /*
        Relabel to front method this method discharges nodes by maintaining a LinkedList.
        @param returns max flow computed by the relabelToFront algorithm
     */

    public int relabelToFront() {
        initNetworkForRelabel();
        int vertexWithExcessFlow = getVertexWithExcessFlow();
        while (vertexWithExcessFlow > 0) {
            //do something
            boolean pushSuccessful = pushFlowFrom(vertexWithExcessFlow);

            if (!pushSuccessful) {
                relabelVertex(vertexWithExcessFlow);
            }

            vertexWithExcessFlow = getVertexWithExcessFlow();
        }
        return excessFlows[sink.getName()];
    }

    /*
        Relabel method
     */

    private void relabelVertex(int vertexWithExcessFlow) {
        int minHeight = Integer.MAX_VALUE;
        Graph.Vertex vertex = graph.getVertex(vertexWithExcessFlow + 1);
        for (Graph.Edge edge : vertex) {
            if (flow(edge) == capacity(edge)) continue;
            Graph.Vertex otherEnd = edge.otherEnd(vertex);
            minHeight = Math.min(minHeight, heights[otherEnd.getName()]);
            heights[vertex.getName()] = minHeight + 1;
        }
    }


    /*
        Push Method
        @param inputs : Vertex with excess flow from where flow is to be pushed.
     */


    private boolean pushFlowFrom(int vertexWithExcessFlow) {
        Graph.Vertex vertex = graph.getVertex(vertexWithExcessFlow + 1);
        for (Graph.Edge edge : vertex) {
            if (flow(edge) >= capacity(edge)) continue;
            Graph.Vertex otherEnd = edge.otherEnd(vertex);
            if (heights[vertex.getName()] > heights[otherEnd.getName()]) {
                int flowToPush = Math.min(capacity(edge) - flow(edge), excessFlows[vertex.getName()]);
                excessFlows[otherEnd.getName()] += flowToPush;
                edgeFlow.put(edge, edgeFlow.get(edge) + flowToPush);
                excessFlows[vertex.getName()] -= flowToPush;
                makeBackEdge(vertex, otherEnd, -edge.weight, flowToPush);
                return true;
            }
        }
        return false;
    }

    /*
        Method used to create backedges.
        @param inputs : to vertex, from vertex, capacity, flow.

     */

    private void makeBackEdge(Graph.Vertex to, Graph.Vertex from, int weight, int flow) {
        for (Graph.Edge edge : from) {
            if (edge.otherEnd(from).equals(to)) {
                if (edge.getName() == -1) {
                    int cap = edgeCapacity.get(edge) - flow;
                    edgeCapacity.put(edge, cap < 0 ? 0 : cap);
                    edgeFlow.put(edge, 0);
                    return;
                }
            }
        }
        Graph.Edge revEdge = new Graph.Edge(from, to, weight);
        revEdge.name = -1;
        edgeFlow.put(revEdge, 0);
        edgeCapacity.put(revEdge, flow);
        from.adj.add(revEdge);
        to.revAdj.add(revEdge);
    }

    /*
        Method used to get vertex with excess flow
        @param returns name of the next vertex with excess flow, if all are exhausted returns 0.
     */

    private int getVertexWithExcessFlow() {
        for (Graph.Vertex vertex : graph) {
            if (vertex.equals(source) || vertex.equals(sink)) continue;
            if (excessFlows[vertex.getName()] > 0) return vertex.getName();
        }
        return 0;
    }


    // flow going through edge e
    public int flow(Graph.Edge e) {
        return edgeFlow.getOrDefault(e, 0);
    }

    // capacity of edge e
    public int capacity(Graph.Edge e) {
        return edgeCapacity.getOrDefault(e, 0);
    }

}