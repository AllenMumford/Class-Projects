import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/*
 * Allen Mumford
 * CS 3345.003
 * Fall 2018
 * Project 5. Implement Quick Sort and test speed. ARRAY_SIZE is modified to test various sizes, and the method call is edited to select pivot selection method
 * It overwrites old Sorted and Unsorted text files. 
 */

public class QuickSort {

	Random rand;
	int[] arr;
	
	//----------------THIS LINE IS EDITED TO CHANGE SIZE OF THE ARRAY TO BE SORTED--------
	int ARRAY_SIZE = 50000;
	
	PrintWriter writerUnsorted;
	PrintWriter writerSorted;
	
	public static void main(String[] args) {
		QuickSort test = new QuickSort();
	}

	QuickSort() {
		rand = new Random();
		try {
		writerUnsorted = new PrintWriter("Unsorted.txt", "UTF-8");
		writerSorted = new PrintWriter("Sorted.txt", "UTF-8");
		
		createRandomArray(ARRAY_SIZE);
		writerUnsorted.println(Arrays.toString(arr));
		//printArray();
		long stopwatch = System.nanoTime();
		
		//--------------THIS LINE IS EDITED TO TEST VARIOUS PIVOT SELECTION METHODS--------
		sortArrayMedian();
		
		long timeToSort = System.nanoTime();
		writerSorted.println(Arrays.toString(arr));
		//printArray();
		System.out.println(timeToSort - stopwatch);
		} catch (Exception er) {
			System.out.println("Caught Exception " + er.getMessage());
		}
		
		writerUnsorted.close();
		writerSorted.close();

	}

	void printArray() {
		System.out.println(Arrays.toString(arr));
	}

	//Uses the first element as the pivot
	void sortArrayFirst() {
		sortArrayFirst(0, arr.length-1);
		return;
	}
	void sortArrayFirst(int start, int last) {
		if (start >= last) return;
		int pivot = start;
		//System.out.println(arr[pivot]);

		//Sorting
		int pivotVal = arr[pivot];

		int i = start;
		int j = last;
		while ( i <= j) {
			//move both pointers until values that need to be swapped are found
			while (arr[i] < pivotVal) {
				i++;
			}
			while (arr[j] > pivotVal) {
				j--;
			}

			//if the pointers are still on the correct sides
			if (i <= j) {
				//swap, then advance both pointers
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		//Recur
		sortArrayFirst(start, j);
		sortArrayFirst(i, last);
	}

	//uses a random element as the pivot
	void sortArrayRandom() {
		sortArrayRandom(0, arr.length-1);
		return;
	}
	void sortArrayRandom(int start, int last) {
		if (start >= last) return;
		int pivot = rand.nextInt(last - start) + start;

		//Sorting
		int pivotVal = arr[pivot];

		int i = start;
		int j = last;
		while ( i <= j) {
			//move both pointers until values that need to be swapped are found
			while (arr[i] < pivotVal) {
				i++;
			}
			while (arr[j] > pivotVal) {
				j--;
			}

			//if the pointers are still on the correct sides
			if (i <= j) {
				//swap, then advance both pointers
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		//Recur
		sortArrayRandom(start, j);
		sortArrayRandom(i, last);
	}

	//chooses three elements randomly, then uses the median as a pivot
	void sortArrayMedianRandom() {
		sortArrayMedianRandom(0, arr.length-1);
		return;
	}
	void sortArrayMedianRandom(int start, int last) {
		if (start >= last) return;
		int pivot;
		int piv1 = rand.nextInt(last - start) + start;
		int piv2 = rand.nextInt(last - start) + start;
		int piv3 = rand.nextInt(last - start) + start;

		if (piv1 <= piv2) {
			if (piv2 <= piv3) {
				pivot = piv2;
			} else {
				if (piv1 <= piv3) pivot = piv3;
				else pivot = piv1;
			}
		} else {
			if (piv1 <= piv3) {
				pivot = piv1;
			} else {
				if (piv3 <= piv2) pivot = piv2;
				else pivot = piv3;
			}
		}

		//Sorting
		int pivotVal = arr[pivot];

		int i = start;
		int j = last;
		while ( i <= j) {
			//move both pointers until values that need to be swapped are found
			while (arr[i] < pivotVal) {
				i++;
			}
			while (arr[j] > pivotVal) {
				j--;
			}

			//if the pointers are still on the correct sides
			if (i <= j) {
				//swap, then advance both pointers
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		//Recur
		sortArrayMedianRandom(start, j);
		sortArrayMedianRandom(i, last);

	}

	//takes the first, last, and middle element, then chooses the median to use as a pivot
	void sortArrayMedian() {
		sortArrayMedian(0, arr.length-1);
		return;
	}
	void sortArrayMedian(int start, int last) {
		if (start >= last) return;

		int pivot;
		int piv1 = start;
		int piv2 = last;
		int piv3 = (start + last) / 2;

		if (piv1 <= piv2) {
			if (piv2 <= piv3) {
				pivot = piv2;
			} else {
				if (piv1 <= piv3) pivot = piv3;
				else pivot = piv1;
			}
		} else {
			if (piv1 <= piv3) {
				pivot = piv1;
			} else {
				if (piv3 <= piv2) pivot = piv2;
				else pivot = piv3;
			}
		}

		//Sorting
		int pivotVal = arr[pivot];

		int i = start;
		int j = last;
		while ( i <= j) {
			//move both pointers until values that need to be swapped are found
			while (arr[i] < pivotVal) {
				i++;
			}
			while (arr[j] > pivotVal) {
				j--;
			}

			//if the pointers are still on the correct sides
			if (i <= j) {
				//swap, then advance both pointers
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		//Recur
		sortArrayMedian(start, j);
		sortArrayMedian(i, last);

	}

	void createRandomArray(int size) {
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = rand.nextInt(1000000);
		}

		arr = result;
	}

}
