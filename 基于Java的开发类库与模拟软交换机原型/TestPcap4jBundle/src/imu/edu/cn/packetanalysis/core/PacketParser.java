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
	//待解析数据包数据
	private List<byte[]> packetData;
	//扩展协议解析树模型
	private ProtocolRoot treeRoot;
	
	/**
	 * 获取待解析数据包
	 * @return
	 */
	public List<byte[]> getPacketData() {
		return packetData;
	}
	/**
	 * 设置待解析数据包
	 * @param packetData
	 */
	public void setPacketData(List<byte[]> packetData) {
		this.packetData = packetData;
	}
	/**
	 * 获取扩展协议解析树模型
	 * @return
	 */
	public ProtocolRoot getTreeRoot() {
		return treeRoot;
	}
	/**
	 * 设置扩展协议解析树模型
	 * @param treeRoot
	 */
	public void setTreeRoot(ProtocolRoot treeRoot) {
		this.treeRoot = treeRoot;
	}
	/**
	 * 解析数据包
	 * @return
	 */
	public List<Map> packetParse(){
		List<Map> listMap = new ArrayList<Map>();
		if(this.packetData.size() > 0 && this.treeRoot != null){
			//循环处理每一个节点
			for(int i = 0; i < this.packetData.size(); i++){
				Map packet = onePacketParse(i);
				listMap.add(packet);
			}
		}
		return listMap;
	}
	/**
	 * 解析数据包根据序列号
	 */
	public List<Map> packetParseBySequence(FieldInfo fieldInfo){
		List<Map> listMap = new ArrayList<Map>();
		if(this.packetData.size() > 0 && fieldInfo.getType() > 0){
			//循环处理每一个节点
			for(int i = 0; i < this.packetData.size(); i++){
				Map packet = onePacketParseBySequence(i, fieldInfo);
				listMap.add(packet);
			}
		}
		return listMap;
	}
	/** 
	 * 解析单个数据包
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
	 * 解析单个数据包
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
	 * 遍历扩展协议解析树进行解析
	 * @param proRoot
	 * @param packet
	 * @param data
	 */
	public void protocolTreeParse(ProtocolRoot proRoot, Map packet, byte[] data){
		if(proRoot != null){
			protocolRootParse(proRoot, packet, data);//打印协议根节点
			for(RuleField rule : proRoot.getRuleChilds()){//打印内容节点
				ruleFieldParse(rule, packet, data);
			}
			for(LogicField logic : proRoot.getLogicChilds()){//打印判定节点
				logicFieldAndChildsParse(logic, packet, data);
			}
		}
	}
	/**
	 * 解析协议根节点
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
	 * 解析协议内容节点
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
				//获取字段计算表达式
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
	 * 解析协议判定节点
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
	 * 解析协议判定节点及其子节点
	 * @param logic
	 * @param packet
	 * @param data
	 */
	public void logicFieldAndChildsParse(LogicField logic, Map packet, byte[] data){
		if(logic != null){
			byte[] temp = logicFieldParse(logic, packet, data);//打印判定节点详细信息
			if(temp != null){
				if(temp.length <= 4){
					String cValue = TypeConvertor.bytesToHexString(temp);
					System.out.println("本次解析判断字段值：" + cValue);
					//先遍历执行内容子节点，截取内容子节点内容
					for (Map.Entry<String, RuleField> entry : logic.getRuleChilds().entrySet()) {//打印内容子节点信息
					    System.out.println("VALUE = " + entry.getKey());  
					    String value = entry.getKey();
					    if(value.equals(cValue.trim())){
						    ruleFieldParse(entry.getValue(), packet, data);
					    }
					} 
					//然后遍历执行协议子节点，解析协议子节点内容
					for (Map.Entry<String, ProtocolRoot> entry : logic.getProChilds().entrySet()) {//打印协议子节点信息
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
	 * 从数据中解析协议解析
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
		//判断字段长度是否是8的倍数
		if(length % 8 == 0){
			bcount = length / 8;
			remainder = 0;
		}else{
			bcount = length / 8;
			remainder = length % 8;
		}
		//判断字段之前数据字节数是否是8的倍数
		if(prelength % 8 == 0){
			precount = prelength / 8;
			preremainder = 0;
		}else{
			precount = prelength / 8;
			preremainder = prelength % 8;
		}
		//字段之前数据为字节的整数倍
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
				//处理最后一个非整字节数据
				afterRemainder(field, bcount, remainder);
			}
		}else{
			if(remainder == 0){
				field = new byte[bcount + 1];
				for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
					field[count] = data[i];
				}
				//处理之前一个非整字节数据
				beforeRemainder(field, precount - 1, preremainder);
				//处理最后一个非整数字节
				afterRemainder(field, bcount, 8 - preremainder);
			}else{
				//获取处理后缀
				int after = length - (8 - preremainder) - (bcount * 8);
				if(after <= 0){
					field = new byte[bcount + 1];
					for(int i = precount,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//处理之前一个非整字节数据
					beforeRemainder(field, 0, preremainder);
					//处理最后一个非整数字节
					afterRemainder(field, bcount, -after);
				} else {
					field = new byte[bcount + 2];
					for(int i = precount - 1,count = 0; i < data.length && count < bcount + 1; i++,count++){
						field[count] = data[i];
					}
					//处理之前一个非整字节数据
					beforeRemainder(field, 0, preremainder);
					//处理最后一个非整数字节
					afterRemainder(field, bcount + 1, after);
				}
			}
		}
		return field;
	}
	/**
	 * 处理后面数据为非整数字节
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
	 * 处理后面数据为非整数字节
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
	 * 通过表达式计算数据字段长度
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
		//判断是否结束
		while(pos != -1){
			String exp1 = tempExp.substring(0, pos);
			exps.push(exp1);
			tempExp = tempExp.substring(pos+1);
			System.out.println("入栈串为：" + exp1);
			System.out.println("剩余串为：" + tempExp);
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
