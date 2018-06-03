package imu.edu.cn.packetanalysis.tools;

import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ProtocolTreeTraverser {
	/**
	 * չʾ��չЭ�������ģ��
	 * @param proRoot
	 */
	public List<Integer> protocolTreeTraverser(ProtocolRoot proRoot,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(proRoot != null){
			protocolRootShow(proRoot);//��ӡЭ����ڵ�
			for(RuleField rule : proRoot.getRuleChilds()){//��ӡ���ݽڵ�
				list = ruleFieldShow(rule, sequence);
				if(list.size() > 0){
					break;
				}
			}
			if(list.size() == 0){
				for(LogicField logic : proRoot.getLogicChilds()){//��ӡ�ж��ڵ�
					list = logicFieldAndChildsShow(logic, sequence);
					if(list.size() > 0){
						break;
					}
				}
			}
		}
		return list;
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
	public List<Integer> ruleFieldShow(RuleField rule,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(rule != null){
			System.out.println("(" + rule.getSequence() + "," + rule.getName() + "," + rule.getLength() + "," + rule.getExpression() + "," + rule.getPrelength() + ")");
			if(sequence.equals(rule.getSequence())){
				list.add(rule.getLength());
				list.add(rule.getPrelength());
			}
		}
		return list;
	}
	/**
	 * չʾ�ж��ڵ���ϸ��Ϣ
	 * @param logic
	 */
	public List<Integer> logicFieldShow(LogicField logic,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(logic != null){
			System.out.println("(" + logic.getSequence() + "," + logic.getName() + "," + logic.getLength() + "," + "," + logic.getPrelength() + ")");
			if(sequence.equals(logic.getSequence())){
				list.add(logic.getLength());
				list.add(logic.getPrelength());	
			}
		}
		return list;
	}
	/**
	 * չʾ�ж��ڵ㼰�ӽڵ���ϸ��Ϣ
	 */
	public List<Integer> logicFieldAndChildsShow(LogicField logic,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(logic != null){
			logicFieldShow(logic, sequence);//��ӡ�ж��ڵ���ϸ��Ϣ
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//��ӡЭ���ӽڵ���Ϣ
			    System.out.println("VALUE = " + entry.getKey());  
			    list = protocolTreeTraverser(entry.getValue(), sequence);
				if(list.size() > 0){
					break;
				}
			} 
			if(list.size() == 0){
				for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//��ӡ�����ӽڵ���Ϣ
				    System.out.println("VALUE = " + entry.getKey());  
				    list = ruleFieldShow(entry.getValue(), sequence);
				    if(list.size() > 0){
				    	break;
				    }
				} 
			}
		}
		return list;
	}
}
