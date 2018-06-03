package imu.edu.cn.packetanalysis.entry;
import java.util.List;

public class ProtocolRoot {
	private String sequence;//协议序号
	private String name;//协议名称
	private int length;//协议头长度
	private int prelength;//本协议之前其他协议头总长度
	private List<LogicField> logicChilds;//判定字段子列表
	private List<RuleField> ruleChilds;//内容字段子列表
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
	
	public void setLogicChilds(List<LogicField> logicChilds){
		this.logicChilds = logicChilds;
	}
	public List<LogicField> getLogicChilds(){
		return this.logicChilds;
	}
	
	public void setRuleChilds(List<RuleField> ruleChilds){
		this.ruleChilds = ruleChilds;
	}
	public List<RuleField> getRuleChilds(){
		return ruleChilds;
	}
}
