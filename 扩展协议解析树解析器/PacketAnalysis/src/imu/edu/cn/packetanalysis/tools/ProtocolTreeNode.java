package imu.edu.cn.packetanalysis.tools;

import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.FieldInfo;

import java.util.Map;

public class ProtocolTreeNode {
	/**
	 * ��ȡ��չЭ�������ģ��
	 * @param proRoot
	 */
	public FieldInfo protocolTreeShow(ProtocolRoot proRoot, String sequence, FieldInfo fieldInfo){
		fieldInfo.setType(0);
		if(proRoot != null){
			fieldInfo = protocolRootShow(proRoot, sequence, fieldInfo);//��ӡЭ����ڵ�
			
			if(fieldInfo.getType() != 0){
				return fieldInfo;
			}
			for(RuleField rule : proRoot.getRuleChilds()){//��ӡ���ݽڵ�
				fieldInfo = ruleFieldShow(rule, sequence, fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}
			
			for(LogicField logic : proRoot.getLogicChilds()){//��ӡ�ж��ڵ�
				fieldInfo = logicFieldAndChildsShow(logic, sequence, fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}
		}
		return fieldInfo;
	}
	/**
	 * ��ȡЭ����ڵ���ϸ��Ϣ
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
	 * ��ȡ���ݽڵ���ϸ��Ϣ
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
	 * ��ȡ�ж��ڵ���ϸ��Ϣ
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
	 * ��ȡ�ж��ڵ㼰�ӽڵ���ϸ��Ϣ
	 */
	public FieldInfo logicFieldAndChildsShow(LogicField logic, String sequence, FieldInfo fieldInfo){
		if(logic != null){
			fieldInfo = logicFieldShow(logic,sequence,fieldInfo);//��ӡ�ж��ڵ���ϸ��Ϣ
			if(fieldInfo.getType() != 0){
				return fieldInfo;
			}
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//��ӡЭ���ӽڵ���Ϣ
			    System.out.println("VALUE = " + entry.getKey());  
			    fieldInfo = protocolTreeShow(entry.getValue(),sequence,fieldInfo);
				if(fieldInfo.getType() != 0){
					break;
				}
			}  
			for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//��ӡ�����ӽڵ���Ϣ
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
