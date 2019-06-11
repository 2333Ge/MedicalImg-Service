package test;

import mysql.AidReleaseOperator;
import util.CloseUtils;

public class TestAidReleaseOperator {

	public static void main(String[] a) {
		AidReleaseOperator operator = new AidReleaseOperator();
		testSelect(operator);
    	CloseUtils.close(operator);
    }
	
    public static void testSelect(AidReleaseOperator operator) {
    	operator.getUnHandledAidReleases("2", 0, 3);
    }

}
