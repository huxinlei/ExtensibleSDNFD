package imu.edu.cn.packetanalysis.tools;

import java.util.Map;
import java.util.HashMap;

import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.LogicField;
public class ProtocolTreeShow {
	/**
	 * չʾ��չЭ�������ģ��
	 * @param proRoot
	 */
	public void protocolTreeShow(ProtocolRoot proRoot){
		if(proRoot != null){
			protocolRootShow(proRoot);//��ӡЭ����ڵ�
			for(RuleField rule : proRoot.getRuleChilds()){//��ӡ���ݽڵ�
				ruleFieldShow(rule);
			}
			for(LogicField logic : proRoot.getLogicChilds()){//��ӡ�ж��ڵ�
				logicFieldAndChildsShow(logic);
			}
		}
	}
	/**
	 * չʾЭ����ڵ���ϸ��Ϣ
	 * @param proRoot
	 */
	public void protocolRootShow(ProtocolRoot  proRoot){
		if(proRoot != null){
			System.out.println("(" + proRoot.getSequence() + "," + proRoot.getName() + "," + proRoot.getLength() + "," + proRoot.getPrelength() + ")");
		}
	}
	/**
	 * չʾ���ݽڵ���ϸ��Ϣ
	 * @param rule
	 */
	public void ruleFieldShow(RuleField rule){
		if(rule != null){
			System.out.println("(" + rule.getSequence() + "," + rule.getName() + "," + rule.getLength() + "," + rule.getExpression() + "," + rule.getPrelength() + ")");
		}
	}
	/**
	 * չʾ�ж��ڵ���ϸ��Ϣ
	 * @param logic
	 */
	public void logicFieldShow(LogicField logic){
		if(logic != null){
			System.out.println("(" + logic.getSequence() + "," + logic.getName() + "," + logic.getLength() + "," + "," + logic.getPrelength() + ")");
		}
	}
	/**
	 * չʾ�ж��ڵ㼰�ӽڵ���ϸ��Ϣ
	 */
	public void logicFieldAndChildsShow(LogicField logic){
		if(logic != null){
			logicFieldShow(logic);//��ӡ�ж��ڵ���ϸ��Ϣ
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//��ӡЭ���ӽڵ���Ϣ
			    System.out.println("VALUE = " + entry.getKey());  
			    protocolTreeShow(entry.getValue());
			}  
			for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//��ӡ�����ӽڵ���Ϣ
			    System.out.println("VALUE = " + entry.getKey());  
			    ruleFieldShow(entry.getValue());
			} 
		}
	}
}
