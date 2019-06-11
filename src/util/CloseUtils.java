package util;

import java.io.Closeable;
import java.io.IOException;

import abs.DBCloseAble;
/**
 * 关闭连接工具类
 *
 */
public class CloseUtils {
	
    public static void close(Closeable closeable){
        if (closeable == null){
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("『Closeable』 close失败" + e.getMessage());
        }
    }
    
    public static void close(AutoCloseable closeable){
        if (closeable == null){
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("『Closeable』 close失败" + e.getMessage());
        }
    }
    
    public static void close(DBCloseAble closeable) {
    	closeable.close();
    }
}
