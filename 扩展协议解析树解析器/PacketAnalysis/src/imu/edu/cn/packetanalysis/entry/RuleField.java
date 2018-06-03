package imu.edu.cn.packetanalysis.entry;

public class RuleField {
	private String sequence;//字段序号
	private String name;//字段名称
	private int length;//字段所占长度
	private int prelength;//本字段之前协议头和字段所占总长度
	private String expression;//表达式
	
	
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
