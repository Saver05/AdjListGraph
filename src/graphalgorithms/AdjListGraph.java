
package graphalgorithms;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**
 * @author Jack Frambes  <<<<---------------
 * Modified the code
 * 
 * @author timmermank1
 * Provided the initial code
 */
public class AdjListGraph {

    /**
     * Driver Method: Rather than have a whole driver class, we can place the
     * main method here to test our algorithms.
     * 
     * You should draw the graphs created by this code to know if your algorithms
     * are correct.
     *
     * @param args
     */
        public static void main(String[] args) {
        int[][] edgeList1 = {
            {0, 3, 12},
            {0, 1, 5},
            {1, 2, 3},
            {2, 3, 2},
            {3, 1, 7} };
        AdjListGraph g1 = new AdjListGraph(4, edgeList1);
        System.out.println(g1 + "\n\n");
        
        System.out.println("DFT should be: [ 0 1 2 3 ] \n");
        System.out.println("       DFT is: " + g1.depthFirstTraversal() +"\n");
        System.out.println("BFT should be: [ 0 1 3 2 ] \n");
        System.out.println("       BFT is: " + g1.breathFirstTraversal() + "\n");
        System.out.println("MST should be: [ (0,1) (1,2) (2,3) ] \n");
        System.out.println("       MST is: " + g1.minSpanningTree() + "\n");
        System.out.println("Dijkstrs should be: [0 5 8 10] \n");
        System.out.println("       Dijkstrs is: " + Arrays.toString(g1.dijkstras(0)) + "\n");
        
        
        int[][] edgeList2 = {
            {0, 1, 4},
            {0, 3, 7},
            {1, 2, 8},
            {2, 3, 3},
            {3, 4, 4},
            {4, 1, 12},
            {4, 5, 8},
            {5, 8, 3},
            {5, 3, 2},
            {5, 6, 2},
            {6, 7, 6},
            {7, 1, 4},
            {7, 8, 15},
            {7, 2, 3},
            {8, 9, 4},
            {8, 11, 6},
            {9, 0, 9},
            {9, 16, 3},
            {9, 10, 5},
            {11, 15, 8},
            {10, 12, 4},
            {12, 14, 6},
            {13, 15, 7},
            {13, 12, 3},
            {16, 10, 5}};
        AdjListGraph g2 = new AdjListGraph(17, edgeList2);
        System.out.println(g2 + "\n\n");
        
        System.out.println("DFT should be: <YOU FILL IN>\n");
        System.out.println("       DFT is: " + g2.depthFirstTraversal() +"\n");
        System.out.println("BFT should be: <YOU FILL IN> \n");
        System.out.println("       BFT is: " + g2.breathFirstTraversal() + "\n");
        System.out.println("MST should be: <YOU FILL IN> \n");
        System.out.println("       MST is: " + g2.minSpanningTree() + "\n");
        System.out.println("Dijkstrs should be: <YOU FILL IN> \n");
        System.out.println("       Dijkstrs is: " + Arrays.toString(g2.dijkstras(0)) + "\n");
        
        
    }

    /**
     * The node class is used for the linked list of neighbors.
     * value holds the integer that is the other vertex label
     * weight is the cost of the edge. Since this is undirected, 
     *            this matches for either direction
     * 
     * NO CHANGES NEEDED
     */
    private class Node {
        public int value;
        public Node next;
        public int weight;
        
        public Node(int value, int weight, Node next) {
            this.value = value;
            this.weight = weight;
            this.next = next;
        }
    }
    
    /**
     * This is a Vertex Object in the Graph.
     * label - is the integer representing the vertex. It is also its location in adjList.
     * marked - is a boolean that can be used when running traversals.
     * neighbors - is a Linked List of neighbors.
     * NO CHANGERS NEEDED
     */
    private class Vertex{
        public int label;
        public boolean marked;
        public Node neighbors; //points to the head of LL
        
        public Vertex(int value) {
            this.label = value;
            neighbors = null;
        }
    }
    
    
    

    //Data Fields
    Vertex[] adjList;
    //Constructors
    /**
     * The constructor establishes the graph by building the adjList.
     * numVertex lists how many vertex there are.
     * edges is a e x 3 matrix where e is the number of edges. Each row in the
     * matrix represents an edge in the graph. A row of [0,3, x] would indicate an
     * edge from vertex 0 to vertex 3 AND an edge from vertex 3 to vertex 0.
     * The value of X is the weight of the edge.
     *
     * @param numVertex
     * @param edges
     */
    public AdjListGraph(int numVertex, int[][] edges) {
        adjList = new Vertex[numVertex];
        for (int i=0;i<numVertex;i++)
        {
            adjList[i] = new Vertex(i);
        }
        for (int i =0;i< edges.length;i++)
        {
            addEdge(edges[i][0],edges[i][1],edges[i][2]);
        }
    }

    
    //Methods
    
