/**
 *
 * @author Ahmed Zouari
 */

import java.util.Random;
import java.util.Scanner;

class Request {
	int size;
	int lifeTime;

	Request(int minSize, int maxSize, int maxLifeTime) {
		// Create a request with uniform random size and life time
		Random rand = new Random();
		int uBoundSegment = maxSize - minSize + 1;
		size = rand.nextInt(uBoundSegment) + minSize;
		lifeTime = rand.nextInt(maxLifeTime) + 1;

	}
}

class Memory {

	int[] nSegm; // Array to hold the number of segments
	int index = 0;

	int[] nHole; // Array to hold the number of holes
	int indexH = 0;

	int[] pArray;// Array to hold the number of probes
	int indexP = 0;

	int[] mArray;// Array to hold the memory usage
	int indexM = 0;

	int[] mArrayff;// Array to hold the memory usage at when a placement first
					// fails.
	int indexff = 0;

	Node head;
	Node tail;
	int memSize;
	Node nCursor = null;// This will be used for Next Fit

	boolean firstFailFlag = true;// This flag will be used to determine when a
									// placemnt first fails

	class Node {
		boolean segment;
		int size;
		int timeToDepart;
		Node previous;
		Node next;

		//Doubly Linked list segment constructor (Based on Requests)
		Node(Request req, int timeOfDay) {
			size = req.size;
			timeToDepart = req.lifeTime + timeOfDay;
			segment = true;

		}
        //Doubly Linked list head and tail contructor
		Node(boolean sg, int sz, int ttd, Node pv, Node nt) {
			segment = sg;
			size = sz;
			timeToDepart = ttd;
			previous = pv;
			next = nt;
		}

	}

	int getNumSegment() {
		// This function will return the number of segments.
		Node cursor = head;
		int size = 0;
		while (cursor != null) {
			if (cursor.segment)
				size++;
			cursor = cursor.next;

		}
		return size;
	}

	int getNumHoles() {
		// This function will return the number of holes.
		Node cursor = head.next;
		int size = 0;
		while (cursor != null) {
			if (!cursor.segment)
				size++;
			cursor = cursor.next;

		}
		return size;
	}

	int getMemoryusage() {
		// This function will return the memory usage
		Node cursor = head.next;
		int sumSize = 0;
		while (cursor != null) {
			if (cursor.segment)
				sumSize += cursor.size;
			cursor = cursor.next;

		}
		return sumSize;
	}

	void printStats(String policy) {

		// This function will print the final outputs.
		// The input will be the policy id
		int sum = 0;// Sum of segments
		for (int x : nSegm) {
			sum += x;

		}

		int sumH = 0;// Sum of holes
		for (int x : nHole) {
			sumH += x;

		}

		int sumP = 0;// Sum of probes
		for (int x : pArray) {
			sumP += x;
		}

		float sumM = 0;// Sum of percent of memory in use
		for (int x : mArray) {
			sumM += (float) x / memSize;

		}
		float sumMff = 0;// Sum of memory in use when a placement first fails.
		for (int x : mArrayff) {
			sumMff += (float) x / memSize;
		}

		sumM = sumM * 100;
		sumMff = sumMff * 100;
		float meanS = (float) sum / nSegm.length;// Mean number of segments.
		float meanH = (float) sumH / nHole.length;// Mean number of holes.
		float meanP = (float) sumP / pArray.length;// Mean number of probes.
		float meanM = sumM / mArray.length;// Mean percent of memory in use.
		float meanMff = sumMff / (indexff + 1);// Mean percent of memory in use
												// when a placement first fails

		System.out.print(policy + " ");
		System.out.printf("%.1f %.1f %.1f %.1f %.1f", meanM, meanMff, meanS,
				meanH, meanP);
		System.out.println();

	}

	Memory(int size) {
		// This is the memory constructor
		head = new Node(false, 0, 0, null, null);
		tail = new Node(false, size, 0, head, null);
		head.next = tail;
		memSize = size;

	}

	boolean FFplace(Request req, int timeOfDay) {
		// This function will attempt to place a request. Return false if there
		// isn't room

		Node cursor = head;
		int probCounter = 0;

		while (cursor != null) {

			if (cursor.segment == false && cursor.size > 0) {
				// When cursor(hole) is greater than request size we need to
				// create a segment and adjust
				// the hole size
				if (cursor.size > req.size) {
					Node newest = new Node(req, timeOfDay);
					cursor.size = cursor.size - newest.size;

					cursor.previous.next = newest;
					newest.previous = cursor.previous;
					cursor.previous = newest;
					newest.next = cursor;

					nSegm[index] = getNumSegment();
					index++;
					nHole[indexH] = getNumHoles();
					indexH++;

					pArray[indexP] = probCounter;
					indexP++;

					mArray[indexM] = getMemoryusage();
					indexM++;

					firstFailFlag = true;// Set flag to true since first attempt
											// was successful
					return true;
				}
				if (cursor.size == req.size) {
					// when a hole equal to the segment size
					cursor.segment = true;
					cursor.timeToDepart = req.lifeTime + timeOfDay;
					nSegm[index] = getNumSegment();
					index++;

					nHole[indexH] = getNumHoles();
					indexH++;

					pArray[indexP] = probCounter;
					indexP++;

					mArray[indexM] = getMemoryusage();
					indexM++;

					firstFailFlag = true;// Set flag to true since first attempt
											// was successful
					return true;

				}

			}

			if (!cursor.segment) {
				probCounter++;
			}
			cursor = cursor.next;

		}

		// Set first fail flag to false if a placement fails in the first
		// attempt.
		if (firstFailFlag) {
			mArrayff[indexff] = getMemoryusage();
			indexff++;
			firstFailFlag = false;

		}
		return false;
	}

