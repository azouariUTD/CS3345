/**
 * 
 * @author ahmedzouari
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Graph Edge class
 * 
 * @author ahmedzouari
 *
 */
class GraphEdge {
	final int i; // Vertex 1
	final int j; // Vertex 2
	final int w; // Weight

	GraphEdge(int i, int j, int w) {
		this.i = i;
		this.j = j;
		this.w = w;
	}

	int to() {
		return j;
	}

	int from() {
		return i;
	}

	public String toString() {
		return i + " ";
	}

}

class DijkstraSP {
	Comparator<Integer> shortD = new Comparator<Integer>() {
		public int compare(Integer From, Integer To) {
			if (distanceTo[From] > distanceTo[To])
				return 1;
			if (distanceTo[From] < distanceTo[To])
				return -1;
			if (From > To)
				return 1;
			if (From < To)
				return -1;
			return 0;

		}
	};

	int[] distanceTo;
	GraphEdge[] edgeTo;
	int V;

	PriorityQueue<Integer> pq = new PriorityQueue<Integer>(1, shortD);

	DijkstraSP(DWG G, int s) {

		distanceTo = new int[G.V];
		edgeTo = new GraphEdge[G.V];
		this.V = G.V;
		for (int i = 0; i < G.V; i++) {
			distanceTo[i] = Integer.MAX_VALUE;
		}
		distanceTo[s] = 0;
		pq.add(s);
		while (!pq.isEmpty()) {
			relax(G, pq.remove());

		}

	}

	void relax(DWG G, int v) {
		for (GraphEdge e : G.ALS[v]) {
			int w = e.to();
			if (distanceTo[w] > distanceTo[v] + e.w) {
				distanceTo[w] = distanceTo[v] + e.w;
				edgeTo[w] = e;
				if (pq.contains(w)) {
					pq.remove(w);
					pq.add(w);
				} else
					pq.add(w);

			}
		}

	}

	public int distTo(int v) {
		return distanceTo[v];
	}

	boolean hasPathTo(int v) {
		return distanceTo[v] < Integer.MAX_VALUE;
	}

	Iterable<GraphEdge> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		Stack<GraphEdge> path = new Stack<GraphEdge>();
		for (GraphEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
			path.add(e);
		return path;

	}

}

/**
 * Directed Weighted Graph class
 * 
 * @author ahmedzouari
 *
 */
class DWG {
	int V;// Number of vertices

	ArrayList<GraphEdge>[] ALS;

	@SuppressWarnings("unchecked")
	DWG(int V) {
		this.V = V;
		ALS = new ArrayList[V];
		for (int v = 0; v < V; v++) {
			ALS[v] = new ArrayList<GraphEdge>();
		}

	}

	void addEdge(int i, int j, int w) {
		GraphEdge e = new GraphEdge(i, j, w);
		ALS[i].add(e);
	}

	public void printing() {
		for (int i = 0; i < V; i++) {

			for (GraphEdge e : ALS[i]) {
				System.out.println(e.toString() + " ");
			}

		}

	}

}

class FloydWarshall {
	int[][] FW;
	int[][] dist;
	int[][] path;
	int V;

	FloydWarshall(int V) {
		this.V = V;
		FW = new int[V][V];
		dist = new int[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				dist[i][j] = Integer.MAX_VALUE;
			}
		}

		for (int i = 0; i < V; i++) {
			FW[i][i] = 0;
			dist[i][i] = 0;

		}

	}

	void addEdge(int i, int j, int w) {
		FW[i][j] = w;
		dist[i][j] = w;
	}

	void FloydWarshallComputation() {
		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if(dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE ) {
						continue;
					}
					if ((dist[i][k] + dist[k][j]) < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						// path[i][j] = k;
					}

				}
			}
		}
	}
}

public class AHZOP4 {

	public static void main(String[] args) {
		
		String title;// Graph title
		int V;// Number of vertices
		int sVertex;// Start vertex
		int i, j, w;// Edge i , j and weight w

		Scanner reader = new Scanner(System.in);
		title = reader.nextLine();
		V = reader.nextInt() + 1;
		sVertex = reader.nextInt();
		System.out.println("Ahmed Zouari");
		System.out.println(title);

		DWG G = new DWG(V);
		FloydWarshall F = new FloydWarshall(V);
		

		while (reader.hasNextLine()) {
			i = reader.nextInt();
			j = reader.nextInt();
			w = reader.nextInt();

			G.addEdge(i, j, w);
			F.addEdge(i, j, w);

			reader.nextLine();

		}

		reader.close();

		DijkstraSP DG = new DijkstraSP(G, sVertex);
		F.FloydWarshallComputation();
		

		for (int cnt = sVertex; cnt < G.V; cnt++) {

			ArrayList<GraphEdge> ar = new ArrayList<GraphEdge>();
			if (DG.hasPathTo(cnt)) {
				for (GraphEdge e : DG.pathTo(cnt)) {

					ar.add(e);

				}
				Collections.reverse(ar);
				for (GraphEdge e : ar) {

					System.out.print(e);
				}
				if (cnt == sVertex)
					System.out.print(cnt + " " + cnt + " ");
				else
					System.out.print(cnt + " ");

				System.out.println(DG.distTo(cnt));

			}

		}
		System.out.println();
		int max = 0;
		int v1 = 0; 
		int v2 = 0;
		for (int index = 1; index < F.V; index++) {
			for (int indexA = 1; indexA < F.V; indexA++) {
				if (F.dist[index][indexA] > max) {
					max = F.dist[index][indexA];
					v1 = index;
					v2 = indexA;
			}
			}

		}
		if (max == Integer.MAX_VALUE) {
			System.out.println("The diameter is infinity");
		} else 
		System.out.println("The diameter is " + max + " between vertices " +  v1 + " and " +  v2);
	}

}
