import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Assignment4 {

    static Scanner scan;
    static String[] pageRefs;
    static int numberOfFrames; 
    static Random rand;

    public static void main(String[] args) {

        System.out.println("Number of Page Faults include initial population of frames.");

        //ERROR CASES
        if (args.length > 2) {
            System.out.println("Too many inputs were provided. Please input only the file name and the number of frames.");
            return;
        }
        System.out.println("First arg is " + args[0]);
        System.out.println("Second arg is " + args[1]);

        try {
            scan = new Scanner(new File(args[0]));
        } catch (Exception e) {
            System.out.println("First input was not a valid file name. Please input name of file to be used and ensure that file is in the same folder as this program.");
            return;
        }

        try {
            numberOfFrames = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Second input was not a valid integer. Please input integer number of frames to be used.");
            return;
        }

        //ACTUAL CODE

        ArrayList<String[]> fileContents = new ArrayList<String[]>();
        while (scan.hasNextLine()) {
            //SCAN NEXT LINE, BREAK LINE INTO ARRAY OF STRINGS
            fileContents.add(scan.nextLine().split(" ", 0));
        }

        int[] countOfPageFaults = new int[fileContents.size()];

        //FIFO
        for (int i = 0; i < fileContents.size(); i++) {
            pageRefs = fileContents.get(i);
            countOfPageFaults[i] = FIFO();
        }

        double average = averageOfInts(countOfPageFaults);
        System.out.println("The average number of page faults for FIFO was " + average); 

        //OPTIMAL
        for (int i = 0; i < fileContents.size(); i++) {
            pageRefs = fileContents.get(i);
            countOfPageFaults[i] = Optimal();
        }

        average = averageOfInts(countOfPageFaults);
        System.out.println("The average number of page faults for Optimal was " + average); 

        //RANDOM
        rand = new Random();
        for (int i = 0; i < fileContents.size(); i++) {
            pageRefs = fileContents.get(i);
            countOfPageFaults[i] = Random();
        }

        average = averageOfInts(countOfPageFaults);
        System.out.println("The average number of page faults for Random was " + average); 

        //LRU
        for (int i = 0; i < fileContents.size(); i++) {
            pageRefs = fileContents.get(i);
            countOfPageFaults[i] = LRU();
        }

        average = averageOfInts(countOfPageFaults);
        System.out.println("The average number of page faults for LRU was " + average); 



    }

    //DON'T NEED THIS 
    /*
    private static int[] convertStringsToInts(String[] strs) {
        int[] ret = new int[strs.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
    */

    private static double averageOfInts(int[] intsToAverage) {
        int total = 0;
        for (int indivCount : intsToAverage) {
            total += indivCount;
        }
        double average = ((double) total) / ((double) intsToAverage.length);
        return average;
    }

    private static int FIFO() {

        //frames is a queue, each time a page is ref, put it at end of queue, always replace from start of queue
        int countOfFIFOPageFaults = 0;
        LinkedList<String> frames = new LinkedList<String>();
        for (String ref : pageRefs) {
            if (frames.contains(ref)) {
                frames.remove(ref);
                frames.add(ref);
            } else {
                countOfFIFOPageFaults++;
                if (frames.size() < numberOfFrames) {
                    frames.add(ref);
                } else {
                    frames.removeFirst();
                    frames.add(ref);
                }
            }
        }
        return countOfFIFOPageFaults;

    }

    private static int Optimal() {

        int countOfOptimalPageFaults = 0;
        ArrayList<String> frames = new ArrayList<String>();
        for (int i = 0; i < pageRefs.length; i++) {
            if (frames.contains(pageRefs[i])) {
                //We're good, do nothing
            } else {
                countOfOptimalPageFaults++;
                if (frames.size() < numberOfFrames) {
                    frames.add(pageRefs[i]);
                } else {
                    //go through each of the currently filled frames and check when that page will next be referenced. Whichever one is furthest out in front, replace.
                    int furthestRef = -1;
                    int indexOffurthestRef = 0;
                    for (int j = 0; j < frames.size(); j++) {
                        int currentFrameNextRef = nextInstanceOfRef(frames.get(j), i);
                        if (currentFrameNextRef == -1) {
                            indexOffurthestRef = j;
                            break;
                        } else {
                            if (currentFrameNextRef > furthestRef) {
                                furthestRef = currentFrameNextRef;
                                indexOffurthestRef = j;
                            }
                        }
                    }
                    frames.set(indexOffurthestRef, pageRefs[i]);
                }
            }

        }
        return countOfOptimalPageFaults;

    }

    //returns the number of references until the next time a given page is referenced
    private static int nextInstanceOfRef(String ref, int curLoc) {
        int ret = -1;
        for (int i = curLoc + 1; i < pageRefs.length; i++) {
            if (pageRefs[i].equals(ref) ) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    private static int Random() {

        int countOfRandomPageFaults = 0;
        ArrayList<String> frames = new ArrayList<String>();
        for (String ref : pageRefs) {
            if (!frames.contains(ref)) {
                countOfRandomPageFaults++;
                if (frames.size() < numberOfFrames) {
                    frames.add(ref);
                } else {
                    frames.set(rand.nextInt(numberOfFrames), ref);
                }
            }
        }
        return countOfRandomPageFaults;
    }

    private static int LRU() {

        int countOfLRUPageFaults = 0;
        ArrayList<String> frames = new ArrayList<String>();
        int[] lru = new int[numberOfFrames];
        for (int i = 0; i < pageRefs.length; i++) {
            if (frames.contains(pageRefs[i])) {
                lru[frames.indexOf(pageRefs[i])] = i;
            } else {
                countOfLRUPageFaults++;
                if (frames.size() < numberOfFrames) {
                    lru[frames.size()] = i;
                    frames.add(pageRefs[i]);
                } else {
	                //FIND index of min val in lru
	                int minIndex = 0;
	                for (int j = 1; j < numberOfFrames; j++) {
	                    if (lru[j] < lru[minIndex]) {
	                        minIndex = j;
	                    }
	                }
	                lru[minIndex] = i;
	                frames.set(minIndex, pageRefs[i]);
                }
            }
        }
        return countOfLRUPageFaults;
    }

}