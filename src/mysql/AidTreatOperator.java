package mysql;

import static util.PrintUtils.println;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import abs.IDBAidTreatOperator;
import abs.InsertJavaBeanToSqlAble;
import bean.AidTreat;
import util.CloseUtils;

public class AidTreatOperator implements IDBAidTreatOperator{
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    private DBUtils dbUtils;
	public AidTreatOperator() {
		init();
	}
	/**
     * 初始化数据库配置。
     */
    public void init() {
    	conn = DBHelper.getConnection();
    	dbUtils = new DBUtils();
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
    public AidTreat getAidTreat(String id) {
    	AidTreat aidTreat = null;
    	try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_TREAT)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            if(set.next()) {
            	aidTreat = new AidTreat();
            	aidTreat.transSqlToJavaBean(set);
            	return aidTreat;
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    	return null;
    }
    @Override
    public AidTreat getAidTreat(String aidReleaseId, String handlerId) {
    	AidTreat aidTreat = null;
    	try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_TREAT)
            .append(" where ")
        	.append(ConstantNameInSql.AID_TREAT_AID_RELEASE_ID)
        	.append(" = ")
        	.append(aidReleaseId)
        	.append(" and ")
        	.append(ConstantNameInSql.AID_TREAT_HANDLER_ID)
        	.append(" = ")
        	.append(handlerId)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            println(sb.toString());
            if(set.next()) {
            	aidTreat = new AidTreat();
            	aidTreat.transSqlToJavaBean(set);
            	return aidTreat;
            	}
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    	return null;
    }
	@Override
	public boolean isAidTreatExits(String aidReleaseID,String handlerId) {
		StringBuilder sb = new StringBuilder("select ");
        sb.append(ConstantNameInSql.ID)
        .append(" from ")
        .append(ConstantNameInSql.TABLE_AID_TREAT)
        .append(" where ")
    	.append(ConstantNameInSql.AID_TREAT_AID_RELEASE_ID)
    	.append(" = ")
    	.append(aidReleaseID)
    	.append(" and ")
    	.append(ConstantNameInSql.AID_TREAT_HANDLER_ID)
    	.append(" = ")
    	.append(handlerId)
    	.append(";");
        try {
			set = stt.executeQuery(sb.toString());
			if(set.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean updateAidTreat(AidTreat aidTreat) {
		if(isAidTreatExits(aidTreat.getAidReleaseId(),aidTreat.getHandlerId())) {
			StringBuilder sb = new StringBuilder("update ");
	        sb.append(ConstantNameInSql.TABLE_AID_TREAT)
	        .append(" set ")
	        .append(ConstantNameInSql.AID_TREAT_AID_SUMMARY)
	        .append(" = ")
	        .append(InsertJavaBeanToSqlAble.includingNull(aidTreat.getAidSummary()))
	        .append(",")
	        .append(ConstantNameInSql.AID_TREAT_ANALYSIS)
	        .append(" = ")
	        .append(InsertJavaBeanToSqlAble.includingNull(aidTreat.getAnalysis()))
	        .append(",")
	        .append(ConstantNameInSql.AID_TREAT_CLINICAL_DIAGNOSIS)
	        .append(" = ")
	        .append(InsertJavaBeanToSqlAble.includingNull(aidTreat.getClinicalDiagnosis()))
	        .append(" where ")
	    	.append(ConstantNameInSql.AID_TREAT_AID_RELEASE_ID)
	    	.append(" = ")
	    	.append(aidTreat.getAidReleaseId())
	    	.append(" and ")
	    	.append(ConstantNameInSql.AID_TREAT_HANDLER_ID)
	    	.append(" = ")
	    	.append(aidTreat.getHandlerId())
	    	.append(";");
	        try {
				stt.execute(sb.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	        return true;
		}else {
			dbUtils.insert(ConstantNameInSql.TABLE_AID_TREAT, aidTreat);
        	return true;
		}
	}
	
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
        CloseUtils.close(dbUtils);
	}
	
}
