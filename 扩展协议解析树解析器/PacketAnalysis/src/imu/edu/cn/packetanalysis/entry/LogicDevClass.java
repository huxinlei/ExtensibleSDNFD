package imu.edu.cn.packetanalysis.entry;

public class LogicDevClass {
	
	/**
	 * @author huxinlei
	 * @function 盛放具体数据包的实体类
	 * @date 2015-3-9
	 */
	
	private byte[] rulefield1;
	
	private int logicfield1;
	
	private byte[] rulefield2;
	
	private byte[] rulefield3;
	
	private byte[] rulefield4;
	
	private byte[] protocoldata;//暂时固定名称
	

	public byte[] getRulefield1() {
		return rulefield1;
	}
	public void setRulefield1(byte[] rulefield1) {
		this.rulefield1 = rulefield1;
	}
	
	public int getLogicfield1() {
		return logicfield1;
	}
	public void setLogicfield1(int logicfield1) {
		this.logicfield1 = logicfield1;
	}
	
	public byte[] getRulefield2() {
		return rulefield2;
	}
	public void setRulefield2(byte[] rulefield2) {
		this.rulefield2 = rulefield2;
	}
	
	public byte[] getRulefield3() {
		return rulefield3;
	}
	public void setRulefield3(byte[] rulefield3) {
		this.rulefield3 = rulefield3;
	}
	
	public byte[] getRulefield4() {
		return rulefield4;
	}
	public void setRulefield4(byte[] rulefield4) {
		this.rulefield4 = rulefield4;
	}
	
	public byte[] getProtocoldata() {
		return protocoldata;
	}
	public void setProtocoldata(byte[] protocoldata) {
		this.protocoldata = protocoldata;
	}
}