    /**
     * The findNodeInsertAfter finds the location to insert the edge in the neighbor list.
     * It takes a Node that is the start of the Linked List and the weight.
     * It returns the Node that the edge should be inserted after.
     * 
     * @param startList - the start of the LinkedList
     * @return returns the node to insert after
     * 
     */
    
    private Node findNodeInsertAfter(Node startList, int weight)
    {
        if (startList.next == null)
        {
            return startList;
        }
        Node next = startList.next;
        Node prev = startList;
        while (next.weight<weight && next.next != null)
        {
            prev=next;
            next = next.next;
        }
        return prev;
    }
    
    /**
     * Adds an edge to the graph.
     * The edges should be added in weighted order.
     * Since this is an undirected graph the edge should be added in both direction.
     * This method should create two nodes. Then each node is added to the appropriate
     * linkedlist neighbors variable in Vertex object in weighted order.
     * @param vertex1 
     * @param vertex2
     * @param weight
     */
    public void addEdge(int vertex1, int vertex2, int weight)
    {
        if (adjList[vertex1].neighbors == null) //checks to make sure the linked-list is actually initialized
        {
            adjList[vertex1].neighbors = new Node(vertex2,weight,null);
        }
        else
        {
            Node after = findNodeInsertAfter(adjList[vertex1].neighbors,weight);
            after.next = new Node(vertex2,weight,after.next);
        }
        if (adjList[vertex2].neighbors == null) //checks to make sure the linked-list is actually initialized
        {
            adjList[vertex2].neighbors = new Node(vertex1,weight,null);
        }
        else
        {
            Node after = findNodeInsertAfter(adjList[vertex2].neighbors, weight);
            after.next = new Node(vertex1, weight, after.next);
        }
    }
    
    
    /**
     * sets all vertices data field marked to false.
     * 
     * NO CHANGES NEEDED.
     */
    private void unMarkAll(){
            for (Vertex adjList1 : adjList) {
                adjList1.marked = false;
            }
    }


    /**
     * complete a breath first traversal starting at vertex 0.
     * You may use ArrayList (which is also the Java queue).
     * As you process nodes, add them to a string that will be returned.
     * Bracket the string with square brackets.
     * @return a string representation of the order in which the vertex were
     *                  visited with square brackets around it.
     */
    public String breathFirstTraversal()
    {
        String r = "[ ";
        Queue<Vertex> q = new LinkedList<>();
        unMarkAll();
        adjList[0].marked = true;
        r = r +" "+ adjList[0].label;
        q.add(adjList[0]);
        while (!q.isEmpty())
        {
            Vertex v =q.poll();
            Node n = v.neighbors.next;
            while (n != null)
            {
                if (!adjList[n.value].marked)
                {
                    adjList[n.value].marked = true;
                    q.add(adjList[n.value]);
                    r = r + " " + adjList[n.value].label;
                }
                n = n.next;
            }
        }
        return r+" ]";
    }
    
     /**
     * Complete a depth first traversal starting at vertex 0.
     * As you process nodes, add them to a string that will be returned.
     * Bracket the string with square brackets.
     * You may use the Java stack data structure if you want.
     * @return a string representation of the order in which the vertex were
     * visited.
     */   
    public String depthFirstTraversal()
    {
        String r = "[";
        Stack<Vertex> s = new Stack<>();
        unMarkAll();
        for (int st =0;st<adjList.length;st++)
        {
            if (!adjList[st].marked)
            {
                s.push(adjList[st]);
                adjList[st].marked = true;
            }
            while(!s.isEmpty())
            {
                Vertex v = s.pop();
                r = r + " " + v.label;
                Node n = v.neighbors;
                while (n != null)
                {
                    if (!adjList[n.value].marked)
                    {
                        s.push(adjList[n.value]);
                        adjList[n.value].marked = true;
                    }
                    n = n.next;
                }
            }
        }
        return r+" ]";
    }
    
    /**
     * Complete a minimum spanning tree algorithm (Prim's or Kruskals).
     * @return a string with the edges in the minimum spanning tree
     * [ (0, 2) (2, 4) ... (5, 6) ]
     */
    public String minSpanningTree(){
        return "";
    }
    
    /**
     * Complete Dijkstras Algorithm.
     * Determine the distance from a given vertex to every other node 
     * using Dijkstra's algorithm.
     * 
     * @param vertex  The starting vertex as an integer value. The integer will be
     *                the location of the vertex in the adjList.
     * @return  the array of distances. 
     */
    public int[] dijkstras(int vertex){
        return new int[adjList.length];
    }
    
    /**
     * Creates a string representation of the Graph object.
     * 
     * NO CHANGES NEEDED
     */
    public String toString(){
        String graph = "";
            for (Vertex v : adjList) {
                graph += "[" + v.label + "]-> ";
                Node cur = v.neighbors;
                while (cur != null) {
                    graph += "| " + cur.value + " | " + cur.weight + " |-> ";
                    cur = cur.next;
                }//end while
                graph += "null\n"; // the "\n" creates a new line
            } //end for
        return graph;
    }
}
