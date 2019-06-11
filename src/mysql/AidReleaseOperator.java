package mysql;

import static util.PrintUtils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import abs.IDBAidReleaseOperator;
import bean.AidRecord;
import bean.AidRelease;
import util.CloseUtils;
/**
 * 表AidRelease操作
 * @author 2413324637
 *
 */
public class AidReleaseOperator implements IDBAidReleaseOperator{
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    private AidRecordOperator aidRecordOperator;
	public AidReleaseOperator() {
		init();
	}
	/**
     * 初始化数据库配置。
     */
    public void init() {
    	conn = DBHelper.getConnection();
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
    public AidRelease getHandledAidRelease(String id) {
    	AidRelease aidRelease = null;
    	try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_RELEASE)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(" and ")
        	.append(ConstantNameInSql.AID_RELEASE_STATE)
        	.append(" = ")
        	.append(10)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            if(set.next()) {
            	aidRelease = new AidRelease();
            	aidRelease.transSqlToJavaBean(set);
            	return aidRelease;
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    	return null;
    }
	@Override
	public List<AidRelease> getUnHandledAidReleases(String employeeId,int start,int end) {
		List<AidRelease> list = new ArrayList<AidRelease>();
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_RELEASE)
            .append(" where ")
        	.append(ConstantNameInSql.AID_RELEASE_HANDLE_EMPLOYEE_ID)
        	.append(" = ")
        	.append(employeeId)
        	.append(" and ")
        	.append(ConstantNameInSql.AID_RELEASE_STATE)
        	.append("<")
        	.append(5)
            .append(" limit ")
        	.append(start)
        	.append(",")
        	.append(end)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            while(set.next()) {
            	AidRelease aidRelease = new AidRelease();
            	aidRelease.transSqlToJavaBean(set);
            	list.add(aidRelease);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return list;
	}
	@Override
	public List<AidRelease> getUnHandledAidReleasesIncludeAidRecordInfo(String employeeId, int start, int end) {
		List<AidRelease> aidReleases = getUnHandledAidReleases(employeeId, start, end);
		for(AidRelease a:aidReleases) {
    		AidRecord record = a.getAidRecord();
    		String id = record.getId();
    		record = aidRecordOperator.getAidRecord(id);
    		a.setAidRecord(record);
    	}
		return aidReleases;
	}
	@Override
	public List<AidRelease> getHandledAidReleases(String employeeId,int start,int end) {
		List<AidRelease> list = new ArrayList<AidRelease>();
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_RELEASE)
            .append(" where ")
        	.append(ConstantNameInSql.AID_RELEASE_HANDLE_EMPLOYEE_ID)
        	.append(" = ")
        	.append(employeeId)
        	.append(" and ")
        	.append(ConstantNameInSql.AID_RELEASE_STATE)
        	.append("=")
        	.append(10)
            .append(" limit ")
        	.append(start)
        	.append(",")
        	.append(end)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            while(set.next()) {
            	AidRelease aidRelease = new AidRelease();
            	aidRelease.transSqlToJavaBean(set);
            	list.add(aidRelease);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return list;
	}
	@Override
	public List<AidRelease> getHandledAidReleasesIncludeAidRecordInfo(String employeeId, int start, int end) {
		List<AidRelease> aidReleases = getHandledAidReleases(employeeId, start, end);
		for(AidRelease a:aidReleases) {
    		AidRecord record = a.getAidRecord();
    		String id = record.getId();
    		record = aidRecordOperator.getAidRecord(id);
    		a.setAidRecord(record);
    	}
		return aidReleases;
	}
	@Override
	public List<AidRelease> getUploadAllAidReleases(String uploadEmployeeId, int start, int end) {
		List<AidRelease> list = new ArrayList<AidRelease>();
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_RELEASE)
            .append(" where ")
        	.append(ConstantNameInSql.AID_RELEASE_UPLOAD_EMPLOYEE_ID)
        	.append(" = ")
        	.append(uploadEmployeeId)
            .append(" limit ")
        	.append(start)
        	.append(",")
        	.append(end)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            while(set.next()) {
            	AidRelease aidRelease = new AidRelease();
            	aidRelease.transSqlToJavaBean(set);
            	list.add(aidRelease);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return list;
	}
	@Override
	public List<AidRelease> getUploadAllAidReleasesIncludeAidRecordInfo(String uploadEmployeeId, int start, int end) {
		List<AidRelease> aidReleases = getUploadAllAidReleases(uploadEmployeeId, start, end);
		for(AidRelease a:aidReleases) {
    		AidRecord record = a.getAidRecord();
    		String id = record.getId();
    		record = aidRecordOperator.getAidRecord(id);
    		a.setAidRecord(record);
    	}
		return aidReleases;
	}
	
	@Override
	public boolean setAidReleaseHandled(String aidReleaseID) {
		StringBuilder sb = new StringBuilder("update ");
        sb.append(ConstantNameInSql.TABLE_AID_RELEASE)
        .append(" set ")
        .append(ConstantNameInSql.AID_RELEASE_STATE)
        .append(" = 10")
        .append(" where ")
    	.append(ConstantNameInSql.ID)
    	.append(" = ")
    	.append(aidReleaseID)
    	.append(";");
        try {
			stt.execute(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        return true;
	}
	
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
        CloseUtils.close(aidRecordOperator);
	}

}
