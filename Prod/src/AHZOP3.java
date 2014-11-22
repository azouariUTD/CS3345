import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

class Maze {
	int i;
	int j;
	int weight;
	String direction;

	Maze(int edgei, int edgej, int w, String d) {
		i = edgei;
		j = edgej;
		weight = w;
		direction = d;
	}

	Integer getI() {
		return i;
	}

}

class UnionFind {
	int N;
	int[] id;
	int[] sz;
	static int pl = 0;// Total path length
	static int cf = 0;// Total calls to finds

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
				if (i == id[id[j]]) {

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
			if (i != 0 && (i + 1) % 20 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);

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
		cf++;
		while (i != id[i]) {
			if (id[i] == id[id[i]]) {
				i = id[i];
				pl++;
			} else {
				id[i] = id[id[i]]; // Path Compression
				i = id[i];
				pl++;
				pl++;
			}
		}

		return i;
	}

	int find(int y) {

		return root(y);
	}

	int sets() {
		ArrayList<Integer> T = new ArrayList<>();
		for (int i = 0; i < id.length; i++) {
			if (!T.contains(id[id[i]]))
				T.add(id[id[i]]);
		}
		return T.size();
	}

	void printStats() {
		System.out.println("Number of disjoint sets remaining = " + sets());
		float meanPath = (float) pl / cf;
		System.out.printf("Mean path length of all find operations = %.2f",
				meanPath);
		System.out.println();

	}
}

class CustomComparator implements Comparator<Maze> {
	@Override
	public int compare(Maze o1, Maze o2) {
		return o1.getI().compareTo(o2.getI());
	}
}

public class AHZOP3 {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Random rand = new Random();
		ArrayList<Maze> edgeList = new ArrayList<Maze>();
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

					System.out.println(uf.id[x] + " " + uf.sz[uf.id[x]]);
					// System.out.println("total path length:" + UnionFind.pl);
				} else
					System.out.println(x + " and " + y
							+ " are already in the same set");
				break;
			case "f":
				x = reader.nextInt();
				System.out.println("Root of " + x + " is " + uf.find(x));
				// System.out.println("total path length:" + UnionFind.pl);
				break;
			case "p":
				uf.printArray();
				break;
			case "c":
				uf.printConnectList();
				break;
			case "e":

				System.exit(0);
			case "S":
				uf.printStats();
				break;

			case "m":
				w = reader.nextInt();
				d = reader.nextInt();
				h = reader.nextInt();
				nextLevel = w * d;

				SIZE = w * d * h;
				UnionFind mz = new UnionFind(SIZE);

				while (mz.sets() != 1) {

					candidate = rand.nextInt(SIZE); // generate candidate
													// between 0<=i<SIZE
					direction = rand.nextInt(6);
					weight = rand.nextInt(20) + 1;

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
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "North");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
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
						// Connect
						else {
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "East");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
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
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "South");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
						break;

					case 3:

						// West decrease by 1
						candidatej = candidate - 1;
						// Validate j
						// Check if j is in the maze:
						if (candidatej < 0)
							break;
						// Check if j is on the same floor as i
						else if (!(candidate / (w * d) == candidatej / (w * d)))
							break;
						// Same line
						else if (!(candidatej / w == candidate / w))
							break;
						// Connect
						else {
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "West");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
						break;

					case 4:

						// Going up example 23 to 14
						candidatej = candidate - nextLevel;
						// validate j
						// Check if j is in the maze
						if (candidatej < 0)
							break;
						// Connect
						else {
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "Up");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
						break;

					case 5:

						// Going Down example 0 to 9
						candidatej = candidate + nextLevel;
						// validate j
						// Check if j is in the maze
						if (candidatej > (SIZE - 1))
							break;
						// Connect
						else {
							if (!(mz.find(candidate) == mz.find(candidatej))) {
								Maze obj = new Maze(candidate, candidatej,
										weight, "Down");
								mz.union(candidate, candidatej);
								edgeList.add(obj);
							}

						}
						break;

					default:
						break;

					}

				}

				Collections.sort(edgeList, new CustomComparator());
				int current = 0;
				int previous = 0;
				int cnter = 0;
				
				String S1 = "";
				String S2 = "";

				for (Maze obj : edgeList) {

					current = obj.i;

					if (current != previous && cnter > 0) {
						System.out.println(previous + " " + S1 + S2);
						S1 = "";
						S2 = "";
					}

					S1 += obj.j + " ";
					S2 += obj.weight + " ";

					previous = current;
					
					cnter++;

				}

				// Print Last Row
				System.out.println(previous + " " + S1 + S2);

				while (reader.hasNextLine()) {
					req = reader.next();

					switch (req) {
					case "p":
						mz.printArray();
						break;
					case "c":
						mz.printConnectList();
						break;
					case "e":

						System.exit(0);
					case "S":
						mz.printStats();

						break;
					case "f":
						x = reader.nextInt();
						System.out.println("The root of " + x + " is "
								+ mz.find(x));
					}
					reader.nextLine();
				}
				break;

			default:
				break;

			}

			reader.nextLine();// Go to next line of the input
		}
		reader.close();

	}
}