package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.AidRecord;
import bean.AidRelease;
import constant.Paths;
import mysql.ConstantNameInSql;
import mysql.DBUtils;
import util.CloseUtils;
import util.DicomUtils;
import util.FileUtils;
import util.TextUtils;

import static util.PrintUtils.*;

/**
 * 模拟病单上传功能
 * @author 2413324637
 *
 */
@WebServlet("/UploadHandleServlet")
public class UploadHandleServlet extends HttpServlet{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private DBUtils dbUtils ;
	
	
	public UploadHandleServlet(){
		super();
		dbUtils = new DBUtils();
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//初始化
		boolean isRelease = false;
		boolean isUploadSuccess = false;
		String rootUploadPath = Paths.rootUploadPath;
		String rootTempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		AidRecord aidRecord = new AidRecord();
		AidRelease aidRelease = new AidRelease();
		List<String> imgList = new ArrayList<String>();
    	aidRecord.setImgList(imgList);
    	aidRelease.setAidRecord(aidRecord);
        
        File tempFile = new File(rootTempPath);
        if(!tempFile.exists()&&!tempFile.isDirectory()){
            System.out.println("临时目录不存在！");
            tempFile.mkdir();
        }
        String message = "";
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
            diskFileItemFactory.setSizeThreshold(1024*100);
            //设置上传时生成的临时文件的保存目录
            diskFileItemFactory.setRepository(tempFile);
            //2、创建一个文件上传解析器
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            //解决上传文件名的中文乱码
            fileUpload.setHeaderEncoding("UTF-8");
            //监听文件上传进度
            fileUpload.setProgressListener(new ProgressListener(){
                public void update(long pBytesRead, long pContentLength, int arg2) {
                   println("已处理：" + pBytesRead*100/pContentLength + "%");
                }
            });
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }
            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
            fileUpload.setFileSizeMax(1024*1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
            fileUpload.setSizeMax(1024*1024*10);
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String keyDefault = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    String key = new String(keyDefault.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(" value: " + value);
                    System.out.println(" key: " + key);
                    if("isRelease".equals(key)) {
            			isRelease = "on".equals(value);
                    }else {
                    	saveInformation(aidRecord,aidRelease,key, value);
                    }
                    
                }else{
                    //如果fileitem中封装的是上传文件，得到上传的文件名称，
                    String fileName = item.getName();
                    System.out.println(fileName);
                    if(fileName == null||fileName.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = TextUtils.getFileName(fileName);
                    saveInformation(aidRecord, aidRelease, "file", fileName);
                    //得到上传文件的扩展名
                    String fileExtName = TextUtils.getFileExtName(fileName);
                    if(!"dcm".equals(fileExtName) && !"jpg".equals(fileExtName)){
                        request.setAttribute("message", "上传文件的类型不符合！！仅支持dicom文件和jpg文件");
                        request.getRequestDispatcher("/message.jsp").forward(request, response);
                        return;
                    }
                    println("上传文件的扩展名为:"+fileExtName);
                    
                    
                    String lastAboveID = dbUtils.getAboveLastId(ConstantNameInSql.TABLE_AID_RECORD);
                  //得到文件保存的路径
                    String savePathStr = getSavePath(rootUploadPath
                    		,fileName
                    		,ConstantNameInSql.TABLE_AID_RECORD
                    		,lastAboveID);
                    System.out.println("保存路径为:"+savePathStr);
                    String filePath = savePathStr + File.separator + fileName;
                    isUploadSuccess = FileUtils.saveFile(savePathStr, fileName, item.getInputStream());
                    if("dcm".equals(fileExtName)) {
                    	//得到文件保存的路径
                        String saveJpgPathStr = getExcatlyPath(rootUploadPath
                        		,"jpg"
                        		,ConstantNameInSql.TABLE_AID_RECORD
                        		,lastAboveID);
                        DicomUtils.transAndSaveDicom2Jpg(filePath, saveJpgPathStr);
                    }
                    message = isUploadSuccess ? "文件上传成功" : "文件上传失败";
                    
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "单个文件超出最大值！！！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }catch (FileUploadBase.SizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }catch (FileUploadException e) {
            e.printStackTrace();
            message = "文件上传失败";
        }
        request.setAttribute("message",message);
        request.getRequestDispatcher("/message.jsp").forward(request, response);
        if(isUploadSuccess) {
        	insertInfo2DB(aidRecord,aidRelease,isRelease);
        	isUploadSuccess = false;
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * 根据文件名得到到拓展名，+表名+id号构造并返回存储路径
     * @param rootpath
     * @param fileUrl
     * @param table
     * @param id
     * @return
     */
    private static String getSavePath(String rootpath,String fileUrl,String table,String id) {
    	String fileExtName = TextUtils.getFileExtName(fileUrl);
    	File file = new File(rootpath+"\\" + fileExtName +"\\" + table + "\\" + id);
    	if(!file.exists()) {
    		file.mkdirs();
    	}
    	return file == null ? null:file.getAbsolutePath();
    }
    /**
     * 指明拓展名和表名和id号构造并返回存储路径
     * @param rootpath
     * @param fileUrl
     * @param table
     * @param id
     * @return
     */
    private  static String getExcatlyPath(String rootpath,String fileExtName,String table,String id) {
    	File file = new File(rootpath+"\\" + fileExtName +"\\" + table + "\\" + id);
    	if(!file.exists()) {
    		file.mkdirs();
    	}
    	return file == null ? null:file.getAbsolutePath();
    }
    
    /**
     * 将表单中的普通输入项数据存储给aidRecord和aidRelease
     * @param key
     * @param value
     */
    private void saveInformation(AidRecord aidRecord,AidRelease aidRelease,String key, String value) {
    	switch (key) {
    		case "sex": 
    			aidRecord.setSex("boy".equals(value) ? ConstantNameInSql.BOY_INT_IN_MYSQL : ConstantNameInSql.GIRL_INT_IN_MYSQL);
    			break;
    		case "age":
    			aidRecord.setAge(Integer.parseInt(value));
    			break;
    		
    		case "chiefComplaint":
    			aidRecord.setChiefComplaint(value);
    			break;
    		case "clincalManifestation":
    			aidRecord.setClinicalManifestation(value);
    			break;
    		case "imagingFeatures":
    			aidRecord.setImagingFeatures(value);
    			break;
    		case "file":
    			aidRecord.getImgList().add(value);
    			break;
    		case "handleEmployeeId":
    			aidRelease.setHandleEmployeeId(value);
    			break;
    		case "uploadEmployeeId":
    			aidRelease.setUploadEmployeeId(value);
    			break;
    		case "state":
    			aidRelease.setState(Integer.parseInt(value));
    			break;
			default:
				break;
    			
    	}
    }
    /**
     * 保存数据到数据库
     */
    private void insertInfo2DB(AidRecord aidRecord,AidRelease aidRelease,boolean isRelease) {
    	String id = dbUtils.insertAndReturnId(ConstantNameInSql.TABLE_AID_RECORD, aidRecord);
    	//创建的时候是不知道id的，需要手动赋值
    	aidRecord.setId(id);
    	if(isRelease) {
    		dbUtils.insert(ConstantNameInSql.TABLE_AID_RELEASE, aidRelease);
    	}
    	isRelease = false;
    }
    
    @Override
    public void destroy() {
    	super.destroy();
    	CloseUtils.close(dbUtils);
    }
}
