package imu.edu.cn.packetanalysis.entry;
import java.util.Map;
public class LogicField {
	private String sequence;//�ֶ����
	private String name;//�ֶ�����
	private int length;//�ֶ���ռ����
	private int prelength;//���ֶ�֮ǰЭ��ͷ���ֶ���ռ�ܳ���
	private Map<String, RuleField> ruleChilds;//�����ֶμ�ֵ���б�
	private Map<String, ProtocolRoot> proChilds;//Э���ֶμ�ֵ���б�
	
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
