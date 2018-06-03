package imu.edu.cn.packetanalysis.tools;

import java.util.Map;
import java.util.HashMap;

import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.LogicField;
public class ProtocolTreeShow {
	/**
	 * 展示扩展协议解析树模型
	 * @param proRoot
	 */
	public void protocolTreeShow(ProtocolRoot proRoot){
		if(proRoot != null){
			protocolRootShow(proRoot);//打印协议根节点
			for(RuleField rule : proRoot.getRuleChilds()){//打印内容节点
				ruleFieldShow(rule);
			}
			for(LogicField logic : proRoot.getLogicChilds()){//打印判定节点
				logicFieldAndChildsShow(logic);
			}
		}
	}
	/**
	 * 展示协议根节点详细信息
	 * @param proRoot
	 */
	public void protocolRootShow(ProtocolRoot  proRoot){
		if(proRoot != null){
			System.out.println("(" + proRoot.getSequence() + "," + proRoot.getName() + "," + proRoot.getLength() + "," + proRoot.getPrelength() + ")");
		}
	}
	/**
	 * 展示内容节点详细信息
	 * @param rule
	 */
	public void ruleFieldShow(RuleField rule){
		if(rule != null){
			System.out.println("(" + rule.getSequence() + "," + rule.getName() + "," + rule.getLength() + "," + rule.getExpression() + "," + rule.getPrelength() + ")");
		}
	}
	/**
	 * 展示判定节点详细信息
	 * @param logic
	 */
	public void logicFieldShow(LogicField logic){
		if(logic != null){
			System.out.println("(" + logic.getSequence() + "," + logic.getName() + "," + logic.getLength() + "," + "," + logic.getPrelength() + ")");
		}
	}
	/**
	 * 展示判定节点及子节点详细信息
	 */
	public void logicFieldAndChildsShow(LogicField logic){
		if(logic != null){
			logicFieldShow(logic);//打印判定节点详细信息
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//打印协议子节点信息
			    System.out.println("VALUE = " + entry.getKey());  
			    protocolTreeShow(entry.getValue());
			}  
			for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//打印内容子节点信息
			    System.out.println("VALUE = " + entry.getKey());  
			    ruleFieldShow(entry.getValue());
			} 
		}
	}
}
