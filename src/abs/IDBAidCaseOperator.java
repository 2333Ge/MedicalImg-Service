package abs;

import java.util.List;

import bean.AidCase;
import bean.AidRelease;
/**
 * 数据库表AidCase 操作接口
 * @author 2413324637
 *
 */
public interface IDBAidCaseOperator extends DBCloseAble{
	/**
	 * 获取随机病例，查询4次数据库
	 * @param start 开始序号
	 * @param end 结束序号
	 * @return
	 */
	List<AidCase> getRandomAidCases(int start,int end);
	/**
	 * 获取对应工作人员处理的病单
	 * @param handleEmployeeId
	 * @param start 开始序号
	 * @param end 结束序号
	 * @return
	 */
	List<AidCase> getHandledAidCases(String handleEmployeeId,int start,int end);
	
	/**
	 * 根据AidRelease查询对应AidCase，其中aidRelease中的aidRecord已经查询完毕
	 * @param aidRelease AidRelease
	 * @return
	 */
	AidCase getHandledAidCase(AidRelease aidRelease);
}
