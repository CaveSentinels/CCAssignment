/**
 *  @algorithm:
 *  1). We need to use external sorting to sort all the data because the data
 *      set is too large to be put all of them into memory.
 *  2). To sort so large a data set, we can first iterate through the original
 *      data set and split them into multiple disk files. Each file should be
 *      small enough to be sorted in memory.
 *  3). Sort each small file.
 *  4). Merge all the small files back to the large file. This is an N-way
 *      merge, we should maintain a pointer for each file that points at the
 *      first data, then find the currently smallest data of all the files
 *      and put it to the merged file.
 *  5). After all the small files are processed, we will have a sorted large
 *      file.
 */


import java.util.*;


public class Solution06 {

    private static final int DATA_TOTAL_COUNT = 1000;

    private static final int SMALL_FILE_SIZE = 100;

    // Simulate a large file.
    private static class LargeFile {
        private int [] content; // Data contained.
        private int next_index_write;   // The index of the next position to write
        private int next_index_read; // The index of the next number to be read

        public LargeFile() {
            content = new int[DATA_TOTAL_COUNT];
            next_index_write = 0;
            next_index_read = 0;
            for (int i = 0; i < DATA_TOTAL_COUNT; ++i) {
                content[i] = DATA_TOTAL_COUNT - i;
            }
        }

        public boolean put(int value) {
            if (next_index_write < content.length) {
                content[next_index_write++] = value;
                return true;
            }
            return false;
        }

        public boolean has_next() {
            return (next_index_read < content.length);
        }

        public int next() {
            return content[next_index_read++];
        }

        public void print() {
            System.out.println("====================");
            for (int i = 0; i < content.length; ++i) {
                System.out.print(content[i] + " ");
            }
            System.out.println("");
        }
    }

    // Simulate a small file.
    private static class SmallFile {
        private int [] content;
        private int next_index_write;
        private int next_index_read;

        public SmallFile() {
            content = new int[SMALL_FILE_SIZE];
            next_index_write = 0;
            next_index_read = 0;
        }

        public boolean put(int value) {
            if (next_index_write < content.length) {
                content[next_index_write++] = value;
                return true;
            }
            return false;
        }

        public void sort() {
            Arrays.sort(content);
        }

        public boolean has_next() {
            return (next_index_read < content.length);
        }

        public int first() {
            return content[next_index_read];
        }

        public int next() {
            return content[next_index_read++];
        }
    }

    private static void sort_large_file(LargeFile large_file) {
        Vector<SmallFile> small_files = new Vector<>();

        while (large_file.has_next()) {
            SmallFile sf = new SmallFile();
            for (int i = 0; i < SMALL_FILE_SIZE && large_file.has_next(); ++i) {
                int value = large_file.next();
                sf.put(value);
            }
            small_files.add(sf);
        }

        // Now we have split the large file into smaller files.

        // Sort each smaller file.
        for (SmallFile sf : small_files) {
            sf.sort();
        }

        // Finally, merge all the small files.
        while (!small_files.isEmpty()) {
            // Find the smallest number in all the small files.
            int min = small_files.get(0).first();
            int file_index = 0;
            for (int i = 1; i < small_files.size(); ++i) {
                int first = small_files.get(i).first();
                if (first < min) {
                    min = first;
                    file_index = i;
                }
            }

            // Now we've found the currently smallest number.
            // Write it back to the large file.
            large_file.put(min);

            // Move the pointer of the file that has the minimum value.
            small_files.get(file_index).next();
            if (!small_files.get(file_index).has_next()) {
                // If this file has no more numbers, remove it.
                small_files.remove(file_index);
            }
        }
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            LargeFile lf = new LargeFile();
            lf.print(); // Print original order.

            sort_large_file(lf);

            lf.print(); // Print sorted order.
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
