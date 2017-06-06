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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	private WeightedQuickUnionUF	path;
	private WeightedQuickUnionUF	backwash;
	private boolean[]				twoDimensionGrid;
	private final int				SIZE, TOP_INDEX, BOTTOM_INDEX, NUM_OF_NODES;

	//Constructor of Class Percolation
	public Percolation( int sizeN )
	{
		SIZE = sizeN;
		if( SIZE <= 0 )
		{
			throw new IllegalArgumentException();
		}
		NUM_OF_NODES = SIZE * SIZE;
		TOP_INDEX = NUM_OF_NODES;
		BOTTOM_INDEX = NUM_OF_NODES + 1;
		twoDimensionGrid = new boolean[ NUM_OF_NODES ];
		path = new WeightedQuickUnionUF( NUM_OF_NODES + 2 );
		backwash = new WeightedQuickUnionUF( NUM_OF_NODES + 2 );

	}

	//Ensure that new input is within range
	private void validateInput( int row, int col )
	{
		if( row < 0 || col < 0 || row > SIZE - 1 || col > SIZE - 1 )
		{
			throw new IndexOutOfBoundsException();
		}
	}
	
	//Connect nodes that are next to each other
	private void union( int row, int col )
	{
		path.union( row, col );
		backwash.union( row, col );
	}

	//Convert 2D array into 1D vector (see excel file)
	private int mapToIndex( int row, int col )
	{
		return row * SIZE + col;
	}

	//private helper to open node
	private void openSite( int row, int col )
	{
		twoDimensionGrid[mapToIndex( row, col )] = true;
	}

	//public method to open node
	public void open( int row, int col )
	{
		validateInput( row, col );
		openSite( row, col );
		connectToVNode( row, col );
		connectAllNeighbors( row, col );
	}

	//find and connect to virtual nodes
	private void connectToVNode( int row, int col )
	{
		if( row == 0 )
		{
			// must connect both here
			path.union( TOP_INDEX, mapToIndex( row, col ) );
			backwash.union( TOP_INDEX, mapToIndex( row, col ) );
		}

		if( row == SIZE - 1 )
		{
			// Only connect backwash here. This is why backwash
			// is checked in percolates
			backwash.union( BOTTOM_INDEX, mapToIndex( row, col ) );
		}
	}

	//find and connect to all neighbors
	//see all other private connect methods
	private void connectAllNeighbors( int row, int col )
	{
		connectTopNode( row, col );
		connectBottomNode( row, col );
		connectLeftNode( row, col );
		connectRightNode( row, col );
	}

	private void connectTopNode( int row, int col )
	{
		if( row > 0 && isOpen( row - 1, col ) )
		{
			int connectDot1 = mapToIndex( row - 1, col );
			int connectDot2 = mapToIndex( row, col );
			union( connectDot1, connectDot2 );
		}
	}

	private void connectBottomNode( int row, int col )
	{
		if( row < SIZE - 1 && isOpen( row + 1, col ) )
		{
			union( mapToIndex( row + 1, col ), mapToIndex( row, col ) );
		}
	}

	private void connectLeftNode( int row, int col )
	{
		if( col > 0 && isOpen( row, col - 1 ) )
		{
			union( mapToIndex( row, col - 1 ), mapToIndex( row, col ) );
		}
	}

	private void connectRightNode( int row, int col )
	{
		if( col < SIZE - 1 && isOpen( row, col + 1 ) )
		{
			union( mapToIndex( row, col + 1 ), mapToIndex( row, col ) );
		}
	}

	//Determine if site is open or not
	public boolean isOpen( int row, int col )
	{
		validateInput( row, col );
		return twoDimensionGrid[mapToIndex( row, col )];
	}

	//Determine if site is connected to TOP or not
	public boolean isFull( int row, int col )
	{
		validateInput( row, col );
		return path.connected( mapToIndex( row, col ), TOP_INDEX );
	}

	//Determine if TOP is connected to BOTTOM
	public boolean percolates()
	{
		// MUST BE backwash because path is not directly
		// connected to BOTTOM INDEX SEE connectToVNode
		return backwash.connected( BOTTOM_INDEX, TOP_INDEX );
	}
}
	
	