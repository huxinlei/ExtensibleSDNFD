package imu.edu.cn.packetanalysis.entry;
import java.util.List;

public class ProtocolRoot {
	private String sequence;//Э�����
	private String name;//Э������
	private int length;//Э��ͷ����
	private int prelength;//��Э��֮ǰ����Э��ͷ�ܳ���
	private List<LogicField> logicChilds;//�ж��ֶ����б�
	private List<RuleField> ruleChilds;//�����ֶ����б�
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
