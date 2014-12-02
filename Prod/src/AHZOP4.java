/**
 * 
 * @author ahmedzouari
 *
 */
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.PriorityQueue;

/**
 * Class that holds the edges
 * @author ahmedzouari
 *
 */
class DirectedEdge {
	private final int v, w;
	private final int weight;
	
	/**
	 * Class Constructor
	 * @param v
	 * @param w
	 * @param weight
	 */
	public DirectedEdge( int v , int w , int weight ) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public int from () {
		return v;
	}
	
	public int to () {
		return w;
	}
	
	public int weight() {
		return weight;
	}
	
}

class DirectGraph {
	final int V;//Number of vertices
	int E;//Number of Edges
	ArrayList<DirectedEdge> []  adj ; //Array of ArryaList
	
	DirectGraph (int V) {
		this.V = V;
		this.E = 0;
		adj = new ArrayList[V];
		for (int v = 0 ; v < V; v++) {
			adj[v] = new ArrayList<DirectedEdge>();
		}
	}
	
	void addEdge ( int v , int w , int weight) {
		DirectedEdge e = new DirectedEdge(v,w,weight);
		adj[v].add(e);
	}
}

public class AHZOP4 {

	public static void main(String[] args) {
		Queue<Integer>  q = new java.util.LinkedList<Integer>();
		/*
		DirectGraph G = new DirectGraph(10);
		G.addEdge(0, 1, 10);
		G.addEdge(0, 2, 10);
		G.addEdge(0, 3, 10);
		G.addEdge(2, 3, 10);
		for (int i = 0 ; i < 10 ; i++) {
		for ( DirectedEdge e : G.adj[i]) {
			System.out.println(e.from() + " ==> " + e.to() + " weight:" + e.weight());
		}
		}
		*/
		
	
		
				
		
		String title;
		int nVertices; // Number of vertices
		int sVertex; // Source vertex
		int v;
		int w;
		int weight;
		
		
		
		
		
		Scanner reader = new Scanner(System.in);

		title = reader.nextLine();
		nVertices = reader.nextInt();
		sVertex = reader.nextInt();
		
		DirectGraph G = new DirectGraph(nVertices);
		
		System.out.println(title);
		System.out.print(nVertices + " ");
		System.out.println(sVertex);

		reader.nextLine();
		while (reader.hasNextLine()) {
			v = reader.nextInt();
			w = reader.nextInt();
			weight = reader.nextInt();
			
			G.addEdge(v, w, weight);
			
			
			for (int i = 0 ; i < nVertices ; i++) {
				for ( DirectedEdge e : G.adj[i]) {
					System.out.println(e.from() + "->" + e.to() + " " + e.weight());
				}
				}

			reader.nextLine();

		}

		reader.close();
		


	}

}
