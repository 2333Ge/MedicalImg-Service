package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static util.PrintUtils.*;
/**
 * 文本操作工具类
 * @author 2413324637
 *
 */
public class TextUtils {

	public static void main(String[] args) {
		print(getFileNameWithoutDot("jaigjeijigjag\\test.dcm"));
	}
	
	/**
     * 得到文件拓展名
     * @param fileUrl
     * @return
     */
    public static String getFileExtName(String fileUrl) {
    	return fileUrl.substring(fileUrl.lastIndexOf(".")+1);
    }
    /**
     * 得到文件名
     * @param url
     * @return
     */
    public static String getFileName(String url) {
    	return url.substring(url.lastIndexOf(File.separator)+1);
    }
    /**
     * 得到没有点的文件名
     * @param url
     * @return
     */
    public static String getFileNameWithoutDot(String url) {
    	String fileName = getFileName(url);
    	if(fileName.contains(".")) {
    		return fileName.substring(0,fileName.lastIndexOf("."));
    	}else {
    		return fileName;
    	}
    	
    }
    /**
     * 从InputStream中获取String
     * @param is
     * @return 
     */
    public static String getTextFromInputStream(InputStream is) {
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
    	StringBuilder sb = new StringBuilder();
    	String s;
    	try {
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
		} catch (IOException e) {
			return null;
		}finally {
			CloseUtils.close(reader);
		}
    	return sb.toString();
    }
}
