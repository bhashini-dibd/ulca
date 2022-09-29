package com.ulca.dataset.util;

import java.util.Random;

public class Utility {
	
	
	public static String getDatasetSubmitReferenceNumber() {
		return "11"+generateRandomDigits(8);
	}
	
	public static String getDatasetSearchReferenceNumber() {
		return "22"+generateRandomDigits(8);
	}
	
	public static int generateRandomDigits(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}


}
