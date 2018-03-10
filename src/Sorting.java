import java.util.Random;
import java.util.Scanner;
// import apache commons language stopwatch package
import org.apache.commons.lang3.time.StopWatch;

public class Sorting {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        StopWatch sw = new StopWatch();
        int size;
        // initialize accumulator and time variables
        long acc = 0, time = 0;
        // continue program execution while size>0
        do {
            System.out.print("Enter array size (enter value less than 1 to exit): ");
            size = kb.nextInt();
            // break loop if size<1
            if(size<1) continue;
            // declare array
            int[] arr;
            // int choice = 0;
            // run each algorithm 5 times, then get the average run time of each
            for(int j=1; j<5; j++) {
                // reset accumulator
                acc = 0;
                switch(j) {
                    case 1: System.out.println("Exchange Sort");
                        if(size>=500000) {
                            System.out.println("Exchange sort omitted.\n");
                            continue;
                        }
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            exchangeSort(arr);
                            sw.stop();
                            time = sw.getNanoTime();
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 2: System.out.println("Merge Sort");
                        if(size>=500000000) {
                            System.out.println("Merge sort omitted.\n");
                            continue;
                        }
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            mergeSort(arr);
                            time = sw.getNanoTime();
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 3: System.out.println("Quick Sort");
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            quickSort(arr, 0, arr.length-1);
                            time = sw.getNanoTime();
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 4: continue;
                    default: System.out.println("Invalid selection");
                }
                sw.reset();
                System.out.println("\n");
            }
            /* while(choice != 4) {
                System.out.println("Select sorting algorithm: \n(1) Exchange Sort\n(2) Merge Sort\n(3) Quick Sort\n(4) Continue");
                choice = kb.nextInt();
                // reset array with random values for each loop iteration
                // reset accumulator
                acc = 0;
                switch(choice) {
                    case 1: System.out.println("Exchange Sort");
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            exchangeSort(arr);
                            sw.stop();
                            time = sw.getTime(TimeUnit.NANOSECONDS);
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 2: System.out.println("Merge Sort");
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            mergeSort(arr);
                            time = sw.getTime(TimeUnit.NANOSECONDS);
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 3: System.out.println("Quick Sort");
                        for(int i=1; i<6; i++) {
                            arr = new int[size];
                            randomizer(arr);
                            sw.start();
                            quickSort(arr, 0, arr.length-1);
                            time = sw.getTime(TimeUnit.NANOSECONDS);
                            acc += time;
                            System.out.println(i + ") Time Elapsed: " + time);
                            sw.reset();
                        }
                        acc/=5;
                        System.out.println("\nAverage Time Elapsed: " + acc);
                        break;
                    case 4: continue;
                    default: System.out.println("Invalid selection");
                }
                sw.reset();
                System.out.println("\n");
            } */
        } while(size > 0);
    }

    // method to assign new random values to array
    public static void randomizer(int[] target) {
        Random rng = new Random();
        for(int i=0; i<target.length; i++) {
            target[i] = rng.nextInt(target.length)+1;
        }
    }

    public static void exchangeSort(int[] x) {
        int temp; //temporary holding variable
        for(int i=0; i<x.length-1; i++) {
            for(int j=i+1; j<x.length; j++) {
                if(x[i]>x[j]) { //check if x[i] is greater than the next element
                    temp = x[i]; //swap if x[i] is greater
                    x[i] = x[j];
                    x[j] = temp;
                }
            }
        }
        return;
    }

    public static void mergeSort(int[] x) {
        if(x.length>1) {
            // split source array in half recursively until array size = 2
            final int h = (x.length/2);
            final int m = x.length-h;
            int[] x1 = new int[h];
            int[] x2 = new int[m];
            for(int i=0; i<h; i++) {
                x1[i] = x[i];
            }
            for(int i=0; i<m; i++) {
                x2[i] = x[i+h];
            }
            mergeSort(x1);
            mergeSort(x2);
            // solve each half array
            merge(x1, x2, x);
        }
    }

    public static void merge(int[] x1, int[] x2, int[] x) {
        int i=0, j=0, k=0;
        // compare elements in each half array
        while(i<x1.length && j<x2.length) {
            // add smaller element into source array
            if(x1[i] < x2[j]) {
                x[k] = x1[i];
                i++;
            }
            else {
                x[k] = x2[j];
                j++;
            }
            k++;
        }
        // copy remaining array into source array
        if(i >= x1.length) {
            for(int l=j; l<x2.length; l++) {
                x[k] = x2[l];
                k++;
            }
        }
        else {
            for(int l=i; l<x1.length; l++) {
                x[k] = x1[l];
                k++;
            }
        }
    }

    public static void quickSort(int x[], int low, int high)
    {
        if (low < high)
        {
            // piv is partitioning index, arr[pi] is now at right place
            int piv = partition(x, low, high);
            // Recursively sort elements before partition and after partition
            quickSort(x, low, piv-1);
            quickSort(x, piv+1, high);
        }
    }

    public static int partition(int x[], int low, int high)
    {
        int pivot = x[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is less than or equal to pivot
            if (x[j] <= pivot)
            {
                i++;

                // swap x[i] and x[j]
                int temp = x[i];
                x[i] = x[j];
                x[j] = temp;
            }
        }

        // swap x[i+1] and x[high]
        int temp = x[i+1];
        x[i+1] = x[high];
        x[high] = temp;

        return i+1;
    }

}
