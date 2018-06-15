package com.notification.ex.dto;

import java.util.List;



public class ErrorDTO {
	private String msg;
	private   String status;
	//private  List<UserDTO> result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public List<UserDTO> getResult() {
		return result;
	}
	public void setResult(List<UserDTO> result) {
		this.result = result;
	}*/
}
