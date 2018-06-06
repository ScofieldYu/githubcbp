/*
 * @(#)UploadFile.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.util;

import java.io.File;


/**
 * 文件上传UploadFile
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class UploadFile {
	private File file;
	private String fileName;
	
	private String suffix;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

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
	
	
}
