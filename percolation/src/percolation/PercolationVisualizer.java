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

import java.awt.Font;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

public class PercolationVisualizer
{

	// delay in milliseconds (controls animation speed)
	private static final int DELAY = 100;

	// draw N-by-N percolation system
	public static void draw(Percolation perc, int N) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);

        // draw N-by-N grid
        int opened = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                }
                else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                }
                else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col + .5, N - row - 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 20));
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text(.25*N, -N*.025, opened + " open sites");
        if (perc.percolates())
        {
        	StdDraw.text( N/2, N/2, "percolates" );;
        }
		else 
			{
			StdDraw.text(.75*N,-N*.025,"does not percolate");
			}

	}

	//arg is input.txt
	public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        // turn on animation mode
        StdDraw.show(0);

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        draw(perc, N);
        StdDraw.show(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt() ;
            int j = in.readInt();
            perc.open(i, j);
            draw(perc, N);
            StdDraw.show(DELAY);
        }
    }
}
