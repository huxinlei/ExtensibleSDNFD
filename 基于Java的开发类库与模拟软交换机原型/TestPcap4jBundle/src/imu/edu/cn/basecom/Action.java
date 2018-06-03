package imu.edu.cn.basecom;

import org.pcap4j.util.MacAddress;

public class Action {
	private int type;//动作类型，1为转发，0为丢弃，当前只支持两种动作类型
	private int port;//发送端口，动作类型为转发时有效
	private MacAddress portAddress;//端口以太网地址
	private MacAddress dstAddress;//下一跳以太网地址
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public MacAddress getPortAddress() {
		return portAddress;
	}
	public void setPortAddress(MacAddress portAddress) {
		this.portAddress = portAddress;
	}
	public MacAddress getDstAddress() {
		return dstAddress;
	}
	public void setDstAddress(MacAddress dstAddress) {
		this.dstAddress = dstAddress;
	}
	
}
