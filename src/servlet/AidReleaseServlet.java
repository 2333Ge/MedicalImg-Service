package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import constant.ConstantKeyInAskJson;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;

import mysql.AidReleaseOperator;


import util.CloseUtils;
import util.TextUtils;

import bean.AidRelease;

/**
 * 查询某人的要处理的病单Servlet
 *
 */
public class AidReleaseServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	//private static final String TAG = "AidReleaseServlet";
	private AidReleaseOperator dbAidReleaseOperator;
	
	
	public AidReleaseServlet() {
		dbAidReleaseOperator = new AidReleaseOperator();
		
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str = TextUtils.getTextFromInputStream(request.getInputStream());
    	JSONObject json = JSONObject.parseObject(str);
    	String employeeId = json.getString(ConstantKeyInAskJson.AID_RELEASE_HANDLE_EMPLOYEE_ID);
    	int employeeType = json.getIntValue(ConstantKeyInAskJson.AID_RELEASE_REQUEST_PERSON_TYPE);
    	int type = json.getIntValue(ConstantKeyInAskJson.AID_RELEASE_TYPE);
    	int start  = json.getIntValue(ConstantKeyInAskJson.AID_RELEASE_START);
    	int end  = json.getIntValue(ConstantKeyInAskJson.AID_RELEASE_END);
    	
    	JSONObject responseJson = getAidReleases( employeeId, employeeType, type, start,end);
    	
        response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.print(responseJson.toJSONString());
    	CloseUtils.close(out);
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * 
     * @param employeeId
     * @param employeeType 0 id为处理者id，1 id为发布者id
     * @param type 0为未处理病单，1为已处理病单, 2为所有病单
     * @param start
     * @param end
     * @return
     */
    private JSONObject getAidReleases(String employeeId,int employeeType,int type,int start,int end) {
    	List<AidRelease> aidReleases = null;
    	if(employeeType == 0) {//处理者
    		if(type == 0) {
    			aidReleases = dbAidReleaseOperator.getUnHandledAidReleasesIncludeAidRecordInfo(employeeId, start, end);   	
    		}else if(type == 1) {
    			aidReleases = dbAidReleaseOperator.getHandledAidReleasesIncludeAidRecordInfo(employeeId, start, end);

    		}
    	}else if(employeeType == 1) {//发布者
    		if(type == 2) {
    			aidReleases = dbAidReleaseOperator.getUploadAllAidReleasesIncludeAidRecordInfo(employeeId, start, end);   	
    		}
    	}
    	JSONObject responseJson = new JSONObject();
        if(aidReleases == null) {
        	responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SERVER_EXCEPTION);
        }else if(aidReleases.size() == 0) {
        	responseJson.put(ConstantKeyInJson.RESPONSE_CODE,HttpResponseCode.NULL_RESULT);
        }else {
        	responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	JSONArray responceArray = new JSONArray();
        	for(AidRelease aidRelease : aidReleases) {
        		responceArray.add(aidRelease.toJsonObject());
        	}
        	responseJson.put(ConstantKeyInJson.NET_AID_RELEASE_ARRAY, responceArray);
        	
        }
        System.out.println(responseJson);
        return responseJson;
    	
    }
    @Override
    public void destroy() {
    	super.destroy();
    	CloseUtils.close(dbAidReleaseOperator);
    	
    }
}
