package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] numbers = new int[M];
        int bucketNum;
        for (int i = 0; i < M; i++){
            numbers[i] = 0;
        }
        for (Oomage oomage: oomages) {
            bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            numbers[bucketNum] += 1;
        }

        for (int i = 0; i < M; i++){
            if (numbers[i] < oomages.size() / 50 || numbers[i] > oomages.size() / 2.5) return false;
        }
        return true;
    }
}
