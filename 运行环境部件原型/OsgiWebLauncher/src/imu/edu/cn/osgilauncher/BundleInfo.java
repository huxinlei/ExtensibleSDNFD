package imu.edu.cn.osgilauncher;

public class BundleInfo {

	private long id;
	private int state;
	private String symbolicName;
	private int versionMajor;
	private int versionMinor;
	private int versionMicro;
	private String versionQualifier;
	private String location;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getSymbolicName() {
		return symbolicName;
	}
	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}
	public int getVersionMajor() {
		return versionMajor;
	}
	public void setVersionMajor(int versionMajor) {
		this.versionMajor = versionMajor;
	}
	public int getVersionMinor() {
		return versionMinor;
	}
	public void setVersionMinor(int versionMinor) {
		this.versionMinor = versionMinor;
	}
	public int getVersionMicro() {
		return versionMicro;
	}
	public void setVersionMicro(int versionMicro) {
		this.versionMicro = versionMicro;
	}
	public String getVersionQualifier() {
		return versionQualifier;
	}
	public void setVersionQualifier(String versionQualifier) {
		this.versionQualifier = versionQualifier;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
