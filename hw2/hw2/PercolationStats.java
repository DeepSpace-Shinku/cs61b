package hw2;

import org.junit.rules.Stopwatch;

import java.util.Random;

public class PercolationStats {
    private double mean;
    private double stdDev;
    private double[] Xs;
    private int N;
    private int T;
    private Random rand;

    public PercolationStats(int N, int T, PercolationFactory pf)
    {
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        this.rand = new Random();
        this.Xs = new double[T];
        for (int i = 0; i < T; i++){
            Percolation p = pf.make(N);
            while (!p.percolates()){
                int index = getNotOpenedIndex(p);
                p.open(index / N, index % N);
            }
            Xs[i] = p.numberOfOpenSites() * 1.0 / (N * N);
        }
        calculateMean();
        calculateStdDev();
    }

    private void calculateMean()
    {
        double result = 0;
        for (double x: Xs){
            result += x;
        }
        result /= Xs.length;
        this.mean = result;
    }

    private void calculateStdDev()
    {
        double result = 0;
        for (double x: Xs){
            result += (x - mean) * (x - mean);
        }
        result /= Xs.length - 1;
        result = Math.sqrt(result);
        this.stdDev = result;
    }

    private int getNotOpenedIndex(Percolation p)
    {
        int index =  rand.nextInt(N * N);
        while(p.isOpen(index / N, index % N)){
            index = rand.nextInt(N * N);
        }
        return index;
    }

    public double mean()
    {
        return this.mean;
    }

    public double stddev()
    {
        return this.stdDev;
    }

    public double confidenceLow()
    {
        return mean - 1.96 * stdDev / Math.sqrt(T);
    }

    public double confidenceHigh()
    {
        return mean + 1.96 * stdDev / Math.sqrt(T);
    }

    private static void main(String[] args)
    {
        PercolationStats ps = new PercolationStats(100, 100, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }

}
