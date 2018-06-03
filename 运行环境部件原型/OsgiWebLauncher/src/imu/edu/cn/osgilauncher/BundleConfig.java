package imu.edu.cn.osgilauncher;

import java.io.Serializable;

public class BundleConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3612750954539516826L;
	
	private String locationUrl;
	private int startLevel;
	private boolean isAutoStart;
	
	public String getLocationUrl() {
		return locationUrl;
	}
	public void setLocationUrl(String locationUrl) {
		this.locationUrl = locationUrl;
	}
	public int getStartLevel() {
		return startLevel;
	}
	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}
	public boolean isAutoStart() {
		return isAutoStart;
	}
	public void setAutoStart(boolean isAutoStart) {
		this.isAutoStart = isAutoStart;
	}
	
	
}
