
package cs6301.g00;

import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

import java.util.HashMap;

public class GraphHash<V,E> {
    Graph g;
    // Algorithm uses hash tables for storing information about vertices and edges
    HashMap<Vertex,V> vertexMap;
    HashMap<Edge,E> edgeMap;

    public GraphHash(Graph g) {
	this.g = g;
	vertexMap = new HashMap<>();
	edgeMap = new HashMap<>();
    }

    V getVertex(Vertex u) {
	return vertexMap.get(u);
    }

    V putVertex(Vertex u, V value) {
	V res = vertexMap.put(u, value);
	return res;
    }
    
    E getEdge(Edge e) {
	return edgeMap.get(e);
    }

    E putEdge(Edge e, E value) {
	return edgeMap.put(e, value);
    }
}
