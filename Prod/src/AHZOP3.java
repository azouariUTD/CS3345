import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

class Maze {
	int width;
	int height;
	int depth;
	int[] mid;
	int[] msz;

	Maze(int w, int d, int h) {
		mid = new int[w * h * d - 1];
		msz = new int[w * h * d - 1];
		this.height = h;
		this.depth = d;
		this.width = w;
		for (int i = 0; i < mid.length; i++) {
			mid[i] = i;
			msz[i] = 1;
		}
	}
}

class UnionFind {
	int N;
	int[] id;
	int[] sz;

	// Constructor
	UnionFind(int N) {
		id = new int[N];
		sz = new int[N];
		this.N = N;

		for (int i = 0; i < id.length; i++) {
			id[i] = i;
			sz[i] = 1;
		}

	}

	public void printConnectList() {
		boolean pflag = false;

		for (int i = 0; i < id.length; i++) {
			for (int j = 0; j < id.length; j++) {
				if (i == id[j]) {
					System.out.print(j + " ");
					pflag = true;
				}

			}
			if (pflag) {
				System.out.println();
				pflag = false;
			}
		}
	}

	public void printArray() {
		int[] prArray = new int[N];
		for (int i = 0; i < sz.length; i++) {
			if (id[i] == i) {
				prArray[i] = sz[i] * -1;
			} else
				prArray[i] = id[i];
		}

		for (int i = 0; i < sz.length; i++) {
			System.out.print(prArray[i] + " ");
		}
		System.out.println();
	}

	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		// id [i] = j;
		if (i == j)
			return;
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];

		}

	}

	public boolean connected(int x, int y) {
		return id[x] == id[y];
	}

	private int root(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]]; // Path Compression
			i = id[i];
		}
		return i;
	}

	int find(int y) {
		return root(y);
	}

	int sets() {
		int nSets = 0;
		return nSets;
	}

	void printStats() {

	}
}

public class AHZOP3 {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Random rand = new Random();
		ArrayList edges = new ArrayList();
		String req;
		int SIZE;
		int x = 0, y = 0;
		int w, d, h;
		int candidate;
		int direction;
		int nextLevel;
		int candidatej;
		int weight;

		// Process the header first
		req = reader.next();
		System.out.println("Ahmed Zouari");
		reader.nextLine();
		SIZE = reader.nextInt();
		UnionFind uf = new UnionFind(SIZE);

		while (reader.hasNextLine()) {
			req = reader.next();

			switch (req) {
			case "u":
				x = reader.nextInt();
				y = reader.nextInt();
				if (!uf.connected(x, y)) {
					uf.union(x, y);
				} else
					System.out.println(x + " and " + y
							+ " are already in the same set");
				break;
			case "f":
				x = reader.nextInt();
				System.out.println("The root of " + x + " is " + uf.find(x));
				break;
			case "p":
				uf.printArray();
				break;
			case "c":
				uf.printConnectList();
				break;
			case "e":
				System.exit(0);
				break;
			case "m":
				w = reader.nextInt();
				d = reader.nextInt();
				h = reader.nextInt();
				nextLevel = w * d;

				SIZE = w * d * h;
				UnionFind mz = new UnionFind(SIZE);

				while (edges.size() < SIZE) {
					candidate = rand.nextInt(SIZE); // generate candidate
													// between 0<=i<SIZE
					direction = rand.nextInt(6);
					switch (direction) {

					case 0:
						// North decrease by width
						// Stay on the same floor
						candidatej = candidate - w;
						// Validate j
						// Check if j is in the maze:
						if (candidatej < 0)
							break;
						// Check if j is on the same floor as i
						else if (!(candidate / (w * d) == candidatej / (w * d)))
							break;
						// Connect
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);
						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						
						break;
					case 1:
						// East increase by 1
						candidatej = candidate + 1;
						// Validate j
						// Check if j is on the same floor as i
						if (!(candidate / (w * d) == candidatej / (w * d)))
							break;
						// Same line
						else if (!(candidatej / w == candidate / w))
							break;
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);
						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						break;
					case 2:

						// South increase by width
						// Stay on the same floor
						candidatej = candidate + w;
						// Validate j
						// Check if j is in the maze:
						if (candidatej < 0)
							break;
						// Check if j is on the same floor as i
						else if (!(candidate / (w * d) == candidatej / (w * d)))
							break;
						// Connect
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);
						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						break;

					case 3:
						// West decrease by 1
						candidatej = candidate - 1;
						// Validate j
						// Check if j is on the same floor as i
						if (!(candidate / (w * d) == candidatej / (w * d)))
							break;
						// Same line
						else if (!(candidatej / w == candidate / w))
							break;
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);
						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						break;
					case 4:
						// Up increase by the height
						candidatej = candidate + h;
						// validate j
						// Check if j is in the maze
						if (candidatej > (SIZE - 1))
							break;
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);

						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						break;
					case 5:
						// Down decrease by the height
						candidatej = candidate - h;
						// validate j
						// Check if j is in the maze
						if (candidatej < 0)
							break;
						else {
							x = mz.find(candidate);
							y = mz.find(candidatej);
							mz.union(x, y);
							weight = rand.nextInt(21);

						}
						if (!edges.contains(x)) edges.add(x);
						if (!edges.contains(y)) edges.add(y);
						break;
						default:
							break;

					}

				}

			default:
				break;

			}

			reader.nextLine();// Go to next line of the input
		}
		reader.close();

		/*
		 * int n = 12; int s = 26;
		 * 
		 * Random edges = new Random(); Random weights = new Random();
		 * 
		 * 
		 * UnionFind maze = new UnionFind(s); UnionFind uf = new UnionFind(n);
		 * uf.union(1, 2);
		 * 
		 * uf.union(3, 4);
		 * 
		 * uf.union(5,6); uf.union(7,8); uf.union(9,10); uf.union(2,4);
		 * uf.union(6,8); uf.union(11,6); System.out.println("The root of 1 is:"
		 * + uf.find(1)) ; System.out.println("The root of 2 is:" + uf.find(2))
		 * ;
		 * 
		 * System.out.println("The root of 4 is:" + uf.find(4)) ;
		 * System.out.println("The root of 8 is:" + uf.find(8)) ;
		 * System.out.println("The root of 10 is:" + uf.find(10)) ;
		 * System.out.println("The root of 8 is:" + uf.find(8)) ;
		 * System.out.println("The root of 3 is:" + uf.find(3)) ;
		 * 
		 * 
		 * for ( int i = 0 ; i < uf.id.length ; i++) { System.out.print(i +
		 * "|"); } System.out.println();
		 * 
		 * for ( int i = 0 ; i < uf.id.length ; i++) { System.out.print(uf.id[i]
		 * + "|"); } System.out.println();
		 * 
		 * for ( int i = 0 ; i < uf.sz.length ; i++) { System.out.print(uf.sz[i]
		 * + "|"); } System.out.println();
		 * 
		 * uf.printArray();
		 * 
		 * boolean pflag = false;
		 * 
		 * for (int i = 0 ; i < uf.id.length ; i++ ) { for (int j = 0; j <
		 * uf.id.length ; j++) { if (i == uf.id[j]) { System.out.print(j + " ");
		 * pflag = true; }
		 * 
		 * } if (pflag) { System.out.println(); pflag = false; } }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //—1 -4 1 1 1 -5 5 5 5 -2 9 5
		 */

	}
}
