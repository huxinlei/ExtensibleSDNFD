package imu.edu.cn.basecom;

import org.pcap4j.util.MacAddress;

public class Action {
	private int type;//�������ͣ�1Ϊת����0Ϊ��������ǰֻ֧�����ֶ�������
	private int port;//���Ͷ˿ڣ���������Ϊת��ʱ��Ч
	private MacAddress portAddress;//�˿���̫����ַ
	private MacAddress dstAddress;//��һ����̫����ַ
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
