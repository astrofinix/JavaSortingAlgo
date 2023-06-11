import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SortingAlgorithms {
    private static final String FILE_PATH = "dataset.txt";
    private static final int ARRAY_SIZE = 1_500_000;
    private static final int NUM_TRIALS = 20;

    public static void main(String[] args) {
        int[] array = readArrayFromFile(FILE_PATH);

        long[][] runtimes = new long[NUM_TRIALS][6];

        System.out.println("Bubble Sort:");
        for (int i = 0; i < NUM_TRIALS; i++) {
            int[] copyArray = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            bubbleSort(copyArray);
            long endTime = System.nanoTime();
            long runtime = endTime - startTime;
            runtimes[i][0] = runtime;
            printFormattedTime(runtime);
        }
        printRuntimeTable(runtimes, 0);

        System.out.println("\nInsertion Sort:");
        for (int i = 0; i < NUM_TRIALS; i++) {
            int[] copyArray = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            insertionSort(copyArray);
            long endTime = System.nanoTime();
            long runtime = endTime - startTime;
            runtimes[i][1] = runtime;
            printFormattedTime(runtime);
        }
        printRuntimeTable(runtimes, 1);

        System.out.println("\nSelection Sort:");
        for (int i = 0; i < NUM_TRIALS; i++) {
            int[] copyArray = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            selectionSort(copyArray);
            long endTime = System.nanoTime();
            long runtime = endTime - startTime;
            runtimes[i][2] = runtime;
            printFormattedTime(runtime);
        }
        printRuntimeTable(runtimes, 2);

        System.out.println("\nQuick Sort:");
        for (int i = 0; i < NUM_TRIALS; i++) {
            int[] copyArray = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            quickSort(copyArray, 0, copyArray.length - 1);
            long endTime = System.nanoTime();
            long runtime = endTime - startTime;
            runtimes[i][3] = runtime;
            printFormattedTime(runtime);
        }
        printRuntimeTable(runtimes, 3);

        System.out.println("\nBuilt-in Java sorting method (Arrays.sort):");
        for (int i = 0; i < NUM_TRIALS; i++) {
            int[] copyArray = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            Arrays.sort(copyArray);
            long endTime = System.nanoTime();
            long runtime = endTime - startTime;
            runtimes[i][4] = runtime;
            printFormattedTime(runtime);
        }
        printRuntimeTable(runtimes, 4);
    }

    private static int[] readArrayFromFile(String filePath) {
        int[] array = new int[ARRAY_SIZE];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null && index < ARRAY_SIZE) {
                array[index++] = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    // Sorting algorithm implementations...

    private static void printFormattedTime(long runtime) {
        long ns = runtime % 1000;
        runtime /= 1000;
        long ms = runtime % 1000;
        runtime /= 1000;
        long sec = runtime % 60;
        runtime /= 60;
        long min = runtime % 60;
        runtime /= 60;
        long hr = runtime;

        System.out.printf("%02d:%02d:%02d:%03d:%03d%n", hr, min, sec, ms, ns);
    }

    private static void printRuntimeTable(long[][] runtimes, int algorithmIndex) {
        System.out.println("Runtime Table:");
        System.out.println("----------------------------");
        System.out.println("| Trial |     Runtime      |");
        System.out.println("----------------------------");
        for (int i = 0; i < NUM_TRIALS; i++) {
            System.out.printf("|   %2d   | ", i + 1);
            printFormattedTime(runtimes[i][algorithmIndex]);
            System.out.println("|");
        }
        System.out.println("----------------------------");
        printAverageRuntime(runtimes, algorithmIndex);
        System.out.println();
    }

    private static void printAverageRuntime(long[][] runtimes, int algorithmIndex) {
        long totalRuntime = 0;
        for (int i = 0; i < NUM_TRIALS; i++) {
            totalRuntime += runtimes[i][algorithmIndex];
        }
        long averageRuntime = totalRuntime / NUM_TRIALS;
        System.out.print("Average Runtime: ");
        printFormattedTime(averageRuntime);
    }
    private static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private static void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
