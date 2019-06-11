package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import bean.AidTreat;
import constant.ConstantKeyInAskJson;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;
import mysql.AidTreatOperator;
import util.CloseUtils;
import util.TextUtils;
/**
 *发送病单的的处理情况
*/
public class AskAidTreatServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	//private static final String TAG = "AidTreatServlet";
	private AidTreatOperator dbAidTreatOperator;
	public AskAidTreatServlet() {
		dbAidTreatOperator = new AidTreatOperator();
		
	}
	
	@SuppressWarnings("unused")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String re = TextUtils.getTextFromInputStream(request.getInputStream());
		JSONObject responseJson =  getAidTreat(JSONObject.parseObject(re));
		response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.print(responseJson.toJSONString());
    	CloseUtils.close(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    private JSONObject getAidTreat(JSONObject requestJson) {
    	String handlerID = requestJson.getString(ConstantKeyInAskJson.AID_TREAT_HANDLER_ID);
    	String releaseID = requestJson.getString(ConstantKeyInAskJson.AID_TREAT_AID_RELEASE_ID);
    	JSONObject responseJson = new JSONObject();
    	AidTreat aidTreat = dbAidTreatOperator.getAidTreat(releaseID,handlerID);
    	if(aidTreat == null) {
    		responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SERVER_EXCEPTION);
        }else {
        	responseJson.put(ConstantKeyInJson.RESPONSE_CODE,HttpResponseCode.SUCCESS);
        	responseJson.put(ConstantKeyInJson.NET_AID_TREAT,aidTreat.toJsonObjectExceptNull());
        }
    	return responseJson;
    }
    
    @Override
    public void destroy() {
    	
    	super.destroy();
    	CloseUtils.close(dbAidTreatOperator);
    }
}
