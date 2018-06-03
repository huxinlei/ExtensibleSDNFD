package imu.edu.cn.basecom;
import org.pcap4j.util.MacAddress;

import java.util.Map;
import java.util.HashMap;

public class BasePacket {
	private MacAddress dstAddress;//目的mac地址
	private MacAddress srcAddress;//源mac地址
	private byte[] type;//数据包类型
	private Map<String,byte[]> datas;//数据包解析后内容数据
	private byte[] orignalDatas;///数据包解析前原始数据
	private ActionSet alist;//处理动作集
	
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
