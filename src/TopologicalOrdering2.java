import java.util.*;

// data structure to store graph edges
class Edge
{
    int source, dest;

    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
    }
};

// class to represent a graph object
class Graph2 {
    // A List of Lists to represent an adjacency list
    List<List<Integer>> adjList = null;

    // stores indegree of a vertex
    List<Integer> indegree = null;

    // Constructor
    Graph2(List<Edge> edges, int N) {
        adjList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adjList.add(i, new ArrayList<>());
        }

        // initialize indegree of each vertex by 0
        indegree = new ArrayList<>(Collections.nCopies(N, 0));

        // add edges to the undirected graph
        for (int i = 0; i < edges.size(); i++) {
            int src = edges.get(i).source;
            int dest = edges.get(i).dest;

            // add an edge from source to destination
            adjList.get(src).add(dest);

            // increment in-degree of destination vertex by 1
            indegree.set(dest, indegree.get(dest) + 1);
        }
    }

}
class Main
{
    // Recursive function to find all topological orderings of a given DAG
    public static void findAllTopologicalOrders(Graph2 graph, List<Integer> path,
                                                boolean[] discovered, int N)
    {
        // do for every vertex
        for (int v = 0; v < N; v++)
        {
            // proceed only if in-degree of current node is 0 and
            // current node is not processed yet
            if (graph.indegree.get(v) == 0 && !discovered[v])
            {
                // for every adjacent vertex u of v, reduce in-degree of u by 1
                for (int u: graph.adjList.get(v)) {
                    graph.indegree.set(u, graph.indegree.get(u) - 1);
                }

                // include current node in the path and mark it as discovered
                path.add(v);
                discovered[v] = true;

                // recur
                findAllTopologicalOrders(graph, path, discovered, N);

                // backtrack: reset in-degree information for the current node
                for (int u: graph.adjList.get(v)) {
                    graph.indegree.set(u, graph.indegree.get(u) + 1);
                }

                // backtrack: remove current node from the path and
                // mark it as undiscovered
                path.remove(path.size() - 1);
                discovered[v] = false;
            }
        }

        // print the topological order if all vertices are included in the path
        if (path.size() == N) {
            System.out.println(path);
        }
    }

    // Print all topological orderings of a given DAG
    public static void printAllTopologicalOrders(Graph2 graph)
    {
        // get number of nodes in the graph
        int N = graph.adjList.size();

        // create an auxiliary array to keep track of whether vertex is discovered
        boolean[] discovered = new boolean[N];

        // list to store the topological order
        List<Integer> path = new ArrayList<>();

        // find all topological ordering and print them
        findAllTopologicalOrders(graph, path, discovered, N);
    }

    public static void main(String[] args)
    {
        // List of graph edges as per above diagram
//        List<Edge> edges = Arrays.asList(
//                new Edge(1, 0),
//                new Edge(1, 4),
//                new Edge(2, 3),
//                new Edge(2, 4),
//                new Edge(3, 0),
//                new Edge(4, 0),
//                new Edge(4, 1)
//        );

//        List<Edge> edges = Arrays.asList(
//                new Edge(1, 0),
//                new Edge(2, 3),
//                new Edge(2, 4),
//                new Edge(3, 0),
//                new Edge(4, 0),
//                new Edge(4, 1)
//        );

        List<Edge> edges = Arrays.asList(
                new Edge(1, 0),
                new Edge(2, 0),
                new Edge(3, 0)
        );


        // Number of nodes in the graph
        int N = 4;

        // create a graph from edges
        Graph2 graph = new Graph2(edges, N);

        // print all topological ordering of the graph
        printAllTopologicalOrders(graph);
    }
}