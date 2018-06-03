package imu.edu.cn.packetanalysis.tools;

import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ProtocolTreeTraverser {
	/**
	 * 展示扩展协议解析树模型
	 * @param proRoot
	 */
	public List<Integer> protocolTreeTraverser(ProtocolRoot proRoot,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(proRoot != null){
			protocolRootShow(proRoot);//打印协议根节点
			for(RuleField rule : proRoot.getRuleChilds()){//打印内容节点
				list = ruleFieldShow(rule, sequence);
				if(list.size() > 0){
					break;
				}
			}
			if(list.size() == 0){
				for(LogicField logic : proRoot.getLogicChilds()){//打印判定节点
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
	 * 展示判定节点详细信息
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
	 * 展示判定节点及子节点详细信息
	 */
	public List<Integer> logicFieldAndChildsShow(LogicField logic,String sequence){
		List<Integer> list = new ArrayList<Integer>();
		if(logic != null){
			logicFieldShow(logic, sequence);//打印判定节点详细信息
			for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//打印协议子节点信息
			    System.out.println("VALUE = " + entry.getKey());  
			    list = protocolTreeTraverser(entry.getValue(), sequence);
				if(list.size() > 0){
					break;
				}
			} 
			if(list.size() == 0){
				for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//打印内容子节点信息
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
