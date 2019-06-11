package util;

import java.util.List;
/**
 * 打印操作工具类
 * @author 2413324637
 *
 */
public class PrintUtils {
	public static void print(Object o){
		System.out.print(o);
	}
	public static void println(Object o){
		System.out.println(o);
	}
	public static void println(List<Object> list){
		for(Object o: list)
			print(o+" ");
	}
	public static void println(Object[] objects){
		for(Object o: objects)
			print(o+" ");
	}
	public static void println(int[] ints){
		for(Object o: ints)
			print(o+" ");
	}
}
