package cs6301.g50;

import cs6301.g00.Graph;

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

public class Djikstra {
    private Graph graph;
    private Graph.Vertex source;
    private Integer[] dist;
    private Deque<Graph.Edge>[] lastEdge;
    private PriorityQueue<Graph.Edge> queue;
    private boolean[] visited;
    private HashMap<Graph.Vertex,Integer> vertexRewardMap;

    public class VertexComparator implements Comparator<Graph.Edge>
    {
        public int compare(Graph.Edge x, Graph.Edge y)
        {
            return compareTo(x.getWeight(),y.getWeight());
        }
    }

    private int compareTo(Integer n1, Integer n2){
        if(n1==null&&n2==null){
            return 0;
        }
        if(n1==null){
            return 1;
        }
        if(n2==null){
            return -1;
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

    public Djikstra(Graph g, Graph.Vertex s){
        graph = g;
        source = s;
        dist = new Integer[graph.size()];
        lastEdge = new Deque[graph.size()];
        dist[s.getName()] = 0;
        queue = new PriorityQueue<>(new VertexComparator());
        visited = new boolean[graph.size()];
    }

    public void findSP(){
        for(Graph.Edge e:source){
            queue.add(e);
        }
        visited[source.getName()] = true;
        while (!queue.isEmpty()){
            Graph.Edge e = queue.poll();
            Graph.Vertex v = e.fromVertex();
            if(!visited[v.getName()]){
                v = e.toVertex();
            }
            Graph.Vertex d = e.otherEnd(v);
            visited[d.getName()] = true;
            if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==1){
                dist[d.getName()] = add(dist[v.getName()],e.getWeight());
                Deque<Graph.Edge> ndeque = new LinkedList<>();
                ndeque.addLast(e);
                lastEdge[d.getName()] = ndeque;
            }else if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==0){
                Deque<Graph.Edge> ndeque = lastEdge[d.getName()];
                if(ndeque == null){
                    ndeque = new LinkedList<>();
                    lastEdge[d.getName()] = ndeque;
                }
                ndeque.addLast(e);
            }
            for(Graph.Edge edge:d){
                if(!visited[edge.otherEnd(d).getName()]){
                    queue.add(edge);
                }
            }
        }
    }

    public boolean hasPath(Graph.Vertex v){
        return null != lastEdge[v.getName()];
    }

    public int getReward(Graph.Vertex target){
        if(source == target) return vertexRewardMap.get(source);
        Deque<Graph.Edge> ndeque =lastEdge[target.getName()];
        int nMax = 0;
        for(Graph.Edge e:ndeque){
            int val = getReward(e.otherEnd(target));
            if(val>nMax){
                nMax = val;
            }
        }
        return nMax+vertexRewardMap.get(target);
    }

    public void setVertexRewardMap(HashMap<Graph.Vertex,Integer> vertexRewardMap){
        this.vertexRewardMap = vertexRewardMap;
    }
}