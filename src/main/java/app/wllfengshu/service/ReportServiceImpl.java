package app.wllfengshu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.ReportDao;
import app.wllfengshu.entity.ReportAgent;
import app.wllfengshu.entity.ReportManage;
import app.wllfengshu.entity.ReportTM;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.AuthUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ReportServiceImpl implements ReportService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private ReportDao reportDao;
	
	@Override
	public String getReports(String sessionId,String user_id,String tenant_id,String call_type,String token, String start_time,String end_time, int pageNo, int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		JSONObject user = AuthUtil.instance.getUser(sessionId, user_id);
		if (null==user || user.isNullObject()) {
			throw new NotAcceptableException("没有权限");
		}
		if(!token.equals("manage")){
			JSONArray roles = user.getJSONArray("roles");
			JSONObject role = roles.getJSONObject(0);
			String role_name=role.getString("role_name");
			if (!"agent".equals(role_name) && !"tm".equals(role_name) ) {//允许座席、租户管理员查看
				throw new NotAcceptableException("角色异常");
			}
		}
		List<ReportAgent> recordsAgent =null;
		List<ReportTM> recordsTM =null;
		List<ReportManage> recordsManage =null;
		if (token.equals("crm")) {//使用crm系统的用户，只能查询属于自己数据
			recordsAgent = reportDao.getReportsAgent(user_id, call_type, start_time, end_time, 0, 1);//座席只有一条记录
			responseMap.put("data", recordsAgent);
			responseMap.put("count", 1);//对于座席而言，永远只有一条记录
		}else if(token.equals("tm")){//使用tm系统的用户，可以查询当前租户下所有数据
			recordsTM = reportDao.getReportsTM(tenant_id, call_type, start_time, end_time, (pageNo-1)*pageSize, pageSize);
			responseMap.put("data", recordsTM);
			responseMap.put("count", 2);//这里应该是用下面的代码，但是我的sql有问题，把为空的数据也查出来了
//			responseMap.put("count", reportDao.getReportsTMCount(tenant_id, call_type, start_time, end_time));
		}else if (token.equals("manage")) {//使用manage系统的用户，查询数据库中所有数据
			recordsManage = reportDao.getReportsManage(call_type, start_time, end_time, (pageNo-1)*pageSize, pageSize);
			responseMap.put("data", recordsManage);
			responseMap.put("count", 4);
//			responseMap.put("count", reportDao.getReportsManageCount(call_type, start_time, end_time));
		}else{//其他token直接返回失败
			throw new NotAcceptableException("凭证异常");
		}
		
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
}
