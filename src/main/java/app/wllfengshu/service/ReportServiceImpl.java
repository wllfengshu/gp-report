package app.wllfengshu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.ReportDao;
import app.wllfengshu.entity.Record;
import app.wllfengshu.entity.Report;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.AuthUtil;

@Service
public class ReportServiceImpl implements ReportService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private ReportDao reportDao;
	
	@Override
	public String getReports(String sessionId,String user_id,String tenant_id,String call_type,String token, String start_time,String end_time, int pageNo, int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		List<Report> records =null;
		if (token.equals("crm")) {//使用crm系统的用户，只能查询属于自己数据
			records = reportDao.getReports(user_id,"",call_type,start_time,end_time,(pageNo-1)*pageSize,pageSize);
		}else if(token.equals("tm")){//使用tm系统的用户，可以查询当前租户下所有数据
			records = reportDao.getReports("",tenant_id,call_type,start_time,end_time,(pageNo-1)*pageSize,pageSize);
		}else if (token.equals("manage")) {//使用manage系统的用户，查询数据库中所有数据
			records = reportDao.getReports("","",call_type,start_time,end_time,(pageNo-1)*pageSize,pageSize);
		}else{//其他token直接返回失败
			throw new NotAcceptableException("凭证异常");
		}
		responseMap.put("data", records);
		responseMap.put("count", records.size());
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
}
