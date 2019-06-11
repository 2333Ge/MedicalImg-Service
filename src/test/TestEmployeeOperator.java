package test;

import mysql.AidReleaseOperator;
import mysql.EmployeeOperator;
import util.CloseUtils;
import static util.PrintUtils.*;
public class TestEmployeeOperator {
	public static void main(String[] a) {
		EmployeeOperator operator = new EmployeeOperator();
		testSelect(operator);
    	CloseUtils.close(operator);
    }
	
    public static void testSelect(EmployeeOperator operator) {
    	println(operator.getCommonInfo("1").toJsonObject().toString());
    }
}
