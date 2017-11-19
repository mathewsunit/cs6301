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

import cs6301.g50.Graph.Edge;
import cs6301.g50.Graph.Vertex;

import java.util.LinkedList;
import java.util.List;



public class Euler{
    int VERBOSE;    
    Graph g;
    List<Graph.Edge> tour;
    Graph.Vertex startVertex;
    EulerVertex[] ev;
    
    
    
    /* Constructor for the Class */
    Euler(Graph g, Graph.Vertex start) {
	    VERBOSE = 1;
	    tour = new LinkedList<>();
        this.g = g; 
        this.startVertex=start;
        
        // Create EulerVertices from GraphVertices.
	    ev = new EulerVertex[g.size()];
        for(Vertex u: g) {
        	EulerVertex newEv = new EulerVertex(u);
            ev[u.getName()] = newEv;
	        for(Edge e: u)
		        newEv.eadj.add(e);
	    }
    }
     
    
    /* Class to store information about a vertex in this algorithm */
    public static class EulerVertex{
    	Vertex u;
    	boolean finishTour;
    	List<Graph.Edge> eadj;
    	List<Graph.Edge> tour;
    	
    	EulerVertex(Vertex u) {
    	    eadj = new LinkedList<>();
    	    tour = new LinkedList<>();
    	    this.u = u;
    	    this.finishTour = false;
    	}
    }
    
    
    
    /* Function to get the corresponding Euler Vertex from a Graph Vertex */
    EulerVertex getVertex(Vertex u) {
	    return Vertex.getVertex(ev, u);
    }
    
    
    
    /* Function to find an Euler tour */
    public List<Graph.Edge> findEulerTour() {
	    findTours();
	    if(VERBOSE > 9) { printTours(); }
	    stitchTours();
	    return tour;
    }

    
    
    /* Function to test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {
    	Graph.Vertex src = g.getVertex(1);// "true" because its a directed graph
        DFS scc= new DFS(g,src);
        int result = scc.stronglyConnectedComponents(g);
		if(result > 1) {
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
		    return false;
		}else {
	        for(Graph.Vertex current: g) {
	        	int inDegree = current.revAdj.size();
	        	int outDegree = current.adj.size();
			    if( inDegree != outDegree ) {
			    	System.out.println("Graph is not Eulerian");
			    	System.out.println("Reason: inDegree = "+ inDegree + ", outDegree = " + outDegree + " at Vertex" + current);
				    return false;
				}
			}
		    return true;
		}
    }

    
    
    /* Helper Function to find tours starting at vertices with unexplored edges */
    void findTours(Vertex u, List<Graph.Edge> tour) {
    	EulerVertex eu = getVertex(u);
    	if(eu.eadj.size()==0)
    		return;
        Edge e = eu.eadj.remove(0);
        tour.add(e);
        u = e.otherEnd(u);
	    findTours(u, tour);
    }
    
    
    
    /* Function to find tours starting at vertices with unexplored edges */
    void findTours() {  
    	findTours(startVertex, getVertex(startVertex).tour);    	
    	for(EulerVertex u : ev)  				
    	    findTours(u.u, u.tour);
    }

    
    
    /* Function that Print tours found by findTours() in the following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     */
    void printTours() {
    	for(EulerVertex u : ev) {
    		if(u.tour.size() > 0) {
    		    System.out.print(u.u + ": ");
    		    System.out.println(u.tour);
    		}
    	}
    }

    
    
    /* Helper Function to Stitch tours into a single tour */
    void explore(Vertex u){
    	EulerVertex eu = getVertex(u);
    	eu.finishTour = true;
    	EulerVertex tmp = eu;
    	for (Edge e : eu.tour) {
    		tour.add(e);
    		tmp = getVertex(e.otherEnd(tmp.u));
    		if(tmp.tour.size() > 0 && !tmp.finishTour)
    			explore(tmp.u);
    	}   	
    }
    
    
    
    /* Function that Stitches tours into a single tour using the algorithm discussed in class */
    void stitchTours() {
    	explore(startVertex);
    }

    
    
    void setVerbose(int v) {
	    VERBOSE = v;
    }
}
