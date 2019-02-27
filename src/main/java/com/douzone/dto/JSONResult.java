package com.douzone.dto;

public class JSONResult 
{
	private String result; // "success", "fail"
	private String message; // result가 "success"면 null, 아니면 익셉션 메세지
	
	private Object data; // result가 fail이면 null success면 객체를 전달

	
	public JSONResult (String result, String message, Object data)
	{
		this.result = result;
		this.message = message;
		this.data = data;
	}
	
	public static JSONResult success(Object data)
	{
		return new JSONResult("success", null, data);
	}
	
	public static JSONResult fail(String message)
	{
		return new JSONResult("fail", message, null);
	}
	
	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	
	}

	public Object getData() {
		return data;
	}	
}
