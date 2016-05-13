
import java.util.Scanner;
import java.util.*;
import java.util.Iterator;
import java.util.Map.Entry;
import java.io.File;

class PriorityQueue implements Runnable {

	static int QueueLength = 0;

	public static int Capacity = 500;
	static int[] Queue = new int[Capacity];
	static int BurstRate = 2;
	static int dequeuePriority = 0;// used for recording the dequeued priority;
	static int dequeuePriorityCounter = 0;// used for counting the number of the
											// same dequeued priority;
	
	static HashMap<Integer, Integer[]> EnqueueDequeueRecord = new HashMap<>();
	int PriorityNum = 0;
	boolean DequeueFlag = false;

	private Thread t;

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();

		}
	}

	public void run() {
		// TODO Auto-generated method stub
		int Priority;

		try {

			// Enqueue

			if (PriorityNum > 0) {
				Priority = PriorityNum;
				System.out.println(" Inserting /Enqueue Item  : " + Priority);
				thread_safe(Priority, QueueLength, 1);
				QueueLength += 1;
				EnqueueDequeueRecordMaintain(Priority, 1);
				PriorityNum = 0;
				Thread.sleep(4000);
			}

			if (DequeueFlag == true) {
				// System.out.print(" \n\n --Dequeuing-- ");

				int root = thread_safe(0, 0, 0); // making the items thread
													// safe
				System.out.println(" Dequeue Element is------- " + root);

				if (root == dequeuePriority) {// if the dequeued priority
												// equals the previous
												// dequeued priority
					dequeuePriorityCounter++;
				} else {// if the dequeued priority does not equal to the
						// previous dequeued priority
					dequeuePriority = root;
					dequeuePriorityCounter = 1;
				}
				EnqueueDequeueRecordMaintain(root, 0);
				DequeueFlag = false;
				Thread.sleep(4000);
			}

		} catch (Exception ex) {

			System.out.println("Incorrect Sleep time for thread OR" + " Queue is NOW Empty, please start Again");
		}

	}

	static int DequeueElement() {

		int root = Queue[0];
		Integer DequeueCount = EnqueueDequeueRecord.get(root)[1];

		

			if (root == dequeuePriority && dequeuePriorityCounter == BurstRate) {// if
																					// the
																					// same
																					// value
																					// has
																					// been
																					// dequeued
																					// twice

				int secondPriority = root;
				int secondPriorityPosition = 1;// position of the second
												// priority;
				while (secondPriority == root && Queue[secondPriorityPosition] != 0) {
					secondPriority = Queue[secondPriorityPosition];
					secondPriorityPosition++;
				}
				Queue[secondPriorityPosition] = Queue[QueueLength - 1];
				Queue[QueueLength - 1] = 0;
				QueueLength -= 1;
				Recursive_Heapify(0);
				return secondPriority;
			} else {
				Queue[0] = Queue[QueueLength - 1];
				Queue[QueueLength - 1] = 0;

				QueueLength -= 1;
				Recursive_Heapify(0);
				return root;
			}
		
	}

	static void Insert(int Priority, int i) {
		// Insertion is done into a Min-Heap
		Queue[i] = Priority;

		Recursive_Heapify(0);// Rearranging from the root
	}

	// min - priority queue.
	public static int Recursive_Heapify(int x) {// x stands for the position in
												// queue;
		int LeftNode = 2 * x + 1;// position for left child;
		int RightNode = 2 * x + 2;// position for right child;

		if (Queue[LeftNode] == 0 && Queue[RightNode] == 0) {
			return Queue[x];
		} else if (Queue[RightNode] == 0) {
			if (Queue[x] > Queue[LeftNode]) {
				swap(x, LeftNode);
			}
			return Queue[x];
		} else {
			int minLeft = Recursive_Heapify(LeftNode);
			int minRight = Recursive_Heapify(RightNode);
			int minValue = Math.min(Queue[x], Math.min(minLeft, minRight));
			if (minValue == Queue[LeftNode]) {
				swap(x, LeftNode);
			} else if (minValue == Queue[RightNode]) {
				swap(x, RightNode);
			}
			return minValue;
		}
	}

	static synchronized int thread_safe(int Priority, int i, int DequeuEnqueueFlag) {
		// this is a thread safe wrapper, Dequeue Or Enqueue has two values: 0
		// for Dequeue, 1 for Enqueue;
		if (DequeuEnqueueFlag == 0) {
			return DequeueElement();
		} else {
			Insert(Priority, i);
			return 0;
		}
	}

	static void swap(int position1, int position2) {// swap two items based on
													// their positions in the
													// queue
		int temp = Queue[position1];
		Queue[position1] = Queue[position2];
		Queue[position2] = temp;
	}

	static void EnqueueDequeueRecordMaintain(int Priority, int flag) { // 1 for
																		// Enqueue
																		// and o
																		// for
																		// dequeue
		if (flag == 1) {

			if (EnqueueDequeueRecord.containsKey(Priority)) {
				Integer[] arr = new Integer[2];
				arr = EnqueueDequeueRecord.get(Priority);
				arr[0] = arr[0] + 1;
				EnqueueDequeueRecord.put(Priority, arr);
			} else {
				Integer[] arr = new Integer[2];
				arr[0] = 1;
				arr[1] = 0;
				EnqueueDequeueRecord.put(Priority, arr);
			}
		}

		else {

			if (EnqueueDequeueRecord.containsKey(Priority)) {
				Integer[] arr = new Integer[2];
				arr = EnqueueDequeueRecord.get(Priority);
				arr[1] = arr[1] + 1;
				EnqueueDequeueRecord.put(Priority, arr);
			} else {
				Integer[] arr = new Integer[2];
				arr[1] = 1;
				EnqueueDequeueRecord.put(Priority, arr);
			}
		}

	}
}