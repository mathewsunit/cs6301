package g50;

import java.util.*;

import g50.Graph.*;
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

public class Flow {

    private Map<Edge, Integer> edgeFlow;
    private Map<Edge, Integer> edgeCapacity;
    private Vertex source;
    private Vertex sink;
    private int[] levels;
    private Map<Edge, Edge> reverseEdgeMap;
    private g50.Graph graph;

    private int[] heights;
    private int[] excessFlows;


    public Flow(g50.Graph g, Vertex s, Vertex t, Map<Edge, Integer> capacity) {
        this.source = s;
        this.sink = t;
        this.edgeCapacity = capacity;
        this.graph = g;
        levels = new int[g.size()];
        heights = new int[g.size()];
        excessFlows = new int[g.size()];
    }

    /********************************************************************************************/

    private void initNetworkForRelabel() {
        edgeFlow = new HashMap<>();
        for (Vertex vertex : graph) {
            for (Edge edge : vertex) {
                edgeFlow.put(edge, 0);
            }
        }
        Arrays.fill(heights, 0);
        Arrays.fill(excessFlows, 0);
        heights[source.getName()] = graph.size();
        for (Edge edge : source) {
            edgeFlow.put(edge, edgeCapacity.get(edge));
            Vertex otherEnd = edge.otherEnd(source);
            excessFlows[otherEnd.getName()] += flow(edge);
            makeBackEdge(source, otherEnd, edge.weight, flow(edge));
        }
    }

