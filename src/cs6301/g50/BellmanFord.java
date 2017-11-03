package cs6301.g50;

import java.util.*;
import cs6301.g00.Graph;

public class BellmanFord {
    private Graph graph;
    private Graph.Vertex source;
    private Integer[] dist;
    private Deque<Graph.Edge>[] lastEdge;
    private Deque<Graph.Vertex> queue;
    private int cost;
    private boolean[] present;
    private boolean cycle;

    private int compareTo(Integer n1, Integer n2){
        if(n1==null&&n2==null){
            return 0;
        }
        if(n1==null){
            return -1;
        }
        if(n2==null){
            return 1;
        }
        return n1.compareTo(n2);
    }


    private Integer add(Integer n1, Integer n2){
        if(n1==null&&n2==null){
            return null;
        }
        if(n1==null){
            return n2;
        }
        if(n2==null){
            return n1;
        }
        return n1+n2;
    }

    public BellmanFord(Graph g, Graph.Vertex s){
        graph = g;
        source = s;
        dist = new Integer[graph.size()];
        lastEdge = new Deque[graph.size()];
        dist[s.getName()] = 0;
        queue = new LinkedList<>();
        present = new boolean[graph.size()];
        cycle = false;
    }

    public void findSP(){
        //Algorithm
        queue.addLast(source);
        while (!queue.isEmpty()){
            if(cost==graph.size()){
                cycle = true;
                return;
            }
            Graph.Vertex v = queue.removeFirst();
            present[v.getName()] = true;
            relax(v);
            cost++;
        }
    }

    public void relax(Graph.Vertex v){
        for(Graph.Edge e:v.adj){
            Graph.Vertex d = e.otherEnd(v);
            if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==-1){
                dist[d.getName()] = add(dist[v.getName()],e.getWeight());
                Deque<Graph.Edge> ndeque = new LinkedList<>();
                ndeque.addLast(e);
                lastEdge[d.getName()] = ndeque;
                if(!present[d.getName()]){
                    queue.addLast(d);
                    present[d.getName()] = true;
                }
            }else if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==0){
                Deque<Graph.Edge> ndeque = lastEdge[d.getName()];
                if(ndeque == null){
                    ndeque = new LinkedList<>();
                    lastEdge[d.getName()] = ndeque;
                }
                ndeque.addLast(e);
                if(!present[d.getName()]){
                    queue.addLast(d);
                    present[d.getName()] = true;
                }
            }
        }
    }
}