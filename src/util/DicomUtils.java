package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ij.plugin.DICOM;
import static util.PrintUtils.*;
/**
 * dicom图像操作工具类
 * @author 2413324637
 *
 */
public class DicomUtils {

	//private final static String dcmPath = "F:\\毕设相关\\Head-Neck Cetuximab-Demo\\0522c0001\\08-23-1999-NeckHeadNeckPETCT-03251\\2-CT 5.0 H30s-55580\\000008.dcm";
	
	public static void main(String args[]) {
		//transAndSaveDicom2Jpg(dcmPath,"F:/毕设相关/Head-Neck Cetuximab-Demo");

  }
    /**
     * 保存dicom图像为jpg图像,保存的文件名不变
     * @param dicomPath dicom图像路径，包含文件名
     * @param jpgPath 保存的jpg图像路径
     */
    public static boolean transAndSaveDicom2Jpg(String dicomPath,String jpgPath) {
    	String fileName = TextUtils.getFileNameWithoutDot(dicomPath);
    	 DICOM dicom = new DICOM();
         dicom.run(dicomPath);
         BufferedImage bi = (BufferedImage) dicom.getImage();
         try {
			ImageIO.write(bi, "jpg", new File(jpgPath + File.separator + fileName+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			println("文件保存失败");
			return false;
		}finally {
			dicom.close();
		}
         return true;
    }

}
