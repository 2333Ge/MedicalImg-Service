package test;

public class a {
	public static void main(String[] args) {
		int a = 1234;
		int b = get(a);
		System.out.println(b);
		
	}
	public static int get(int n){
		if(n/10 == 0) {
			System.out.println(n);
			 return n;
		}
		else  {
			
			//int temp = ;
			//System.out.println(temp);
			return (n%10)*10 + get(n/10);
		}
	}
}
