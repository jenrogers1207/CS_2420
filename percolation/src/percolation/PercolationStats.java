/********************************************************
 *
 *  Project :  <Percolation>
 *  File    :  <Percolate>
 *  Name    :  <Robert Lawrence, Jen Rogers>
 *  Date    :  <Date created: 05/22/2017 (pdd: 05/26/2017)>
 *
 *	1) Description: This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *	2) Data Structures:
 *
 *	3) Algorithms:
 *
 *	4) Methods:		draw(Percolation, int), main(String[] args)
 *    
 *	5) Dependencies: Percolation.java StdDraw.java In.java	
 *
 *  Changes :  < Description: N/A |date of modifications: N/A >
 *
 ********************************************************/

package percolation;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] results;

  //Construct the stats class
    public PercolationStats(int size, int numExperiments) {
        assertPositive(size);
        assertPositive(numExperiments);
        results = new double[numExperiments];
        runExperiments(size, numExperiments);
    }

    //determine if input is positive or not
    private void assertPositive(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
    }

    //Run mSize experiments with an nSize matrix
    private void runExperiments(int nSize, int mSize) {
        for (int i = 0; i < mSize; i++) {
            results[i] = buildRunExperiment(nSize);
        }
    }

    //build and run single experiment
    private double buildRunExperiment(int sizeN) {
        Percolation p = new Percolation(sizeN);
        int openSpaces = 0;
        do {
        	int row = StdRandom.uniform(0, sizeN);
        	int col = StdRandom.uniform(0, sizeN);
            if (!p.isOpen(row, col)){
                p.open(row, col);
                openSpaces++;
            }
        } while (!p.percolates());

        return (double) openSpaces/((double) sizeN*sizeN);
    }

    /********************************************************
	* Below are the stats methods...combine perhaps?
    
    ********************************************************/
    //TODO combine maybe
    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - confidence();
    }

    private double confidence() {
        return (1.96 * stddev() / Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return mean() + confidence();
    }
    
    public String getStats() {
    	return "\n mean	= " + mean() +
    	       "\n stddev = " + stddev() +
       	       "\n 95% confidence interval = " + confidenceLo() + ", " + confidenceHi();
    }
    
    /********************************************************
	* Above are the stats methods...combine perhaps?
    
    ********************************************************/
    
    //must sets args in window ex: 10 500 or 20 1000
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       // System.out.println("mean                    = " + stats.mean());
       // System.out.println("stddev                  = " + stats.stddev());
       // System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    
    System.out.println(stats.getStats());
    }
}
