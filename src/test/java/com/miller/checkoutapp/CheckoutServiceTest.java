package com.miller.checkoutapp;

import java.util.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/*
 * Unit tests for Checkout Service
 */
public class CheckoutServiceTest 
    extends TestCase
{
    /*
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CheckoutServiceTest( String testName )
    {
        super( testName );
    }

    /*
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CheckoutServiceTest.class );
    }

	private CheckoutService svc = new CheckoutService();
	
	// set up the test data
	// PRODUCT CODE    UNIT PRICE    VOLUME PRICE
	// ------------    ----------    ------------
	// A               $2            4 for $7
	// B               $12
	// C               $1.25         6 for $6
	// D               $0.15
	@Override
    protected void setUp() throws Exception
    {
        super.setUp();
		HashMap<Integer, Double> map1 = new HashMap<Integer, Double>(); {
			map1.put(1, new Double(2));
			map1.put(4, new Double(7));
		}
		HashMap<Integer, Double> map2 = new HashMap<Integer, Double>(); {
			map2.put(1, new Double(12));
		}
		HashMap<Integer, Double> map3 = new HashMap<Integer, Double>(); {
			map3.put(1, new Double(1.25));
			map3.put(6, new Double(6));
		}
		HashMap<Integer, Double> map4 = new HashMap<Integer, Double>(); {
			map4.put(1, new Double(0.15));
		}
		svc.setPricing("A", map1);
		svc.setPricing("B", map2);
		svc.setPricing("C", map3);
		svc.setPricing("D", map4);
	}

    /*
     * Test cases
     */
	
	// TEST 1: Items ABCDABAA Total $32.40
	public void test1()
    {
		String[] items = {"A", "B", "C", "D", "A", "B", "A", "A"};
		Double total_expected = new Double(32.40);
		
		svc.clearCheckoutData();
		for (String s : items) 
		{
			svc.scanItem(s);
		}
		Double total = svc.getTotal();
        assertTrue( 
			"got total = " + total + ", expected  total = " + total_expected, 
			Double.compare(total, total_expected) == 0 
		);
		System.out.println("Receipt for test 1");
		svc.printReceipt();
    }

	// TEST 2: Items CCCCCCC Total $7.25
	public void test2()
    {
		String[] items = {"C", "C", "C", "C", "C", "C", "C"};
		Double total_expected = new Double(7.25);
		
		svc.clearCheckoutData();
		for (String s : items) 
		{
			svc.scanItem(s);
		}
		Double total = svc.getTotal();
        assertTrue( 
			"got total = " + total + ", expected  total = " + total_expected, 
			Double.compare(total, total_expected) == 0 
		);
		System.out.println("Receipt for test 2");
		svc.printReceipt();
    }

	// TEST 3: Items ABCD Total $15.40
	public void test3()
    {
		String[] items = {"A", "B", "C", "D"};
		Double total_expected = new Double(15.40);
		
		svc.clearCheckoutData();
		for (String s : items) 
		{
			svc.scanItem(s);
		}
		Double total = svc.getTotal();
        assertTrue( 
			"got total = " + total + ", expected  total = " + total_expected, 
			Double.compare(total, total_expected) == 0 
		);
		System.out.println("Receipt for test 3");
		svc.printReceipt();
    }
	 
	// TEST 4: Items A * 10 Total $18.00
	public void test4()
    {
		String[] items = new String[10];
		for (int i=0; i<(items.length); i++ ) {
			items[i] = "A";
		}
		Double total_expected = new Double(18.00);
		
		svc.clearCheckoutData();
		for (String s : items) 
		{
			svc.scanItem(s);
		}
		Double total = svc.getTotal();
        assertTrue( 
			"got total = " + total + ", expected  total = " + total_expected, 
			Double.compare(total, total_expected) == 0 
		);
		System.out.println("Receipt for test 4");
		svc.printReceipt();
    }
	 
	// TEST 5: Update price definition 
	// PRODUCT CODE    UNIT PRICE    VOLUME PRICE
	// ------------    ----------    ------------
	// A               $2            4 for $7, 6 for $8
	// Items A * 10 Total $15.00
	public void test5()
    {
		// update price map
		HashMap<Integer, Double> map = svc.getPricing("A");
		map.put(6, new Double(8));
		svc.setPricing("A", map);
		// define items and expected total
		String[] items = new String[10];
		for (int i=0; i<(items.length); i++ ) {
			items[i] = "A";
		}
		Double total_expected = new Double(15.00);
		// scan items
		svc.clearCheckoutData();
		for (String s : items) 
		{
			svc.scanItem(s);
		}
		Double total = svc.getTotal();
        assertTrue( 
			"got total = " + total + ", expected  total = " + total_expected, 
			Double.compare(total, total_expected) == 0 
		);
		System.out.println("Receipt for test 5");
		svc.printReceipt();
    }
	 
}
