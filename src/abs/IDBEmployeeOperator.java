package abs;

import bean.Employee;
import bean.User;
import mysql.ConstantNameInSql;

/**
 * 数据库 表 Employee操作接口
 * @author 2413324637
 *
 */
public interface IDBEmployeeOperator extends DBCloseAble{
	/**
	 * 根据id查询所有信息，其中User只获取id,查询一次数据库
	 * @param id
	 */
	public Employee getEmployee(String id);
	/**
	 * 根据id查询所有信息，查询 两次数据库，基本信息userinfo通过第二次查询获得,
	 * @param id
	 */
	public Employee getEmployeeIncludeBasicInfo(String id);
	/**
	 * 获取对外显示的基本信息，包括id,基本信息id、科、室,查询两次数据库
	 * @param id
	 * @return
	 */
	public Employee getCommonInfo(String id);
	/**
	 * 根据id查找Name,查询2次数据库
	 * @param id
	 * @return
	 */
	public String getNickname(String id);
	/**
	 * 根据id查找基本信息id
	 * @param id
	 * @return
	 */
	public String getBasicId(String id);
	
	/**
	 * 根据基本信息查找对应工作信息
	 * @param user
	 * @return
	 */
	public Employee getEmployee(User user);
	
	/**
	 * 通过信息编号修改信息  /工号 医院编号 科 室
	 * @param isNormal 修改的信息是基本信息还是员工信息
	 * @param infoIndex
	 * @param id
	 * @return
	 */
	public boolean updateInfoByIndex(boolean isNormal,int infoIndex,String id,String value);
	
	public static  String getKey(int index) {
		switch(index) {
			case 0:return ConstantNameInSql.EMPLOYEE_INFO_EMPLOYEE_ID;
			case 1:return ConstantNameInSql.EMPLOYEE_INFO_HOSPITAL_ID;
			case 2:return ConstantNameInSql.EMPLOYEE_INFO_DEPARTMENT;
			case 3:return ConstantNameInSql.EMPLOYEE_INFO_ROOM;
		}
		return null;
	}
	
}
