package percolation;

import junit.framework.TestCase;

//import org.junit.Assert.*;


public class PercolationTest extends TestCase {

	private final static int SIZE = 4;


	public void test_close() {
		Percolation percolation = new Percolation(SIZE);
		boolean actual = percolation.isOpen(0, 0);
		assertEquals(false, actual);
	}

	
	public void test_open() {
		Percolation percolation = new Percolation(SIZE);
		percolation.open(0, 0);
		boolean actual = percolation.isOpen(0, 0);
		assertEquals(true, actual);
	}

	 
	public void test_simple_percolation() {
		Percolation percolation = new Percolation(SIZE);
		percolation.open(0, 1);
		percolation.open(1, 1);
		percolation.open(2, 1);
		percolation.open(3, 1);
		assertEquals(true, percolation.percolates());
	}

	 
	public void test_no_percolation() {
		Percolation percolation = new Percolation(SIZE);
		percolation.open(0, 1);
		percolation.open(2, 1);
		assertEquals(false, percolation.percolates());
	}

	 
	public void test_complex_percolation() {
		Percolation percolation = new Percolation(SIZE);
		percolation.open(0, 0);
		percolation.open(0, 1);
		percolation.open(0, 2);
		percolation.open(1, 3);
		percolation.open(2, 3);
		percolation.open(3, 3);
		assertEquals(false, percolation.percolates());
		percolation.open(0, 3);
		assertEquals(true, percolation.percolates());
	}

	 
	public void test_complex_percolation_two() {
		Percolation percolation = new Percolation(SIZE);
		percolation.open(0, 0);
		percolation.open(0, 2);
		percolation.open(0, 3);
		percolation.open(1, 1);
		percolation.open(1, 3);
		percolation.open(2, 0);
		percolation.open(2, 1);
		percolation.open(2, 2);
		percolation.open(3, 0);
		percolation.open(3, 2);
		percolation.open(3, 3);
		assertEquals(false, percolation.percolates());
		// Other open site percolates the system !
		percolation.open(1, 0);
		assertEquals(true, percolation.percolates());
	}

	


}