package com.ccit.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.ccit.manager.ObjectManager;
import com.ccit.util.CommDAO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings( { "unchecked", "serial" })
public class ReadExcelAction extends ActionSupport {
	private static final long serialVersionUID = 1l;
	private static final int BUFFER_SIZE = 1;
	private File myFile;
	private String contentType;
	private String fileName;
	private String imageFileName;
	private ObjectManager objectManager;
	private static final String tables = "Part";

	public String readExcel() throws Exception {
		if (null == fileName) {
			return "export";
		}
		int pos = fileName.lastIndexOf(".");
		fileName.substring(pos);
		imageFileName = fileName.substring(0, pos) + new Date().getTime()
				+ fileName.substring(pos);
		String path = ServletActionContext.getServletContext().getRealPath(
				"/upload")
				+ "/" + imageFileName;

		try {
			File file = new File(path);

			try {
				copy(myFile, file);
			} catch (Exception e) {
				return "export";
			}
			new CommDAO().insert(file, "t_" +tables);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ok";
	}

	private void copy(File src, File dst) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream(src),
				BUFFER_SIZE);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(dst),
				BUFFER_SIZE);
		byte[] buffer = new byte[BUFFER_SIZE];
		while (in.read(buffer) > 0) {
			out.write(buffer);
		}
		in.close();
		out.close();
	}

	public void setMyFileContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setMyFileFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}

	 
 
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
