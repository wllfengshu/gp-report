package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface ReportService {
	
	public String getReports(String sessionId,String user_id,String tenant_id,String call_type,String token, String start_time, String end_time, int pageNo, int pageSize) throws NotAcceptableException;

}
