package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.tools.ProtocolTreeTraverser;

import java.util.Stack;
import java.util.List;
public class LengthParser {
	//������ջ
	private Stack<String> opers;
	//���ʽջ
	private Stack<String> exps;
	//Э�������
	private ProtocolRoot treeRoot;
	//�����ܳ���
	private int length;
	
	public Stack<String> getOpers() {
		return opers;
	}
	public void setOpers(Stack<String> opers) {
		this.opers = opers;
	}
	public Stack<String> getExps() {
		return exps;
	}
	public void setExps(Stack<String> exps) {
		this.exps = exps;
	}
	public ProtocolRoot getTreeRoot() {
		return treeRoot;
	}
	public void setTreeRoot(ProtocolRoot treeRoot) {
		this.treeRoot = treeRoot;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	//���Ƚ�������
	public int LengthParser(){
		int result = 0;
		while(!opers.empty()){
			String oper = opers.pop();
			System.out.println("������Ϊ" + oper);
			String exp1 = "";
			String exp2 = "";
			if(!exps.empty()){
				exp1 = exps.pop();
			}
			if(!exps.empty()){
				exp2 = exps.pop();
			}
			System.out.println("���ʽ1Ϊ��" + exp1);
			System.out.println("���ʽ2Ϊ��" + exp2);
			int num1 = getLengthFromExp(exp2);
			int num2 = getLengthFromExp(exp1);
			//�����ȡ
			if(num1 < 0 || num2 < 0){
				break;
			}
			if(oper.equals("+")){
				result = num1 + num2;
			}else if(oper.equals("-")){
				result = num1 - num2;
			}
		}
		if(!exps.empty()){
			result = 0;
		}
		return result;
	}
	//��ñ��ʽ����
	public int getLengthFromExp(String exp){
		int result = 0;
		int lpos = exp.indexOf("length");
		int plpos = exp.indexOf("prelength");
		ProtocolTreeTraverser pTraver = new ProtocolTreeTraverser();
		if(lpos == 0 && plpos < 0){
			int bracketl = exp.indexOf("(");
			int bracketr = exp.indexOf(")");
			String sequence = exp.substring(bracketl+1, bracketr);
			if(!sequence.equals("")){
				List<Integer> list = pTraver.protocolTreeTraverser(treeRoot, sequence);
				System.out.println("���кţ�" + sequence);
				if(list.size() == 2){
					result = list.get(0);
				}
			}else{
				result = this.length * 8;
			}
		}else if(plpos == 0 && lpos == 3){
			int bracketl = exp.indexOf("(");
			int bracketr = exp.indexOf(")");
			String sequence = exp.substring(bracketl+1, bracketr);
			if(!sequence.equals("")){
				List<Integer> list = pTraver.protocolTreeTraverser(treeRoot, sequence);
				System.out.println("���кţ�" + sequence);
				if(list.size() == 2){
					result = list.get(1);
				}
			}
		}else{
			result = -1;
		}
		return result;
	}
	//�������ĳ��Ƚ�������
	public int LengthParser(Stack<String> opers,Stack<String> exps,ProtocolRoot treeRoot,int length){
		this.opers = opers;
		this.exps = exps;
		this.treeRoot = treeRoot;
		this.length = length;
		int result = LengthParser();
		return result;
	}
}
