/*
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 */

package cs6301.g50;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    public int count=0;
    public int time=0;
    public int componentNumber=0;
    LinkedList<Graph.Vertex> decFinlist = new LinkedList<>();


    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }else
            in = new Scanner(System.in);

        Graph g = Graph.readGraph(in, true);
        Graph.Vertex src = g.getVertex(1);// "true" because its a directed graph
        DFS scc= new DFS(g,src);
        int result = scc.stronglyConnectedComponents(g);
        System.out.println("Number of Strongly Connected Components");
        System.out.println(result);
    }

    
    
    /* Class to store information about a vertex in this algorithm */
    static class DFSVertex {
        boolean seen;
        Graph.Vertex parent;
        int discoveryTime=0;  // discovery Time of vertex from source
        int finishTime=0;
        int cno=0;

        DFSVertex(Graph.Vertex u) {
            seen = false;
            parent = null;
            discoveryTime = 0;
            finishTime = 0;
            cno=0;
        }
    }
    
    
    
    Graph.Vertex src;
    int stronglyConnectedComponents(Graph g){
        ArrayIterator<Graph.Vertex> it =  g.iterator();
        dfs(it,false);
        for(Graph.Vertex u : g) {
            List<Graph.Edge> temp;
            temp=u.adj;
            u.adj=u.revAdj;
            u.revAdj=temp;
        }
        Graph.Vertex[] v  = new Graph.Vertex[decFinlist.size()];
        for (int i =0 ; i < decFinlist.size(); i++)
            v[i] = decFinlist.get(i);
        ArrayIterator<Graph.Vertex> newIt = new ArrayIterator<Graph.Vertex>(v);
        src = v[0];
        reinitialize(src);
        componentNumber = 0;
        dfs(newIt,true);
        return componentNumber;
    }

    
    
    public DFS(Graph g, Graph.Vertex src){
        super(g);
        this.src=src;
        node = new DFSVertex[g.size()];
        // Creating an array for storing vertex properties
        for(Graph.Vertex u : g)
            node[u.getName()]= new DFSVertex(u);
        
        //Setting source to discovery time to be Zero
        getVertex(src).discoveryTime=0;
    }

    
    
    // Setting all the vertices to default values. To do DFS from a new source.
    void reinitialize(Graph.Vertex newSource){
        src=newSource;
        for(Graph.Vertex u : g) {
            DFSVertex d = getVertex(u);
            d.seen = false;
            d.parent = null;
            d.discoveryTime = 0;
            d.cno=0;
        }
        getVertex(src).discoveryTime=0;
    }

    

    public void  dfs(ArrayIterator<Graph.Vertex> it, boolean transpose){
        time =0;
        // Creates a new linked list for GraphVertex and makes all vertices are not seen
        for(Graph.Vertex u: g)
            node[u.getName()] = new DFSVertex(u);
        while(it.hasNext()){
            Graph.Vertex u = it.next();
            if(!seen(u)){
                componentNumber++;
                DfsVisit(u,transpose);
            }
        }
    }

    
    
    /*
     @param u is the vertex to be visited
     @param transpose is used as a flag to add elements to decFinlist only before we tranpose the graph.
    */
    void DfsVisit(Graph.Vertex u,boolean transpose) {
        DFSVertex du = getVertex(u);
        du.seen = true;
        du.discoveryTime = ++time;
        du.cno = componentNumber;
        for (Graph.Edge e : u) {
            DFSVertex v = getVertex(e.otherEnd(u));
            if (!v.seen) {
                v.parent=getParent(u);
                DfsVisit(e.otherEnd(u), transpose);
            }
            du.finishTime = ++time;
        }
        if (!transpose)
            decFinlist.addFirst(u);
    }

    
    
    boolean seen(Graph.Vertex u){
        return getVertex(u).seen;
    }

    
    
    Graph.Vertex getParent(Graph.Vertex u){
        return getVertex(u).parent;
    }

    
    
    int discoveryTime(Graph.Vertex u){
        return getVertex(u).discoveryTime;
    }

    
    
    int distance(Graph.Vertex u){
        return getVertex(u).finishTime;
    }

}




