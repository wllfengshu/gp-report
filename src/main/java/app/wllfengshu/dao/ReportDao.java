package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.ReportAgent;
import app.wllfengshu.entity.ReportManage;
import app.wllfengshu.entity.ReportTM;

@Repository
public interface ReportDao {
	public List<ReportAgent> getReportsAgent(
			@Param("user_id")String user_id, 
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time, 
			@Param("pageStart")int pageStart, 
			@Param("pageEnd")int pageEnd);
	
	public int getReportsAgentCount(
			@Param("user_id")String user_id, 
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time);
	
	public List<ReportTM> getReportsTM(
			@Param("tenant_id")String tenant_id, 
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time, 
			@Param("pageStart")int pageStart, 
			@Param("pageEnd")int pageEnd);
	
	public int getReportsTMCount(
			@Param("tenant_id")String tenant_id, 
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time);
	
	public List<ReportManage> getReportsManage(
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time, 
			@Param("pageStart")int pageStart, 
			@Param("pageEnd")int pageEnd);
	
	public int getReportsManageCount(
			@Param("call_type")String call_type, 
			@Param("start_time")String start_time, 
			@Param("end_time")String end_time);
}
