package imu.edu.cn.packetanalysis.entry;

public class RuleField {
	private String sequence;//�ֶ����
	private String name;//�ֶ�����
	private int length;//�ֶ���ռ����
	private int prelength;//���ֶ�֮ǰЭ��ͷ���ֶ���ռ�ܳ���
	private String expression;//���ʽ
	
	
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
	public int getPrelength() {
		return prelength;
	}
	public void setPrelength(int prelength) {
		this.prelength = prelength;
	}
	public String getExpression(){
		return this.expression;
	}
	public void setExpression(String expression){
		this.expression = expression;
	}
}
