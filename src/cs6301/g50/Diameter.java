/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 * on 8/29/2017.
 **/

package cs6301.g50; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Diameter {

    static LinkedList<Graph.Vertex> diameter(Graph g) {
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(g);

        Graph.Vertex furthest = breadthFirstSearch.doBfs(g.getVertex(1));
        breadthFirstSearch.resetVertexStates();
        furthest = breadthFirstSearch.doBfs(furthest);

        return breadthFirstSearch.getDiameterPath(furthest);
    }

    public static void main(String[] args) throws FileNotFoundException {
        int evens = 0;
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readGraph(in);

        LinkedList<Graph.Vertex> list = diameter(g);
        System.out.println("Furthest Vertex " + list);
    }
}

/******************************
 $ java cs6301.g00.BreadthFirstSearch
 10 9
 1 3 1
 4 3 1
 4 1 1
 2 6 1
 6 7 1
 7 2 1
 8 3 1
 8 9 1
 10 9 1

 Output:
 Input Graph has 3 components:
 1 [ 1 ] :(1,3) (4,1)
 2 [ 2 ] :(2,6) (7,2)
 3 [ 1 ] :(1,3) (4,3)
 4 [ 1 ] :(4,3) (4,1)
 5 [ 3 ] :
 6 [ 2 ] :(2,6) (6,7)
 7 [ 2 ] :(6,7) (7,2)

 */