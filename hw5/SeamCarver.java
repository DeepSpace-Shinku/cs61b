import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SeamCarver {
    private Picture picture;
    //private List<Double> energies = new ArrayList<ArrayList<Double>>();

    public SeamCarver(Picture picture)
    {
        this.picture = picture;
    }

    private class SearchNode{
        public int value;
        public SearchNode prev;
        public SearchNode(int value, SearchNode prev)
        {
            this.value = value;
            this.prev = prev;
        }
    }

    /**
     * Return current picture
     */
    public Picture picture() {
        return this.picture;
    }

    /**
     * Return the width of the current picture
     */
    public int width() {
        return picture.width();
    }

    /**
     * Return the height of the current picture
     */
    public int height() {
        return picture.height();
    }

    /**
     * Return the energy of the pixel on the given index.
     */
    public double energy(int x, int y) {
        double deltaXSquare, deltaYSquare;
        deltaXSquare = calcDeltaXSquare(x, y);
        deltaYSquare = calcDeltaYSquare(x, y);
        return deltaXSquare + deltaYSquare;
    }

    /**
     * Return the sequence of horizontal indices of
     * the horizontal seam with smallest energy.
     */
    public int[] findHorizontalSeam() {
        /**
         * To avoid dealing with the special cases on the edges, we increase the size
         * of M by 2 and set the first and the last element with positive infinity.
         */
        double[] M = initializeM(), prevM, E;
        SearchNode[] seams = initializeSeams(), prevSeams;
        for (int i = 0; i < width(); i += 1){
            E = calcColumnE(i);
            prevM = M;
            M = initializeM();
            prevSeams = seams;
            seams = initializeSeams();
            for (int j = 0; j < height(); j += 1){
                int smallest = j + smallestIndex(prevM[j], prevM[j + 1], prevM[j + 2]);
                seams[j] = new SearchNode(j, prevSeams[smallest]);
                M[j + 1] = E[j] + prevM[smallest + 1];
            }
        }
        int minIndex = findMinIndex(M);
        int[] arraySeam = makeArraySeam(seams[minIndex - 1]);
        return arraySeam;
    }

    /**
     * Return a sequence of vertical indices of
     * the vertical seam with smallest energy.
     */

    public int[] findVerticalSeam() {
        picture = transposePicture(picture);
        int[] seam = findHorizontalSeam();
        picture = transposePicture(picture);
        return seam;
    }

    /**
     * Remove the given horizontal seam of the picture.
     */
    public void removeHorizontalSeam(int[] seam)
    {
        picture = removeHorizontalSeam(picture, seam);
    }

    /**
     * Remove the given vertical seam of the picture.
     */
    public void removeVerticalSeam(int[] seam)
    {
        picture = removeVerticalSeam(picture, seam);
    }

    /**
     * Return the new x index which to the dx of the original x index.
     */
    private int xIndexTo(int x, int dx) {
        if (x + dx == width()) return 0;
        if (x + dx < 0) return width() - 1;
        return x + dx;
    }

    /**
     * Return the new y index which to the dy of the original y index.
     */
    private int yIndexTo(int y, int dy) {
        if (y + dy == height()) return 0;
        if (y + dy < 0) return height() - 1;
        return y + dy;
    }

    private double calcDeltaYSquare(int x, int y) {
        Color topColor, bottomColor;
        double RY, GY, BY;
        topColor = picture.get(x, yIndexTo(y, 1));
        bottomColor = picture.get(x, yIndexTo(y, -1));
        RY = topColor.getRed() - bottomColor.getRed();
        GY = topColor.getGreen() - bottomColor.getGreen();
        BY = topColor.getBlue() - bottomColor.getBlue();
        return RY * RY + GY * GY + BY * BY;
    }

    private double calcDeltaXSquare(int x, int y) {
        Color leftColor, rightColor;
        double RX, GX, BX;
        leftColor = picture.get(xIndexTo(x, -1), y);
        rightColor = picture.get(xIndexTo(x, 1), y);
        RX = leftColor.getRed() - rightColor.getRed();
        GX = leftColor.getGreen() - rightColor.getGreen();
        BX = leftColor.getBlue() - rightColor.getBlue();
        return RX * RX + GX * GX + BX * BX;
    }

    /**
     * Remove the given horizontal seam of the picture.
     * @Source SeamRemover.java
     */
    private static Picture removeHorizontalSeam(Picture var0, int[] var1) {
        if (var1 == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (var0.width() == 1) {
            throw new IllegalArgumentException("Image width is 1.");
        } else if (var1.length != var0.width()) {
            throw new IllegalArgumentException("Seam length does not match image width.");
        } else {
            for (int var2 = 0; var2 < var1.length - 2; ++var2) {
                if (Math.abs(var1[var2] - var1[var2 + 1]) > 1) {
                    throw new IllegalArgumentException("Invalid seam, consecutive vertical indices are greater than one apart.");
                }
            }

            Picture var5 = new Picture(var0.width(), var0.height() - 1);

            for (int var3 = 0; var3 < var0.width(); ++var3) {
                int var4;
                for (var4 = 0; var4 < var1[var3]; ++var4) {
                    var5.set(var3, var4, var0.get(var3, var4));
                }

                for (var4 = var1[var3] + 1; var4 < var0.height(); ++var4) {
                    var5.set(var3, var4 - 1, var0.get(var3, var4));
                }
            }

            return var5;
        }
    }

    /**
     * Remove the given vertical seam of the picture.
     *
     * @Source SeamRemover.java
     */
    private static Picture removeVerticalSeam(Picture var0, int[] var1) {
        if (var1 == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (var0.height() == 1) {
            throw new IllegalArgumentException("Image height is 1.");
        } else if (var1.length != var0.height()) {
            throw new IllegalArgumentException("Seam length does not match image height.");
        } else {
            for (int var2 = 0; var2 < var1.length - 2; ++var2) {
                if (Math.abs(var1[var2] - var1[var2 + 1]) > 1) {
                    throw new IllegalArgumentException("Invalid seam, consecutive horizontal indices are greater than one apart.");
                }
            }

            Picture var5 = new Picture(var0.width() - 1, var0.height());

            for (int var3 = 0; var3 < var0.height(); ++var3) {
                int var4;
                for (var4 = 0; var4 < var1[var3]; ++var4) {
                    var5.set(var4, var3, var0.get(var4, var3));
                }

                for (var4 = var1[var3] + 1; var4 < var0.width(); ++var4) {
                    var5.set(var4 - 1, var3, var0.get(var4, var3));
                }
            }

            return var5;
        }
    }

    /** Calculate the energies of all pixels on the ith column of the picture*/
    private double[] calcColumnE(int i)
    {
        double[] E = new double[height()];
        for (int j = 0; j < height(); j += 1){
            E[j] = energy(i, j);
        }
        return E;
    }

    /** Return -1 if x is the smallest, 0 if y is the smallest, 1 if z is the smallest.*/
    private static int smallestIndex(double x, double y, double z)
    {
        if (x < y){
            if (x < z){
                return -1;
            }else{
                return 1;
            }
        }else{
            if (y > z){
                return 1;
            }else{
                return 0;
            }
        }
    }

    private SearchNode[] initializeSeams()
    {
        SearchNode[] seams = new SearchNode[height()];
        return seams;
    }

    private double[] initializeM()
    {
        double[] M = new double[height() + 2];
        M[0] = M[height() + 1] = Double.POSITIVE_INFINITY;
        return M;
    }

    private static int findMinIndex(double[] array)
    {
        int minIndex = -1;
        Double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < array.length; i += 1){
            if (array[i] < min){
                min = array[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int[] makeArraySeam(SearchNode seam)
    {
        int[] arraySeam = new int[width()];
        for (int i = width() - 1; i >= 0; i -= 1){
            arraySeam[i] = seam.value;
            seam = seam.prev;
        }
        return arraySeam;
    }

    private static Picture transposePicture(Picture picture)
    {
        Picture transposed = new Picture(picture.height(), picture.width());
        for (int i = 0; i < picture.width(); i += 1){
            for (int j = 0; j < picture.height(); j += 1){
                transposed.set(j, i, picture.get(i, j));
            }
        }
        return transposed;
    }

    public static void main(String[] args)
    {
        int x = 1, y = 2, z = 3;
        System.out.println(smallestIndex(x, y, z) == -1);
        System.out.println(smallestIndex(x, z, y) == -1);
        System.out.println(smallestIndex(y, x, z) == 0);
        System.out.println(smallestIndex(y, z, x) == 1);
        System.out.println(smallestIndex(z, x, y) == 0);
        System.out.println(smallestIndex(z, y, x) == 1);
    }



}
