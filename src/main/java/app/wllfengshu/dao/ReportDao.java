package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.Report;

@Repository
public interface ReportDao {
	public List<Report> getReports(
			@Param("user_id")String user_id, 
			@Param("tenant_id")String tenant_id, 
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time, 
			@Param("pageStart")int pageStart, 
			@Param("pageEnd")int pageEnd);
	
}
