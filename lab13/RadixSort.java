/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int maxLen = maxLength(asciis);
        String[] sorted, sorting = asciis;
        for (int i = maxLen - 1; i >= 0; i--){
            sorting = sortHelperLSD(sorting, i);
        }
        sorted = sorting;
        return sorted;
    }

    // Return the maxLength of the all the strings;
    private static int maxLength(String[] asciis)
    {
        int max = 0;
        for (String s: asciis){
            if (s.length() > max){
                max = s.length();
            }
        }
        return max;
    }


    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] starts = setStarts(asciis, index);
        String[] result = new String[asciis.length];
        for (String s: asciis){
            result[starts[asciiAt(s, index)]] = s;
            starts[asciiAt(s, index)] += 1;
        }
        return result;
    }

    private static int[] setCounting(String[] asciis, int index)
    {
        int[] counting = new int[257];
        for (String s: asciis){
            counting[asciiAt(s, index)] += 1;
        }
        return counting;
    }

    private static int[] setStarts(String[] asciis, int index)
    {
        int[] counting = setCounting(asciis, index);
        int[] starts = new int[counting.length];
        int cumulative = 0;
        for (int i = 0; i < counting.length; i++) {
            starts[i] = cumulative;
            cumulative += counting[i];
        }
        return starts;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    /**
     * Return the ASCII code plus 1 of the nth character of a string.
     * If the length of the string is less than n, return LEAST(0).
     */
    private static int asciiAt(String s, int index) {
        final int LEAST = 0;
        if (s.length() <= index) {
            return LEAST;
        } else {
            return (int) s.charAt(index) + 1;
        }
    }

}
