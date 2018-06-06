/*
 * @(#)MultipartFileBean.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.util;

/**
 * 文件上传bean
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class MultipartFileBean {
	
	/** 上传的文件名称 **/
	private String fileName;
	
	/** 上传的文件后缀 **/
	private String suffix;
	
	/** 文件链接url **/
	private String docUrl;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}
	

}
