package abs;

import bean.User;
import mysql.ConstantNameInSql;

/**
 * 数据库 表 User操作接口
 * @author 2413324637
 *
 */
public interface IDBUserOperator extends DBCloseAble{
	/**
	 * 根据id获取全部信息，不包括密码
	 * @param phone 电话号
	 * @return
	 */
	public User getUser(String id);
	/**
	 * 根据电话号获取全部信息，不包括密码
	 * @param phone 电话号
	 * @return
	 */
	public User getUser(String phone,String password);
	/**
	 * 获取常用普通信息，包括id，头像，昵称
	 */
	public User getCommonInfo(String id);
	/**
	 * 获取昵称
	 * @param id
	 * @return
	 */
	public String getNickname(String id);
	/**
	 * 通过信息编号修改信息 //姓名 昵称 电话 邮箱 性别 生日
	 * @param infoIndex
	 * @param id
	 * @return
	 */
	public boolean updateInfoByIndex(int infoIndex,String id,String value);
	/**
	 * 通过序号查找数据库中对应字段名
	 * @param index 0-5
	 * @return
	 */
	public static  String getKey(int index) {
		switch(index) {
			case 0:return ConstantNameInSql.USER_INFO_NAME;
			case 1:return ConstantNameInSql.USER_INFO_NICKNAME;
			case 2:return ConstantNameInSql.USER_INFO_PHONE;
			case 3:return ConstantNameInSql.USER_INFO_EMAIL;
			case 4:return ConstantNameInSql.USER_INFO_SEX;
			case 5:return ConstantNameInSql.USER_INFO_BIRTHDAY;
		}
		return null;
	}
}