	boolean NFplace(Request req, int timeOfDay) {
		// This function will attempt to place a request. Return false if there
		// isn't room.

		int probCounter = 0;
		if (nCursor == null) {
			nCursor = head;

		}

		while (nCursor != null) {

			if (nCursor.segment == false && nCursor.size > 0) {
				if (nCursor.size > req.size) {
					// When cursor(hole) is greater than request size we need to
					// create a segment and adjust
					// the hole size
					Node newest = new Node(req, timeOfDay);
					nCursor.size = nCursor.size - newest.size;

					Node tmp = nCursor.previous;
					tmp.next = newest;

					// nCursor.previous.next = newest;
					newest.previous = tmp;
					nCursor.previous = newest;
					newest.next = nCursor;

					nCursor = newest;
					nSegm[index] = getNumSegment();
					index++;

					nHole[indexH] = getNumHoles();
					indexH++;

					pArray[indexP] = probCounter;
					indexP++;

					mArray[indexM] = getMemoryusage();
					indexM++;

					firstFailFlag = true;// Set flag to true since first attempt
											// was successful

					return true;
				}
				if (nCursor.size == req.size) {
					// when a hole equal to the segment size
					nCursor.segment = true;
					nCursor.timeToDepart = req.lifeTime + timeOfDay;
					nSegm[index] = getNumSegment();
					index++;

					nHole[indexH] = getNumHoles();
					indexH++;

					pArray[indexP] = probCounter;
					indexP++;

					mArray[indexM] = getMemoryusage();
					indexM++;

					firstFailFlag = true;// Set flag to true since first attempt
											// was successful

					return true;
				}

			}

			if (!nCursor.segment) {
				probCounter++;
			}

			nCursor = nCursor.next;
		}

		// Set first fail flag to false if a placement fails in the first
		// attempt.
		if (firstFailFlag) {
			mArrayff[indexff] = getMemoryusage();
			indexff++;
			firstFailFlag = false;

		}
		return false;
	}

	void removeSegmentsDueToDepart(int timeOfDay) {
		// Remove all segments whole lifetimes are exceeded.

		Node cursor = head.next;

		while (cursor != null) {

			if (cursor.segment) {
				if (cursor.timeToDepart <= timeOfDay) {
					cursor.segment = false;
					cursor.timeToDepart = 0;

					// Merge two holes current and previous
					if ((!cursor.previous.segment) && cursor.previous != head) {
						cursor.size += cursor.previous.size;

						cursor.previous = cursor.previous.previous;
						cursor.previous.next = cursor;

					}

					// Merge two holes current and next
					if (cursor != tail) {
						if ((!cursor.next.segment)) {
							cursor.next.size += cursor.size;
							cursor.next.previous = cursor.previous;
							cursor.previous.next = cursor.next;
							cursor = cursor.next;

						}
					}
				}
			}

			cursor = cursor.next;
		}

	}
}

public class AHZOP1 {

	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);
		while (reader.hasNextLine()) {
			int memorySize = 0;
			int minSegmentSize = 0;
			int maxSegmentSize = 0;
			int maxSegmentLifeTime = 0;
			int trialLength = 0;
			int timeOfDay = 0;
			int placements = 0;
			memorySize = reader.nextInt();
			minSegmentSize = reader.nextInt();
			maxSegmentSize = reader.nextInt();
			maxSegmentLifeTime = reader.nextInt();
			trialLength = reader.nextInt();

			// Handle First Fit
			Memory memory = new Memory(memorySize);// Provided in the input

			memory.nSegm = new int[trialLength];
			memory.nHole = new int[trialLength];
			memory.pArray = new int[trialLength];
			memory.mArray = new int[trialLength];
			memory.mArrayff = new int[trialLength];

			while (placements < trialLength) {
				timeOfDay++;
				memory.removeSegmentsDueToDepart(timeOfDay);

				Request req = new Request(minSegmentSize, maxSegmentSize,
						maxSegmentLifeTime);

				while (!memory.FFplace(req, timeOfDay)) {
					timeOfDay++;

					memory.removeSegmentsDueToDepart(timeOfDay);

				}

				placements++;

			}

			memory.printStats("FF");
			System.out.println(placements);

			// Handle Next Fit
			Memory NFmemory = new Memory(memorySize);

			NFmemory.nSegm = new int[trialLength];
			NFmemory.nHole = new int[trialLength];
			NFmemory.pArray = new int[trialLength];
			NFmemory.mArray = new int[trialLength];
			NFmemory.mArrayff = new int[trialLength];

			placements = 0;

			while (placements < trialLength) {
				timeOfDay++;
				NFmemory.removeSegmentsDueToDepart(timeOfDay);

				Request req = new Request(minSegmentSize, maxSegmentSize,
						maxSegmentLifeTime);

				while (!NFmemory.NFplace(req, timeOfDay)) {
					timeOfDay++;

					NFmemory.removeSegmentsDueToDepart(timeOfDay);

				}

				placements++;

			}

			NFmemory.printStats("NF");
			reader.nextLine();// Go to next line of the input
		}
		reader.close();

	}
}