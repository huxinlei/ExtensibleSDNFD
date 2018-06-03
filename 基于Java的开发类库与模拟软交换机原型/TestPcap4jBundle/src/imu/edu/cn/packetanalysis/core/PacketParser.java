package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.FieldInfo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import imu.edu.cn.packetanalysis.tools.ConstantV2;
import imu.edu.cn.packetanalysis.tools.TypeConvertor;


public class PacketParser {
	//���������ݰ�����
	private List<byte[]> packetData;
	//��չЭ�������ģ��
	private ProtocolRoot treeRoot;
	
	/**
	 * ��ȡ���������ݰ�
	 * @return
	 */
	public List<byte[]> getPacketData() {
		return packetData;
	}
	/**
	 * ���ô��������ݰ�
	 * @param packetData
	 */
	public void setPacketData(List<byte[]> packetData) {
		this.packetData = packetData;
	}
	/**
	 * ��ȡ��չЭ�������ģ��
	 * @return
	 */
	public ProtocolRoot getTreeRoot() {
		return treeRoot;
	}
	/**
	 * ������չЭ�������ģ��
	 * @param treeRoot
	 */
	public void setTreeRoot(ProtocolRoot treeRoot) {
		this.treeRoot = treeRoot;
	}
	/**
	 * �������ݰ�
	 * @return
	 */
	public List<Map> packetParse(){
		List<Map> listMap = new ArrayList<Map>();
		if(this.packetData.size() > 0 && this.treeRoot != null){
			//ѭ������ÿһ���ڵ�
			for(int i = 0; i < this.packetData.size(); i++){
				Map packet = onePacketParse(i);
				listMap.add(packet);
			}
		}
		return listMap;
	}
	/**
	 * �������ݰ��������к�
	 */
	public List<Map> packetParseBySequence(FieldInfo fieldInfo){
		List<Map> listMap = new ArrayList<Map>();
		if(this.packetData.size() > 0 && fieldInfo.getType() > 0){
			//ѭ������ÿһ���ڵ�
			for(int i = 0; i < this.packetData.size(); i++){
				Map packet = onePacketParseBySequence(i, fieldInfo);
				listMap.add(packet);
			}
		}
		return listMap;
	}
	/** 
	 * �����������ݰ�
	 * @param i
	 * @param fieldInfo
	 * @return
	 */
	public Map onePacketParseBySequence(int i, FieldInfo fieldInfo){
		Map packet = new HashMap();
		byte[] data = this.packetData.get(i);
		if(data != null){
			if(fieldInfo.getType() == 2){
				logicFieldParse(fieldInfo.getLogic(), packet, data);
			}else if(fieldInfo.getType() == 3){
				ruleFieldParse(fieldInfo.getRule(), packet, data);
			}
		}
		return packet;
	}
	/** 
	 * �����������ݰ�
	 * @param i
	 * @return
	 */
	public Map onePacketParse(int i){
		Map packet = new HashMap();
		byte[] data = this.packetData.get(i);
		if(data != null){
			protocolTreeParse(this.treeRoot, packet, data);
		}
		return packet;
	}
	/**
	 * ������չЭ����������н���
	 * @param proRoot
	 * @param packet
	 * @param data
	 */
	public void protocolTreeParse(ProtocolRoot proRoot, Map packet, byte[] data){
		if(proRoot != null){
			protocolRootParse(proRoot, packet, data);//��ӡЭ����ڵ�
			for(RuleField rule : proRoot.getRuleChilds()){//��ӡ���ݽڵ�
				ruleFieldParse(rule, packet, data);
			}
			for(LogicField logic : proRoot.getLogicChilds()){//��ӡ�ж��ڵ�
				logicFieldAndChildsParse(logic, packet, data);
			}
		}
	}
	/**
	 * ����Э����ڵ�
	 * @param proRoot
	 * @param packet
	 * @param data
	 */
	public void protocolRootParse(ProtocolRoot  proRoot, Map packet, byte[] data){
		if(proRoot != null){
			System.out.println("(" + proRoot.getSequence() + "," + proRoot.getName() + "," + proRoot.getLength() + "," + proRoot.getPrelength() + ")");
			packet.put(proRoot.getSequence(), proRoot.getName().getBytes());
		}
	}
	/**
	 * ����Э�����ݽڵ�
	 * @param rule
	 * @param packet
	 * @param data
	 */
	public void ruleFieldParse(RuleField rule, Map packet, byte[] data){
		if(rule != null){
			System.out.println("(" + rule.getSequence() + "," + rule.getName() + "," + rule.getLength() + "," + rule.getExpression() + "," + rule.getPrelength() + ")");
			if(rule.getLength() != -1){
				byte[] temp = contentParse(rule.getLength(), rule.getPrelength(), data);
				String ruleContent = TypeConvertor.bytesToHexString(temp);
				System.out.println(ruleContent);
				packet.put(rule.getSequence(), temp);
			} else {
				//��ȡ�ֶμ�����ʽ
				String expression = rule.getExpression();
				int length = getLengthByEx(expression,data);
				byte[] temp = contentParse(length, rule.getPrelength(),data);
				String dataContent = TypeConvertor.bytesToHexString(data);
				String ruleContent = TypeConvertor.bytesToHexString(temp);
				System.out.println(dataContent);
				System.out.println(ruleContent);
				packet.put(rule.getSequence(), temp);
			}
		}
	}
	/**
	 * ����Э���ж��ڵ�
	 * @param logic
	 * @param packet
	 * @param data
	 */
	public byte[] logicFieldParse(LogicField logic, Map packet, byte[] data){
		if(logic != null){
			System.out.println("(" + logic.getSequence() + "," + logic.getName() + "," + logic.getLength() + "," + "," + logic.getPrelength() + ")");
			byte[] temp = contentParse(logic.getLength(), logic.getPrelength(), data);
			String logicContent = TypeConvertor.bytesToHexString(temp);
			System.out.println(logicContent);
			packet.put(logic.getSequence(), temp);
			return temp;
		} else {
			return null;
		}
	}
	/**
	 * ����Э���ж��ڵ㼰���ӽڵ�
	 * @param logic
	 * @param packet
	 * @param data
	 */
	public void logicFieldAndChildsParse(LogicField logic, Map packet, byte[] data){
		if(logic != null){
			byte[] temp = logicFieldParse(logic, packet, data);//��ӡ�ж��ڵ���ϸ��Ϣ
			if(temp != null){
				if(temp.length <= 4){
					String cValue = TypeConvertor.bytesToHexString(temp);
					System.out.println("���ν����ж��ֶ�ֵ��" + cValue);
					//�ȱ���ִ�������ӽڵ㣬��ȡ�����ӽڵ�����
					for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//��ӡ�����ӽڵ���Ϣ
					    System.out.println("VALUE = " + entry.getKey());  
					    String value = entry.getKey();
					    if(value.equals(cValue.trim())){
						    ruleFieldParse(entry.getValue(), packet, data);
					    }
					} 
					//Ȼ�����ִ��Э���ӽڵ㣬����Э���ӽڵ�����
					for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//��ӡЭ���ӽڵ���Ϣ
					    System.out.println("VALUE = " + entry.getKey()); 
					    String value = entry.getKey();
					    if(value.equals(cValue.trim())){
					    	protocolTreeParse(entry.getValue(), packet, data);
					    }
					} 
				}
			} 
		}
	}
	/**
	 * �������н���Э�����
	 * @param length
	 * @param prelength
	 * @param data
	 */
	public byte[] contentParse(int length, int prelength, byte[] data){
		int bcount = 0;
		int remainder = 0;
		int precount = 0;
		int preremainder = 0;
		byte[] field = null;
		//�ж��ֶγ����Ƿ���8�ı���
		if(length % 8 == 0){
			bcount = length / 8;
			remainder = 0;
		}else{
			bcount = length / 8;
			remainder = length % 8;
		}
		//�ж��ֶ�֮ǰ�����ֽ����Ƿ���8�ı���
		if(prelength % 8 == 0){
			precount = prelength / 8;
			preremainder = 0;
		}else{
			precount = prelength / 8;
			preremainder = prelength % 8;
		}
		//�ֶ�֮ǰ����Ϊ�ֽڵ�������
		if(preremainder == 0){
			if(remainder == 0){
				field = new byte[bcount];
				for(int i = precount,count = 0; i < data.length && count < bcount; i++,count++){
					field[count] = data[i];
				}
			}else{
				field = new byte[bcount + 1];
				for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
					field[count] = data[i];
				}
				//�������һ�������ֽ�����
				afterRemainder(field, bcount, remainder);
			}
		}else{
			if(remainder == 0){
				field = new byte[bcount + 1];
				for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
					field[count] = data[i];
				}
				//����֮ǰһ�������ֽ�����
				beforeRemainder(field, precount - 1, preremainder);
				//�������һ���������ֽ�
				afterRemainder(field, bcount, 8 - preremainder);
			}else{
				//��ȡ�����׺
				int after = length - (8 - preremainder) - (bcount * 8);
				if(after <= 0){
					field = new byte[bcount + 1];
					for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//����֮ǰһ�������ֽ�����
					beforeRemainder(field, 0, preremainder);
					//�������һ���������ֽ�
					afterRemainder(field, bcount, -after);
				} else {
					field = new byte[bcount + 2];
					for(int i = precount - 1,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//����֮ǰһ�������ֽ�����
					beforeRemainder(field, 0, preremainder);
					//�������һ���������ֽ�
					afterRemainder(field, bcount + 1, after);
				}
			}
		}
		return field;
	}
	/**
	 * �����������Ϊ�������ֽ�
	 * @param data
	 * @param count
	 * @param remainder
	 */
	public void afterRemainder(byte[] data, int count, int remainder){
		switch(remainder){
			case 1:
				data[count] = (byte)(data[count] & ConstantV2.ONE);
				break;
			case 2:
				data[count] = (byte)(data[count] & ConstantV2.TWO);
				break;
			case 3:
				data[count] = (byte)(data[count] & ConstantV2.THREE);
				break;
			case 4:
				data[count] = (byte)(data[count] & ConstantV2.FOUR);
				break;
			case 5:
				data[count] = (byte)(data[count] & ConstantV2.FIVE);
				break;
			case 6:
				data[count] = (byte)(data[count] & ConstantV2.SIX);
				break;
			case 7:
				data[count] = (byte)(data[count] & ConstantV2.SEVEN);
				break;
		}
	}
	/**
	 * �����������Ϊ�������ֽ�
	 * @param data
	 * @param count
	 * @param remainder
	 */
	public void beforeRemainder(byte[] data, int count, int remainder){
		switch(remainder){
			case 1:
				data[count] = (byte)(data[count] & ConstantV2.ONE_Z);
				break;
			case 2:
				data[count] = (byte)(data[count] & ConstantV2.TWO_Z);
				break;
			case 3:
				data[count] = (byte)(data[count] & ConstantV2.THREE_Z);
				break;
			case 4:
				data[count] = (byte)(data[count] & ConstantV2.FOUR_Z);
				break;
			case 5:
				data[count] = (byte)(data[count] & ConstantV2.FIVE_Z);
				break;
			case 6:
				data[count] = (byte)(data[count] & ConstantV2.SIX_Z);
				break;
			case 7:
				data[count] = (byte)(data[count] & ConstantV2.SEVEN_Z);
				break;
		}
	}
	/**
	 * ͨ�����ʽ���������ֶγ���
	 * @param expression
	 * @param data
	 * @return
	 */
	public int getLengthByEx(String expression,byte[] data){
		int result = 0;
		Stack<String> opers = new Stack<String>();
		Stack<String> exps = new Stack<String>();
		String tempExp = expression;
		int posPlus = tempExp.indexOf("+");
		int posMin = tempExp.indexOf("-");
		int pos = -1;
		if(posPlus >= 0 && posMin >= 0){
			if(posPlus < posMin){
				pos = posPlus;
				opers.push("+");
			} else {
				pos = posMin;
				opers.push("-");
			}
		}else if(posPlus < 0 && posMin < 0){
			pos = -1;
		}else{
			if(posPlus < posMin){
				pos = posMin;
				opers.push("-");
			}else{
				pos = posPlus;
				opers.push("+");
			}
		}
		//�ж��Ƿ����
		while(pos != -1){
			String exp1 = tempExp.substring(0, pos);
			exps.push(exp1);
			tempExp = tempExp.substring(pos+1);
			System.out.println("��ջ��Ϊ��" + exp1);
			System.out.println("ʣ�മΪ��" + tempExp);
			posPlus = tempExp.indexOf("+");
			posMin = tempExp.indexOf("-");
			if(posPlus >= 0 && posMin >= 0){
				if(posPlus < posMin){
					pos = posPlus;
					opers.push("+");
				} else {
					pos = posMin;
					opers.push("-");
				}
			}else if(posPlus < 0 && posMin < 0){
				exps.push(tempExp);
				pos = -1;
			}else{
				if(posPlus < posMin){
					pos = posMin;
					opers.push("-");
				}else{
					pos = posPlus;
					opers.push("+");
				}
			}
		}
		LengthParser lParser = new LengthParser();
		result = lParser.LengthParser(opers, exps, treeRoot, data.length);
		return result;
	}
}
