import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

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
		String req;
		int SIZE;
		int x = 0, y = 0;

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
			default:
				break;

			}

			reader.nextLine();// Go to next line of the input
		}
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