    // Return max flow found by relabelToFront algorithm
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
        cleanUpGraph();
        return excessFlows[sink.getName()];
    }

    private void cleanUpGraph() {
        for (Vertex v : graph) {
            List<Edge> toRemove = new ArrayList<>();
            for (Edge edge : v) {
                if (edge.getName() == -1) {
                    toRemove.add(edge);
                }
            }
            for (Edge edge : v.revAdj) {
                if (edge.getName() == -1) {
                    toRemove.add(edge);
                }
            }
            v.adj.removeAll(toRemove);
            v.revAdj.removeAll(toRemove);
        }
    }

    private void relabelVertex(int vertexWithExcessFlow) {
        // Todo Start here
        int minHeight = Integer.MAX_VALUE;
        Vertex vertex = graph.getVertex(vertexWithExcessFlow + 1);
        for (Edge edge : vertex) {
            if (flow(edge) == capacity(edge)) continue;
            Vertex otherEnd = edge.otherEnd(vertex);
            minHeight = Math.min(minHeight, heights[otherEnd.getName()]);
            heights[vertex.getName()] = minHeight + 1;
        }
    }

    private boolean pushFlowFrom(int vertexWithExcessFlow) {
        Vertex vertex = graph.getVertex(vertexWithExcessFlow + 1);
        for (Edge edge : vertex) {
            if (flow(edge) >= capacity(edge)) continue;
            Vertex otherEnd = edge.otherEnd(vertex);
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

    private void makeBackEdge(Vertex to, Vertex from, int weight, int flow) {
        for (Edge edge : from) {
            if (edge.otherEnd(from).equals(to)) {
                if (edge.getName() == -1) {
                    int cap = edgeCapacity.get(edge) - flow;
                    edgeCapacity.put(edge, cap < 0 ? 0 : cap);
                    edgeFlow.put(edge, 0);
                    return;
                }
            }
        }
        Edge revEdge = new Edge(from, to, weight);
        revEdge.name = -1;
        edgeFlow.put(revEdge, 0);
        edgeCapacity.put(revEdge, flow);
        from.adj.add(revEdge);
        to.revAdj.add(revEdge);
    }

    private int getVertexWithExcessFlow() {
        for (Vertex vertex : graph) {
            if (vertex.equals(source) || vertex.equals(sink)) continue;
            if (excessFlows[vertex.getName()] > 0) return vertex.getName();
        }
        return 0;
    }

    private void initNetworkForDinitz() {
        // initializing flows to zero
        edgeFlow = new HashMap<>();
        for (Vertex vertex : graph) {
            for (Edge edge : vertex) {
                edgeFlow.put(edge, 0);
            }
        }
    }

    private boolean hasMoreFlow() {
        assignLevels();
        // if level assigning reached sink, then there can be more flow.
        return levels[sink.getName()] > 0;
    }

    private void assignLevels() {
        Arrays.fill(levels, -1);
        levels[source.getName()] = 0;

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            for (Edge edge : current.adj) {
                Vertex otherEnd = edge.otherEnd(current);
                if (levels[otherEnd.getName()] < 0 && flow(edge) < capacity(edge)) {  // this simulates back edge, while assigning levels
                    levels[otherEnd.getName()] = 1 + levels[current.getName()];
                    queue.add(otherEnd);
                }
            }
        }
    }

    private int addMoreFlow(Vertex start, Vertex end, int currentFlow) {
        //reached end
        if (start.equals(end)) return currentFlow;

        for (Edge edge : start.adj) {
            Vertex otherEnd = edge.otherEnd(start);
            if (levels[otherEnd.getName()] == levels[start.getName()] + 1 && flow(edge) < capacity(edge)) {
                // to see if current edge is the bottle neck in the flow.
                int bottleNeckFlow = Math.min(currentFlow, capacity(edge) - flow(edge));
                int forwardBottleNeckFlow = addMoreFlow(otherEnd, end, bottleNeckFlow);
                if (forwardBottleNeckFlow > 0) {
                    edgeFlow.put(edge, edgeFlow.get(edge) + forwardBottleNeckFlow);
                    return forwardBottleNeckFlow;
                }

            }
        }
        return 0;
    }


    /********************************************************************************************/

    // Return max flow found by Dinitz's algorithm
    public int dinitzMaxFlow() {
        initNetworkForDinitz();
        int finalMaxFlow = 0;
        if (source.equals(sink)) return -1;

        while (hasMoreFlow()) {
            int addedFlow = addMoreFlow(source, sink, Integer.MAX_VALUE);
            finalMaxFlow += addedFlow;
        }
        return finalMaxFlow;
    }


    // flow going through edge e
    public int flow(g50.Graph.Edge e) {
        return edgeFlow.getOrDefault(e, 0);
    }

    // capacity of edge e
    public int capacity(g50.Graph.Edge e) {
        return edgeCapacity.getOrDefault(e, 0);
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        Set<Vertex> sSideVertices = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(source);
        sSideVertices.add(source);
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
//            if(sSideVertices.contains(current))
            for (Edge edge : current) {
                if (flow(edge) != capacity(edge)) {
                    if (!sSideVertices.contains(edge.otherEnd(current))) {
                        queue.add(edge.otherEnd(current));
                        sSideVertices.add(edge.otherEnd(current));
                    }
                }
            }
        }
        return sSideVertices;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        Set<Vertex> tSideVertices = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(sink);
        tSideVertices.add(sink);
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            for (Edge edge : current.revAdj) {
                if (flow(edge) != capacity(edge)) {
                    queue.add(edge.otherEnd(current));
                    tSideVertices.add(edge.otherEnd(current));
                }
            }
        }
        return tSideVertices;
    }

    public boolean verify() {
        // checking for a path in the network after flows are assigned.
        return !canReachSink(source) && checkEdgeFlows() && checkVertexInnOutFlows();
    }

    private boolean checkVertexInnOutFlows() {
        for (Vertex vertex : graph) {
            if (vertex.equals(source) || vertex.equals(sink))
                continue;
            int netflow = 0;
            for (Edge e : vertex) {
                netflow += flow(e);
            }
            List<Edge> inflowEdges = vertex.revAdj;
            for (Edge e : inflowEdges) {
                netflow -= flow(e);
            }
            return netflow == 0;
        }
        return false;
    }

    private boolean checkEdgeFlows() {
        for (Vertex vertex : graph) {
            for (Edge e : vertex) {
                if (flow(e) > capacity(e)) return false;
            }
        }
        return true;
    }

    private boolean canReachSink(Vertex vertex) {
        if (vertex.equals(sink)) return true;
        for (Edge edge : vertex) {
            Vertex otherEnd = edge.otherEnd(vertex);
            if (flow(edge) < capacity(edge)) return canReachSink(otherEnd);
        }
        return false;
    }
}