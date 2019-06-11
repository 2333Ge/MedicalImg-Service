package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bean.AidCase;
import constant.ConstantKeyInAskJson;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;
import mysql.AidCaseOperator;
import util.CloseUtils;
import util.TextUtils;

/**
 * 查询病例Servlet
 *
 */
public class AidCaseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private AidCaseOperator aidCaseOperator;
	
	public AidCaseServlet(){
		aidCaseOperator = new AidCaseOperator();
		
		
		int a = 0;
		String s = a+"";
		Integer.parseInt(s);
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AidCase> aidCaseList = getInfoFormInputStream(request.getInputStream());
        JSONObject json = new JSONObject();
        if(aidCaseList == null) {
        	json.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SERVER_EXCEPTION);
        }else if(aidCaseList.size() == 0) {
        	json.put(ConstantKeyInJson.RESPONSE_CODE,HttpResponseCode.NULL_RESULT);
        }else {
        	json.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	JSONArray responceArray = new JSONArray();
        	for(AidCase aidCase : aidCaseList) {
        		responceArray.add(aidCase.toJsonObjectExceptNull());
        	}
        	json.put(ConstantKeyInJson.NET_AID_CASE_ARRAY, responceArray);
        	
        }
        response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.print(json.toJSONString());
    	CloseUtils.close(out);
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    private List<AidCase> getInfoFormInputStream(InputStream is) {
    	List<AidCase> listCases = null;
    	String str = TextUtils.getTextFromInputStream(is);
    	JSONObject json = JSONObject.parseObject(str);
    	String employeeId = json.getString(ConstantKeyInAskJson.AID_CASE_REQUEST_PERSON_ID);
    	int type = json.getIntValue(ConstantKeyInAskJson.AID_CASE_REQUEST_TYPE);
    	int start  = json.getIntValue(ConstantKeyInAskJson.AID_CASE_START);
    	int end  = json.getIntValue(ConstantKeyInAskJson.AID_CASE_END);
    	if(type == 0 ) {//随机获取
    		listCases = aidCaseOperator.getRandomAidCases(start, end);
    	}else if(type == 1) {
    		listCases = aidCaseOperator.getHandledAidCases(employeeId, start, end);
    	}
    	return listCases;
    	
    }
    
    @Override
    public void destroy() {
    	super.destroy();
    	CloseUtils.close(aidCaseOperator);
    	
    }
	
}
