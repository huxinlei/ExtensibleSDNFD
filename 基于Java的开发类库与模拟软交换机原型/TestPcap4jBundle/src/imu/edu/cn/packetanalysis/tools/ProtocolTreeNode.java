package imu.edu.cn.packetanalysis.tools;

import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.FieldInfo;

import java.util.Map;

public class ProtocolTreeNode {
	/**
	 * 获取扩展协议解析树模型
	 * @param proRoot
	 */
	public FieldInfo protocolTreeShow(ProtocolRoot proRoot, String sequence, FieldInfo fieldInfo){
		fieldInfo.setType(0);
		if(proRoot != null){
			fieldInfo = protocolRootShow(proRoot, sequence, fieldInfo);//打印协议根节点
			
			if(fieldInfo.getType() != 0){
				return fieldInfo;
			}
			for(RuleField rule : proRoot.getRuleChilds()){//打印内容节点
				fieldInfo = ruleFieldShow(rule, sequence, fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}
			
			for(LogicField logic : proRoot.getLogicChilds()){//打印判定节点
				fieldInfo = logicFieldAndChildsShow(logic, sequence, fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}
		}
		return fieldInfo;
	}
	/**
	 * 获取协议根节点详细信息
	 * @param proRoot
	 */
	public FieldInfo protocolRootShow(ProtocolRoot  proRoot, String sequence, FieldInfo fieldInfo){
		if(proRoot != null){
			System.out.println("(" + proRoot.getSequence() + "," + proRoot.getName() + "," + proRoot.getLength() + "," + proRoot.getPrelength() + ")");
			if(proRoot.getSequence().equals(sequence)){
				fieldInfo.setType(1);
				fieldInfo.setRoot(proRoot);
			}
		}
		return fieldInfo;
	}
	/**
	 * 获取内容节点详细信息
	 * @param rule
	 */
	public FieldInfo ruleFieldShow(RuleField rule, String sequence, FieldInfo fieldInfo){
		if(rule != null){
			System.out.println("(" + rule.getSequence() + "," + rule.getName() + "," + rule.getLength() + "," + rule.getExpression() + "," + rule.getPrelength() + ")");
			if(rule.getSequence().equals(sequence)){
				fieldInfo.setType(3);
				fieldInfo.setRule(rule);
			}
		}
		return fieldInfo;
	}
	/**
	 * 获取判定节点详细信息
	 * @param logic
	 */
	public FieldInfo logicFieldShow(LogicField logic, String sequence, FieldInfo fieldInfo){
		if(logic != null){
			System.out.println("(" + logic.getSequence() + "," + logic.getName() + "," + logic.getLength() + "," + "," + logic.getPrelength() + ")");
			if(logic.getSequence().equals(sequence)){
				fieldInfo.setType(2);
				fieldInfo.setLogic(logic);
			}
		}
		return fieldInfo;
	}
	/**
	 * 获取判定节点及子节点详细信息
	 */
	public FieldInfo logicFieldAndChildsShow(LogicField logic, String sequence, FieldInfo fieldInfo){
		if(logic != null){
			fieldInfo = logicFieldShow(logic,sequence,fieldInfo);//打印判定节点详细信息
			if(fieldInfo.getType() != 0){
				return fieldInfo;
			}
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//打印协议子节点信息
			    System.out.println("VALUE = " + entry.getKey());  
			    fieldInfo = protocolTreeShow(entry.getValue(),sequence,fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}  
			for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//打印内容子节点信息
			    System.out.println("VALUE = " + entry.getKey());  
			    fieldInfo = ruleFieldShow(entry.getValue(),sequence,fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			} 
		}
		return fieldInfo;
	}
}
