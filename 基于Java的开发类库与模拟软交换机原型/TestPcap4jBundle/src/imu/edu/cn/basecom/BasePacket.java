package imu.edu.cn.basecom;
import org.pcap4j.util.MacAddress;

import java.util.Map;
import java.util.HashMap;

public class BasePacket {
	private MacAddress dstAddress;//Ŀ��mac��ַ
	private MacAddress srcAddress;//Դmac��ַ
	private byte[] type;//���ݰ�����
	private Map<String,byte[]> datas;//���ݰ���������������
	private byte[] orignalDatas;///���ݰ�����ǰԭʼ����
	private ActionSet alist;//��������
	
	public MacAddress getDstAddress() {
		return dstAddress;
	}
	public void setDstAddress(MacAddress dstAddress) {
		this.dstAddress = dstAddress;
	}
	public MacAddress getSrcAddress() {
		return srcAddress;
	}
	public void setSrcAddress(MacAddress srcAddress) {
		this.srcAddress = srcAddress;
	}
	public byte[] getType() {
		return type;
	}
	public void setType(byte[] type) {
		this.type = type;
	}
	public Map<String, byte[]> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, byte[]> datas) {
		this.datas = datas;
	}
	public byte[] getOrignalDatas() {
		return orignalDatas;
	}
	public void setOrignalDatas(byte[] orignalDatas) {
		this.orignalDatas = orignalDatas;
	}
	public ActionSet getAlist() {
		return alist;
	}
	public void setAlist(ActionSet alist) {
		this.alist = alist;
	}
	
}
