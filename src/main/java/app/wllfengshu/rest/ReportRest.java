package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.ReportService;
import app.wllfengshu.util.LogUtils;

@Controller
@Path("/report")
public class ReportRest {
	
	@Autowired
	private ReportService reportService ;
    
	/**
	 * @title 查询所有报表
	 * @param sessionId
	 * @param request
	 * @param response
	 * @return
	 */
    @GET
    public Response getReports(
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@HeaderParam(value="tenant_id") String tenant_id,
    		@HeaderParam(value="call_type") String call_type,
    		@QueryParam("token") String token,
    		@QueryParam("begin_time") String begin_time,@QueryParam("end_time") String end_time,
    		@QueryParam("pageNo") int pageNo,@QueryParam("pageSize") int pageSize,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=reportService.getReports(sessionId,user_id,tenant_id,call_type,token,begin_time,end_time,pageNo,pageSize);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# ReportRest-getReports,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }

    
}
