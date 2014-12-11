import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author ahmedzouari
 *
 */
/**
 * Graph Edge class
 * @author ahmedzouari
 *
 */
class GraphEdge {
	final int i; //Vertex 1
	final int j; //Vertex 2
	final int w; //Weight
	
	GraphEdge (int i, int j , int w) {
		this.i = i;
		this.j = j;
		this.w = w;
	}
	
	public String toString() {
		return i + " " + j + " " + w;
	}
	
}


/**
 * Directed Weighted Graph class
 * @author ahmedzouari
 *
 */
class DWG {
	int V;//Number of vertices
	
	ArrayList<GraphEdge> ALS[];
	
	DWG (int V) {
		this.V = V;
		ALS = new ArrayList[V];
		for (int v = 0 ; v < V; v++) {
			ALS[v] = new ArrayList<GraphEdge>();
		}
		
	}
	
	void addEdge(int i , int j , int w) {
		GraphEdge e = new GraphEdge(i,j,w);
		ALS[i].add(e);
	}
	
	public void printing() {
		for (int i = 0 ; i < V ; i++) {
			
			for (GraphEdge e : ALS[i]) {
				System.out.println(e.toString() + " ");
			}
			
			
		}
		
	}
	
	
}
public class AHZOP4 {

	public static void main(String[] args) {
		String title;//Graph title
		int V;//Number of vertices
		int sVertex;//Start vertex
		int i,j,w;//Edge i , j and weight w
		
		
		Scanner reader = new Scanner(System.in);
		title = reader.nextLine();
		V = reader.nextInt();
		sVertex = reader.nextInt();
		
		DWG G = new DWG(V);
		
		G.addEdge(1, 2, 5);
		G.addEdge(1, 3, 5);
		G.addEdge(1, 4, 5);
		G.addEdge(2, 4, 5);
		
		G.printing();
		
		
		
		System.out.println(title + " " + V + " " + sVertex );
		

	}

}
