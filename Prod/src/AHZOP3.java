import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

class Union {
	Node head;

	Union() {
		head = null;
	}

	void addAtHead(String k, int e, int c, int w) {
		head = new Node(k, e, c, w, head);
	}

	void printUnion() {
		Node cursor = head;
		while (cursor.nxt != null) {
			System.out.println(cursor.edge + " " + cursor.connection + " "
					+ cursor.weight);
			cursor = cursor.nxt;
		}
	}

	void printUniono() {
		Node cursor = head;
		String S1 = "";
		String S2 = "";
		String S = "";
		try {
			while (cursor.nxt != null) {
				Node cursor2 = cursor;
				S1 = "";
				S2 = "";
				while (cursor2 != null & cursor2.edge == cursor.edge) {
					S1 += cursor2.connection + " ";
					S2 += cursor2.weight + " ";
					cursor2 = cursor2.nxt;
				}

				System.out.println(cursor.edge + " " + S1 + S2);
				cursor = cursor2;
			}
		} catch (NullPointerException e) {
		}
	}

	void insertInOrder(String k, int e, int c, int w) {
		if (head == null) {
			head = new Node(k, e, c, w);
		} else {
			if (head.getValue() > e) {
				head = new Node(k, e, c, w, head);
			} else {
				Node ref = head;
				Node prev = null;
				while (ref != null && ref.getValue() <= e) {
					prev = ref;
					ref = ref.getNext();
				}
				prev.putNext(new Node(k, e, c, w, ref));
			}
		}

	}

	class Node {
		String key;
		int edge;
		int connection;
		int weight;
		Node nxt;

		Node(String k, int e, int c, int w) {
			key = k;
			edge = e;
			connection = c;
			weight = w;
			nxt = null;
		}

		Node(String k, int e, int c, int w, Node next) {
			key = k;
			edge = e;
			connection = c;
			weight = w;
			nxt = next;
		}

		int getValue() {
			return edge;
		}

		Node getNext() {
			return nxt;
		}

		void putNext(Node next) {
			nxt = next;
		}

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

public class AHZOP3 {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Random rand = new Random();
		ArrayList<Integer> edges = new ArrayList<Integer>();
		ArrayList<String> edgesn = new ArrayList<String>();
		String req;
		int SIZE;
		int x = 0, y = 0;
		int p, q;
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

		Union u = new Union();

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
				System.out.println("The root of " + x + " is " + uf.find(x));
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
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);

								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);

								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
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
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);
								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);
								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
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
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);
								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);
								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
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
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);
								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);
								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
						}
						break;
					case 4:

						// Up increase by the height
						candidatej = candidate + h;
						// validate j
						// Check if j is in the maze
						if (candidatej > (SIZE - 1))
							break;
						// Connect
						else {
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);
								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);
								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
						}
						break;
					case 5:

						// Down decrease by the height
						candidatej = candidate - h;
						// validate j
						// Check if j is in the maze
						if (candidatej < 0)
							break;
						// Connect
						else {
							String key = candidate + " " + candidatej;
							String keyr = candidatej + " " + candidate;
							if (!edgesn.contains(key)) {

								mz.union(candidate, candidatej);
								u.insertInOrder(candidate + " " + candidatej,
										candidate, candidatej, weight);
								edgesn.add(key);
								edgesn.add(keyr);

							}

						}

						if (!edges.contains(candidate)) {
							edges.add(candidate);
						}

						if (!edges.contains(candidatej)) {
							edges.add(candidatej);
						}
						break;
					default:
						break;

					}

				}
				u.printUniono();
				/*
				 * edgesn.remove("One"); Collections.sort(edgesn);
				 */
				// for (String pr: edgesn) { System.out.println(pr); }

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
