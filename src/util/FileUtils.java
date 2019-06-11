package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 文件操作工具类
 * @author 2413324637
 *
 */
public class FileUtils {
	/**
	 * 设置文件路径、文件名，给定输入流保存文件
	 * @param path
	 * @param fileName
	 * @param is
	 * @return
	 */
	public static boolean saveFile(String path,String fileName,InputStream is) {
		File file = new File(path);
    	if(!file.exists()) {
    		file.mkdirs();
    	}
    	FileOutputStream fos = null;
    	try {
    		fos = new FileOutputStream(path+File.separator+fileName);//创建一个文件输出流
            FileChannel readChannel = ((FileInputStream)is).getChannel();//获取读通道
            FileChannel writeChannel = fos.getChannel();//获取读通道
            ByteBuffer buffer = ByteBuffer.allocate(1024);//创建一个缓冲区
            //int length = 0;//判断输入流中的数据是否已经读完的标识
            while(true){//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                buffer.clear();
                int len = readChannel.read(buffer);//读入数据
                if(len < 0){
                    break;//读取完毕 
                }
                buffer.flip();
                writeChannel.write(buffer);//写入数据
            }
            return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}finally {
    		CloseUtils.close(is);
            CloseUtils.close(fos);
    	}
        
	}
}
