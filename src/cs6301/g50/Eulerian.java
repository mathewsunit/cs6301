/*
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 * on 09/16/2017.
 */

package cs6301.g50;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Eulerian {

	public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } 
        else
            in = new Scanner(System.in);

        Graph g = Graph.readGraph(in, true);
        Eulerian newEulerian = new Eulerian();
        if(!newEulerian.testEulerian(g))
        	System.out.println("Graph is not Eulerian");
        else
        	System.out.println("Graph is Eulerian");
	}
	
	/* 
    Method to check whether the Graph g is Eulerian or not. This method first checks if the graph has only
    a single strongly connected component. If that is the case, then it checks if all the vertices of
    the graph have inDegree = outDegree.
    */
	boolean testEulerian(Graph g) { 
		Graph.Vertex src = g.getVertex(1);// "true" because its a directed graph
        DFS scc= new DFS(g,src);
        int result = scc.stronglyConnectedComponents(g);
		if(result > 1)
		    return false;
		else {
	        for(Graph.Vertex current: g) {
			    if(current.getInDegree() != current.getOutDegree())
				    return false;
			}
		    return true;
		}
	}

}
