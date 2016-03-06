package com.miller.checkoutapp;

import java.text.NumberFormat;
import java.util.*;

/*
 * Checkout Service
 */
public class CheckoutService 
{
    public static void main( String[] args )
    {
        // FIXME: output some documentation here
    }
	
	// pricingData 
	// map of item code to map of quantity to price
	// e.g., { A => { 1 => 2.00, 4 => 7.00 }, ... }
	private HashMap<String, HashMap<Integer, Double>> pricingData = new HashMap<String, HashMap<Integer, Double>>();
	
	// checkoutData 
	// map of code to quantity
	// e.g., { A => 4, B => 2, ... }
	private HashMap<String, Integer> checkoutData = new HashMap<String, Integer>();
	
	// clear checkout data
	public void clearCheckoutData()
	{
		checkoutData.clear();
	}
	
	// get map of quantity to price for an item code
	public HashMap<Integer, Double> getPricing( String code )
	{
		return pricingData.get(code);
	}
	
	// set map of quantity to price for an item code
	public void setPricing( String code, HashMap<Integer, Double> data )
	{
		// FIXME: validate data 
		// - contains at least one quantity to price mapping
		// - calculate price for 1 item if not defined
		pricingData.put(code, data);
	}
	
	// increment the quantity for an item code
	public void scanItem( String code )
	{
		// FIXME: validate we have pricing data for this code
		Integer quantity = checkoutData.containsKey(code) ? checkoutData.get(code) : 0;
		quantity += 1;
		checkoutData.put(code, quantity);
		//System.out.println("scanItem(" + code + "): quantity = " + quantity);
	}
	
	// print list of items, quantities, totals and total due
	public void printReceipt()
	{
		Double total = new Double(0);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		Map<String, Integer> sortedCheckoutData = new TreeMap<String, Integer>();
		sortedCheckoutData.putAll(checkoutData);
		System.out.println("----------------------");
		System.out.println(String.format("%1$-5s %2$-9s %3$6s", "Item", "Quantity", "Total"));
		System.out.println("----------------------");
		for (String code : sortedCheckoutData.keySet())
		{
			Integer totalQuantity = checkoutData.get(code);
			Double totalPrice = getTotal(code);
			System.out.println(String.format("%1$-5s %2$-9s %3$6s", code, totalQuantity, formatter.format(totalPrice)));
			total += totalPrice;
		}
		System.out.println("----------------------");
		System.out.println(String.format("%1$-5s %2$-9s %3$6s", "", "", formatter.format(total)));
	}
	
	// return the total price for an item code
	public Double getTotal(String code) 
	{
		Double total = new Double(0);
		// get a map of pricing data sorted descending, 
		HashMap<Integer, Double> priceMap = pricingData.get(code);
		Map<Integer, Double> sortedPriceMap = new TreeMap<Integer, Double>(Collections.reverseOrder());
		sortedPriceMap.putAll(priceMap);
		Integer totalQuantity = checkoutData.get(code);
		// for each quantity in pricing data, 
		for (Integer quantity : sortedPriceMap.keySet()) 
		{
			Double price = sortedPriceMap.get(quantity);
			Integer q = totalQuantity / quantity;
			Integer r = totalQuantity % quantity;
			// if total quantity is divisible by this price quantity, 
			// add to total and assign remainder to total quantity
			if (q > 0) 
			{
				total += (q * price);
				totalQuantity = r;
			}
		}
		return total;
	}
	
	// return the total price for all item codes scanned
	public Double getTotal() 
	{
		// for each code in checkout data, add to total
		Double total = new Double(0);
		for (String code : checkoutData.keySet())
		{
			total += getTotal(code);
		}
		return total;
	}
	
}
