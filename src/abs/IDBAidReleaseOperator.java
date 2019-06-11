package abs;

import java.util.List;

import bean.AidRelease;
/**
 * 数据库 表 AdiRelease操作接口
 * @author 2413324637
 *
 */
public interface IDBAidReleaseOperator extends DBCloseAble{
	/**
	 *  根据id查找病单,查询一次数据库，aidRecord只获取id
	 * @param id
	 * @return
	 */
	public AidRelease getHandledAidRelease(String id);
	/**
	 *  根据工作id查找发布的未处理的病单,查询一次数据库，aidRecord只获取id
	 * @param employeeId 待处理工作人员的id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getUnHandledAidReleases(String employeeId,int start, int end);
	/**
	 *根据工作id查找发布的未处理的病单，查询两次数据库，aidRecord通过第二次查询数据库获得
	 * @param employeeId 待处理工作人员的id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getUnHandledAidReleasesIncludeAidRecordInfo(String employeeId,int start, int end);
	/**
	 *  根据工作id查找发布的已处理的病单,查询一次数据库，aidRecord只获取id
	 * @param employeeId 待处理工作人员的id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getHandledAidReleases(String employeeId,int start, int end);
	/**
	 *根据工作id查找发布的未处理的病单，查询两次数据库，aidRecord通过第二次查询数据库获得
	 * @param employeeId 待处理工作人员的id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getHandledAidReleasesIncludeAidRecordInfo(String employeeId,int start, int end);
	/**
	 *设置aidRelease已处理， 将state设置为10,表示已经处理
	 * @param aidReleaseID
	 * @return
	 */
	public boolean setAidReleaseHandled(String aidReleaseID);
	/**
	 * 根据上传者id查找aidRelease,查询两次数据库，
	 * @param employeeId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getUploadAllAidReleasesIncludeAidRecordInfo(String uploadEmployeeId,int start, int end);
	
	/**
	 * 根据上传者id查找aidRelease,查询一次数据库，
	 * 根据上传者id查找aidRelease,
	 * @param employeeId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AidRelease> getUploadAllAidReleases(String uploadEmployeeId,int start, int end);
	
	
}
