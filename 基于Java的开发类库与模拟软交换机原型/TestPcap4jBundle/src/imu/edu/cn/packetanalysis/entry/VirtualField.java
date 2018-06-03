package imu.edu.cn.packetanalysis.entry;

public class VirtualField {
	/***
	 * @author huxinlei
	 * @function 常量信息类
	 * @date 2015-3-8
	 * 暂时未使用
	 */
	private String sequence;//字段序号
	private String name;//字段名称
	private int length;//虚字段长度
	private int direction;//字段开始截取方向,1为从头开始截取，2为从未部开始截取
	
	
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
