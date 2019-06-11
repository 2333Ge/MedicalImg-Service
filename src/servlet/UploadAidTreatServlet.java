package servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import bean.AidTreat;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;
import mysql.AidReleaseOperator;
import mysql.AidTreatOperator;
import mysql.DBUtils;
import util.CloseUtils;
import util.TextUtils;
/**
 * 接收对应病单的的处理情况，插入数据库，并返回状态码
*/
public class UploadAidTreatServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private DBUtils dbUtils;
	private AidReleaseOperator dbAidReleaseOperator;
	private AidTreatOperator dbAidTreatOperator;
	public UploadAidTreatServlet() {
		dbUtils = new DBUtils();
		dbAidReleaseOperator = new AidReleaseOperator();
		dbAidTreatOperator = new AidTreatOperator();
		
	}
	
	@SuppressWarnings("unused")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AidTreat aidTreat = new AidTreat();
		JSONObject responsJson = new JSONObject();
    	String str = TextUtils.getTextFromInputStream(request.getInputStream());
    	if(JSONObject.isValidObject(str)) {//合法的json输入串
    		JSONObject json = JSONObject.parseObject(str);
        	aidTreat.transJsonToJavaBean(json);
        	//dbUtils.insert(ConstantNameInSql.TABLE_AID_TREAT, aidTreat);
        	dbAidTreatOperator.updateAidTreat(aidTreat);
        	boolean isSuccess = dbAidReleaseOperator.setAidReleaseHandled(aidTreat.getAidReleaseId());//设置aidTreat状态
        	if(isSuccess) {
        		responsJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	}else {
        		responsJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.ERROR_DATABASE);
        	}
    		
    	}else {
    		responsJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.REQUEST_EXCEPTION);
    	} 
        response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.print(responsJson.toJSONString());
    	CloseUtils.close(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    
    
    @Override
    public void destroy() {
    	super.destroy();
    	CloseUtils.close(dbUtils);
    	CloseUtils.close(dbAidReleaseOperator);
    	CloseUtils.close(dbAidTreatOperator);
    }
}
