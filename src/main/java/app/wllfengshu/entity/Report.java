package app.wllfengshu.entity;

public class Report {
	private String id;
	private String login_name;
	private String username;
	private String start_time;
	private String end_time;
	private String call_time;//呼叫总次数
	private String call_length;//呼叫总时长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCall_time() {
		return call_time;
	}
	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}
	public String getCall_length() {
		return call_length;
	}
	public void setCall_length(String call_length) {
		this.call_length = call_length;
	}
	
	
}
