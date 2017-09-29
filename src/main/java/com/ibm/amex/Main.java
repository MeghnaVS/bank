package com.ibm.amex;

public class Main{

	public static void main(String s[]){
		
		int i=Integer.parseInt(s[0]);
		int j=Integer.parseInt(s[1]);
		CalculatorService service=new CalculatorService();
		System.out.println(service.doSum(i,j));
		System.out.println(service.doDiff(i,j));
		
	}

}