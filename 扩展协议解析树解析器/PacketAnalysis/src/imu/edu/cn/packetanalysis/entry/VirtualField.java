package imu.edu.cn.packetanalysis.entry;

public class VirtualField {
	/***
	 * @author huxinlei
	 * @function ������Ϣ��
	 * @date 2015-3-8
	 * ��ʱδʹ��
	 */
	private String sequence;//�ֶ����
	private String name;//�ֶ�����
	private int length;//���ֶγ���
	private int direction;//�ֶο�ʼ��ȡ����,1Ϊ��ͷ��ʼ��ȡ��2Ϊ��δ����ʼ��ȡ
	
	
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
