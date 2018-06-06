/*
 * @(#)BusinessException.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.exception;
/**
 * sap 下发异常类
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7419400618793645414L;
	private String sapid = "";// 异常对应的返回码
	private String msg;//异常对应的描述信息

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
		this.msg = message;
	}

	public BusinessException(String sapid, String message) {
		super(message);
		this.sapid = sapid;
		this.msg = message;
	}

	public String getSapid() {
		return sapid;
	}

	public void setSapid(String sapid) {
		this.sapid = sapid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}