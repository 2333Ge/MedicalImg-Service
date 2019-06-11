package test;

import java.util.Arrays;
import java.util.List;

import bean.AidRecord;
import bean.AidRelease;
import bean.User;
import mysql.ConstantNameInSql;
import mysql.DBUtils;
import util.CloseUtils;

public class TestDBUtils {

	public static void main(String[] a) {
    	DBUtils dbUtils = new DBUtils();
    	testSelect(dbUtils);
    	//dbUtils.selectAll(ConstantNameInSql.TABLE_AID_RECORD);
    	//println(dbUtils.getAboveLastId(ConstantNameInSql.TABLE_AID_RECORD));
    	CloseUtils.close(dbUtils);
    }
    public static void testInsert1(DBUtils dbUtils) {
    	User user = new User();
    	user.setPassword("123456");
    	user.setSex(0);
    	user.setNickname("1234agefaf56");
    	user.setHeadImg("text.jpg");
    	user.setEmail("555jy来御灵fea5");
    	user.setPhone("1289gfaagj12");
    	System.out.println(dbUtils.insertAndReturnId(ConstantNameInSql.TABLE_USER_INFO,user));
    }
    public static void testInsert2(DBUtils dbUtils) {
    	AidRecord aidRecord = new AidRecord();
    	aidRecord.setChiefComplaint("gageggegaeg");
    	List<String> listImg = Arrays.asList("a.jpg","b.jpg");
    	//String img = "listImg" + ":["+ listImg.get(1) + "," + listImg.get(2)+ "]";
    	//aidRecord.setImgList(listImg);
    	dbUtils.insert(ConstantNameInSql.TABLE_AID_RECORD,aidRecord);
    }
    public static void testInsert3(DBUtils dbUtils) {
    	AidRelease aidRelease = new AidRelease();
    	aidRelease.setState(5);
    	dbUtils.insert(ConstantNameInSql.TABLE_AID_RELEASE,aidRelease);
    }
    public static void testSelect(DBUtils dbUtils) {
    	dbUtils.selectAll(ConstantNameInSql.TABLE_AID_RELEASE);
    }
}
