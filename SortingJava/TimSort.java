// Java program to perform TimSort.
class Tim {

    static int MIN_MERGE = 32;

    public static int minRunLength(int n)
    {
        assert n >= 0;

        // Becomes 1 if any 1 bits are shifted off
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    // This function sorts array from left index to to right index which is of size atmost RUN
    public static void insertionSort(int[] arr, int left,
                                     int right)
    {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    // Merge function merges the sorted runs
    public static void merge(int[] arr, int l, int m, int r)
    {
        // Original array is broken in two parts left and right array
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++) {
            left[x] = arr[l + x];
        }
        for (int x = 0; x < len2; x++) {
            right[x] = arr[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // After comparing, we merge those two array
        // in larger sub array
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            }
            else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        // of left, if any
        while (i < len1) {
            arr[k] = left[i];
            k++;
            i++;
        }

        // Copy remaining element
        // of right, if any
        while (j < len2) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    // Iterative Timsort function to sort the
  
    public static void timSort(int[] arr, int n)
    {
        int minRun = minRunLength(MIN_MERGE);

        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += minRun) {
            insertionSort(
                arr, i,
                Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        // Start merging from size
        // RUN (or 32).
        for (int size = minRun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {

                // Find ending point of left sub array
              
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1),
                                     (n - 1));
                if (mid < right)
                    merge(arr, left, mid, right);
            }
        }
    }

    // Utility function to print the Array
    public static void printArray(int[] arr, int n)
    {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("\n");
    }
    public static void main(String[] args)
    {
        int[] arr = { -2, 7,  15,  -14, 0, 15,  0, 7,
                      -7, -4, -13, 5,   8, -14, 12 };
        int n = arr.length;
        System.out.println("Given Array is");
        printArray(arr, n);

        timSort(arr, n);

        System.out.println("After Sorting Array is");
        printArray(arr, n);
    }
}
