package mysql;

import static util.PrintUtils.println;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import abs.IDBAidCaseOperator;
import bean.AidCase;
import bean.AidRecord;
import bean.AidRelease;
import bean.AidTreat;
import util.CloseUtils;

public class AidCaseOperator implements IDBAidCaseOperator {
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    private AidTreatOperator aidTreatOperator;
    private AidReleaseOperator aidReleaseOperator;
    private AidRecordOperator aidRecordOperator;
    
	public AidCaseOperator() {
		init();
	}
	/**
     * 初始化数据库配置。
     */
    public void init() {
    	conn = DBHelper.getConnection();
    	aidTreatOperator = new AidTreatOperator();
        aidReleaseOperator = new AidReleaseOperator();
        aidRecordOperator = new AidRecordOperator();
    	if (conn == null){
    		println("数据库连接为空");
    		return;
    	}else {
    		try {
				stt = conn.createStatement();
			} catch (Exception e) {
				println("创建sql语句对象失败");
			}
    	}
    	
    }
	

	@Override
	public List<AidCase> getRandomAidCases(int start, int end) {
		List<AidCase> list = new ArrayList<AidCase>();
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_CASE)
            .append(" where ")
            .append(ConstantNameInSql.AID_CASE_AID_TREAT_ID)
            .append(" is not null")
            .append(" limit ")
        	.append(start)
        	.append(",")
        	.append(end)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            while(set.next()) {
            	AidCase aidCase = new AidCase();
            	aidCase.transSqlToJavaBean(set);
            	list.add(aidCase);
            	AidRecord aidRecord = aidRecordOperator.getAidRecord(aidCase.getAidRecordID());
            	aidCase.setAidRecord(aidRecord);
            	AidRelease aidRelease = aidReleaseOperator.getHandledAidRelease(aidCase.getAidReleaseID());
            	aidCase.setAidRelease(aidRelease);
            	AidTreat aidTreat = aidTreatOperator.getAidTreat(aidCase.getAidTreatID());
            	aidCase.setAidTreat(aidTreat);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return list;
	}
	@Override
	public AidCase getHandledAidCase(AidRelease aidRelease) {
		AidCase aidCase = null;
		try {
			StringBuilder sb = new StringBuilder("select * from ");
	        sb.append(ConstantNameInSql.TABLE_AID_CASE)
	        .append(" where ")
	        .append(ConstantNameInSql.AID_CASE_AID_RELEASE_ID)
	        .append(" = ")
	        .append(aidRelease.getId())
	        .append(" and ")
	        .append(ConstantNameInSql.AID_CASE_AID_RECORD_ID)
	        .append(" = ")
	        .append(aidRelease.getAidRecord().getId())
	        .append(" and ")
	        .append(ConstantNameInSql.AID_CASE_AID_TREAT_ID)
	        .append(" is not null")
	    	.append(";");
	        set = stt.executeQuery(sb.toString());
	        if(set.next()) {
	        	aidCase = new AidCase();
	        	aidCase.transSqlToJavaBean(set);
	        	aidCase.setAidRecord(aidRelease.getAidRecord());
	        	aidCase.setAidRelease(aidRelease);
	        	AidTreat aidTreat  = aidTreatOperator.getAidTreat(aidCase.getAidTreatID());
	        	aidCase.setAidTreat(aidTreat);
	        	return aidCase;
	        }
		}catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return null;
	}
	@Override
	public List<AidCase> getHandledAidCases(String handleEmployeeId, int start, int end) {
		List<AidRelease> listReleases = aidReleaseOperator.getHandledAidReleasesIncludeAidRecordInfo(handleEmployeeId, start, end);
		if(listReleases == null) return null;
		else{
			List<AidCase> aidCaseList = new ArrayList<AidCase>();
			for(AidRelease a:listReleases) {
				aidCaseList.add(getHandledAidCase(a));
			}
			return aidCaseList;
		}
		
	}
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
        CloseUtils.close(aidTreatOperator);
        CloseUtils.close(aidReleaseOperator);
        CloseUtils.close(aidRecordOperator);
	}
}
