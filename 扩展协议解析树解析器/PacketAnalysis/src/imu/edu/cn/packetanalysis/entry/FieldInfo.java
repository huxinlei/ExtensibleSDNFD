package imu.edu.cn.packetanalysis.entry;

public class FieldInfo {
	//���ڵ���Ϣ
	private ProtocolRoot root;
	//�ж��ڵ���Ϣ
	private LogicField logic;
	//����ڵ���Ϣ
	private RuleField rule;
	//��ǰ����������Ϣ 0Ϊû���ҵ�ƥ����Ϣ,1Ϊ���ڵ���Ϣ,2Ϊ�ж��ڵ���Ϣ,3Ϊ����ڵ���Ϣ
	private int type;
	
	public ProtocolRoot getRoot() {
		return root;
	}
	public void setRoot(ProtocolRoot root) {
		this.root = root;
	}
	public LogicField getLogic() {
		return logic;
	}
	public void setLogic(LogicField logic) {
		this.logic = logic;
	}
	public RuleField getRule() {
		return rule;
	}
	public void setRule(RuleField rule) {
		this.rule = rule;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
