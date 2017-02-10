package visnode.commons;

import java.util.Arrays;

/**
 * Helper class to work with arrays
 */
public class ArrayHelper {

    /**
     * Copies an array
     * 
     * @param original
     * @return int[][][]
     */
    public static int[][][] copy(int[][][] original) {
        int[][][] copy = Arrays.copyOf(original, original.length);
        for (int i = 0; i < copy.length; i++) {
            copy[i] = copy(original[i]);
        }
        return copy;
    }

    /**
     * Copies an array
     * 
     * @param original
     * @return int[][]
     */
    public static int[][] copy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = copy(original[i]);
        }
        return copy;
    }

    /**
     * Copies an array
     * 
     * @param original
     * @return int[]
     */
    public static int[] copy(int[] original) {
        return Arrays.copyOf(original, original.length);
    }
    
}
