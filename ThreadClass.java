import java.io.File;
import java.util.Scanner;
import java.util.*;

public class ThreadClass extends PriorityQueue {
	public static void main(String[] args) {

		LinkedList<PriorityQueue> ClassObjects = new LinkedList<>();
		for (int i = 0; i < Capacity; i++) {
			PriorityQueue obj = new PriorityQueue();
			ClassObjects.add(obj);
		}
		int i = 0;

		String FileName = "Input1.txt";

		File InputData = new File(FileName);

		try {
			Scanner a = new Scanner(InputData);

			while (a.hasNextLine()) {
				while (a.hasNextInt()) {
					ClassObjects.get(i).PriorityNum = a.nextInt();
					ClassObjects.get(i).start();
					i++;
				}
				String TempString;
				TempString = a.next();
				if (TempString.equals("d")) {
					ClassObjects.get(i).DequeueFlag = true;
					ClassObjects.get(i).start();
					i++;
				}
				if (TempString.equals("x")) {
					break;

				}
			}
		}

		catch (Exception ex) {
			System.out.println("Incorrect data in file");
		}
	}
}
