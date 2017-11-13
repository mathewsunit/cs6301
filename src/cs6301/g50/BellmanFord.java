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
    private boolean nochange;

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

    public BellmanFord(Graph g, Graph.Vertex s){
        graph = g;
        source = s;
        dist = new Integer[graph.size()];
        lastEdge = new Deque[graph.size()];
        dist[s.getName()] = 0;
        queue = new LinkedList<>();
        present = new boolean[graph.size()];
        cycle = false;
        nochange = false;
    }

    public void findSP(){
        queue.addLast(source);
        while (!queue.isEmpty()){
            if(cost>=graph.size()){
                cycle = true;
                return;
            }
            Graph.Vertex v = queue.removeFirst();
            present[v.getName()] = false;
            for(Graph.Edge e:v.adj){
                Graph.Vertex d = e.otherEnd(v);
                if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==1){
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
            cost++;
        }
    }

    public void findSP(Integer maxCost){
        queue.addLast(source);
        while (!queue.isEmpty()&&cost<maxCost&&!nochange){
            if(cost==graph.size()){
                cycle = true;
                return;
            }
            nochange = true;
            queue.addLast(null);
            while (!queue.isEmpty()){
                Graph.Vertex v = queue.removeFirst();
                if(null==v)break;
                present[v.getName()] = false;
                for(Graph.Edge e:v.adj){
                    Graph.Vertex d = e.otherEnd(v);
                    if(compareTo(dist[d.getName()],add(dist[v.getName()],e.getWeight()))==1){
                        dist[d.getName()] = add(dist[v.getName()],e.getWeight());
                        Deque<Graph.Edge> ndeque = new LinkedList<>();
                        ndeque.addLast(e);
                        lastEdge[d.getName()] = ndeque;
                        if(!present[d.getName()]){
                            queue.addLast(d);
                            present[d.getName()] = true;
                        }
                        nochange = false;
                    }
                }
            }
            cost++;
        }
    }

    public int countPaths(Graph.Vertex v){
        if(v==source){
            return 1;
        }
        int count = 0;
        Deque<Graph.Edge> ndeque =lastEdge[v.getName()];
        for(Graph.Edge e:ndeque){
            count = count + countPaths(e.otherEnd(v));
        }
        return count;
    }

    public void printPaths(Graph.Vertex target){
        if(cycle) return;
        Deque<Graph.Vertex> deque = new LinkedList<>();
        printPaths(target, deque);
    }

    public void printPaths(Graph.Vertex v, Deque<Graph.Vertex> deque){
        deque.addFirst(v);
        if(v==source){
            for(Graph.Vertex vertex:deque){
                System.out.print(vertex.toString());
                System.out.print(" ");
            }
            System.out.println();
            deque.removeFirst();
            return;
        }
        Deque<Graph.Edge> ndeque =lastEdge[v.getName()];
        for(Graph.Edge e:ndeque){
            printPaths(e.otherEnd(v),deque);
        }
        deque.removeFirst();
    }

    public boolean isCycle(){
        return cycle;
    }

    public boolean hasPath(Graph.Vertex v){
        if(null==lastEdge[v.getName()]){
            return false;
        }
        return true;
    }

    public int getWeight(Graph.Vertex v){
        if(v == source) return 0;
        Graph.Edge e = lastEdge[v.getName()].getFirst();
        return e.getWeight() + getWeight(e.otherEnd(v));
    }
}