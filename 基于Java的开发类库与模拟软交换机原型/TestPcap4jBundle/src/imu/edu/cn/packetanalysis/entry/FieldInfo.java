package imu.edu.cn.packetanalysis.entry;

public class FieldInfo {
	//根节点信息
	private ProtocolRoot root;
	//判定节点信息
	private LogicField logic;
	//规则节点信息
	private RuleField rule;
	//当前内容类型信息 0为没有找到匹配信息,1为根节点信息,2为判定节点信息,3为规则节点信息
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
