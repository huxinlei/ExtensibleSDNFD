package imu.edu.cn.packetanalysis.entry;
import java.util.Map;
public class LogicField {
	private String sequence;//字段序号
	private String name;//字段名称
	private int length;//字段所占长度
	private int prelength;//本字段之前协议头和字段所占总长度
	private Map<String, RuleField> ruleChilds;//内容字段键值子列表
	private Map<String, ProtocolRoot> proChilds;//协议字段键值子列表
	
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
	
	public void setRuleChilds(Map<String, RuleField> ruleChilds){
		this.ruleChilds = ruleChilds;
	}
	public Map<String, RuleField> getRuleChilds(){
		return this.ruleChilds;
	}
	
	public void setProChilds(Map<String, ProtocolRoot> proChilds){
		this.proChilds = proChilds;
	}
	public Map<String, ProtocolRoot> getProChilds(){
		return this.proChilds;
	}
}
