package imu.edu.cn.packetanalysis.core;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.input.PacketsInputService;

import imu.edu.cn.packetanalysis.tools.Constant;
import imu.edu.cn.packetanalysis.tools.ConstantV2;
import imu.edu.cn.packetanalysis.tools.TypeConvertor;
import imu.edu.cn.packetanalysis.tools.ProtocolTreeShow;
import imu.edu.cn.packetanalysis.tools.ProtocolTreeNode;

import imu.edu.cn.packetanalysis.entry.ProtocolRoot;
import imu.edu.cn.packetanalysis.entry.RuleField;
import imu.edu.cn.packetanalysis.entry.LogicField;
import imu.edu.cn.packetanalysis.entry.FieldInfo;

public class ProtocolFileParserV2 {
	/**
	 * @author huxinlei
	 * @function ������չЭ���������
	 * @date 2015-11-17 
	 * @update date��
	 */
	//xml�ĵ���Ϣ
	private List<Document> xmlDocuments;
	//���������ݰ�
	private List<byte[]> packetData;
	//��չЭ�������ڵ�
	private ProtocolRoot treeRoot;
	
	//��ȡxml�ĵ���Ϣ
	public void setXmlDocuments(List<Document> xmlDocuments){
		this.xmlDocuments = xmlDocuments;
		//��ʼ����չЭ����ģ��
		initExtendedTree();
		//��ʼ����չЭ���������ʾ������
		ProtocolTreeShow showTool = new ProtocolTreeShow();
		showTool.protocolTreeShow(this.treeRoot);
		
	}
	//��ȡ���������ݰ�
	public void setPacketData(List<byte[]> packetData){
		this.packetData = packetData;
	}
	//��ʼ��Э������������ӽڵ��б�
	private void initTreeRoot(){
		this.treeRoot = new ProtocolRoot();
		List<RuleField> ruleChilds = new ArrayList<RuleField>();
		List<LogicField> logicChilds = new ArrayList<LogicField>();
		this.treeRoot.setRuleChilds(ruleChilds);
		this.treeRoot.setLogicChilds(logicChilds);
	}
	//��ʼ��չЭ�������
	private void initExtendedTree(){
		initTreeRoot();//��ʼ����չЭ�������ڵ���ӽڵ��б�
		int initIndex = 0;//���ƴ�����ĵ��±�
		if(this.xmlDocuments.size() > 0){
			Document doc = this.xmlDocuments.get(initIndex);
			Element element = doc.getDocumentElement();
			System.out.println("��Ԫ��Ϊ��" + element.getTagName());
			NodeList protoList = element.getElementsByTagName(Constant.PROTOCOLFILE);
			System.out.println("Э���б��ȣ�" + protoList.getLength());
			for(int i = 0; i < 1; i++){
				Node node = protoList.item(i);
				if(node instanceof Element){
					Element inode = (Element)node;
					Node sequence = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_SEQUENCE);
					Node name = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_NAME);
					Node hlength = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_HEADERLENGTH);
					Node prelength = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_PRELENGTH);
					System.out.println("PROCESS PROTOCOLFILE sequence:" + sequence.getNodeValue());
					System.out.println("PROCESS PROTOCOLFILE name:" + name.getNodeValue());
					System.out.println("PROCESS PROTOCOLFILE hlength:" + hlength.getNodeValue());
					System.out.println("PROCESS PROTOCOLFILE prelength:" + prelength.getNodeValue());
					//���ڵ㸳��ֵ
					this.treeRoot.setSequence(sequence.getNodeValue());
					this.treeRoot.setName(name.getNodeValue());
					int length = Integer.parseInt(hlength.getNodeValue());
					this.treeRoot.setLength(length);
					int prlength = Integer.parseInt(prelength.getNodeValue());
					this.treeRoot.setPrelength(prlength);
					
					NodeList fieldList = node.getChildNodes();
					System.out.println("Э���ֶ��б��ȣ�" + fieldList.getLength());
					int index = 0;
					for(int j = 0; j < fieldList.getLength(); j++){
						Node field = fieldList.item(j);
						if(field instanceof Element){
							System.out.println(field.getNodeName());
							System.out.println(field.getFirstChild().getNodeValue());
							index = index + 1;
							Element itemCurrent = (Element)field;
							
							if(itemCurrent.getTagName().equals(ConstantV2.RULEDEV)){
								processRuleDev(itemCurrent, this.treeRoot, null, "");
							} else if(itemCurrent.getTagName().equals(ConstantV2.LOGICDEV)){
								processLogicDev(itemCurrent, this.treeRoot, null, "", fieldList);
							}
						}
					}
					System.out.println("��ЧЭ���ֶ��б��ȣ�" + index);
				} 
			}
		}
	}
	//���������ֶ�
	private void processRuleDev(Element ruleField, ProtocolRoot root, LogicField logicField, String logicValue){
		Node sequence = ruleField.getAttributeNode(ConstantV2.RULEDEV_SEQUENCE);
		Node name = ruleField.getAttributeNode(ConstantV2.RULEDEV_NAME);
		Node prelength = ruleField.getAttributeNode(ConstantV2.RULEDEV_PRELENGTH);
		String length = ruleField.getFirstChild().getNodeValue();
		
		System.out.println("PROCESS RULEDEV sequence:" + sequence.getNodeValue());
		System.out.println("PROCESS RULEDEV name:" + name.getNodeValue());
		System.out.println("PROCESS RULEDEV prelength:" + prelength.getNodeValue());
		System.out.println("PROCESS RULEDEV length:" + length);
		
		RuleField rule = new RuleField();
		try{
			rule.setSequence(sequence.getNodeValue());
			rule.setName(name.getNodeValue());
			int preL = Integer.parseInt(prelength.getNodeValue());
			rule.setPrelength(preL);
			if(isExpression(length)){
				rule.setLength(-1);//���ڵ�������ʽ
				rule.setExpression(length);
			} else {
				int l = Integer.parseInt(length);
				rule.setLength(l);
				rule.setExpression(null);
			}
		}catch(Exception e){
			rule = null;
		}
		if(root != null){
			List<RuleField> list = root.getRuleChilds();
			list.add(rule);
			root.setRuleChilds(list);
		} else if(logicField != null){
			Map<String, RuleField> map = logicField.getRuleChilds();
			map.put(logicValue, rule);
			logicField.setRuleChilds(map);
		}
	}
	//�����߼��ֶ�
	private void processLogicDev(Element logicField,ProtocolRoot root, LogicField logicParent, String logicValue, NodeList nList){
		Node value = logicField.getAttributeNode(ConstantV2.LOGICDEV_VALUE);
		//��valueֵ��Ϊnullʱ��������ֻ�е�valueֵΪnullʱ���нڵ㴦��
		if(value == null){
			Node sequence = logicField.getAttributeNode(ConstantV2.LOGICDEV_SEQUENCE);
			Node name = logicField.getAttributeNode(ConstantV2.LOGICDEV_NAME);
			Node prelength = logicField.getAttributeNode(ConstantV2.LOGICDEV_PRELENGTH);
			String length = logicField.getFirstChild().getNodeValue();
			
			System.out.println("PROCESS LOGICDEV sequence:" + sequence.getNodeValue());
			System.out.println("PROCESS LOGICDEV name:" + name.getNodeValue());
			System.out.println("PROCESS LOGICDEV prelength:" + prelength.getNodeValue());
			//System.out.println("PROCESS LOGICDEV value:" + value.getNodeValue());
			System.out.println("PROCESS LOGICDEV length:" + length);
			
			LogicField logic = new LogicField();
			//Э���ӽڵ��б��ʼ��
			Map<String, ProtocolRoot> map1 = new HashMap<String, ProtocolRoot>();
			logic.setProChilds(map1);
			//���ݽڵ��б��ʼ��
			Map<String, RuleField> map2 = new HashMap<String, RuleField>();
			logic.setRuleChilds(map2);
			
			try{
				logic.setSequence(sequence.getNodeValue());
				logic.setName(name.getNodeValue());
				int preL = Integer.parseInt(prelength.getNodeValue());
				logic.setPrelength(preL);
				if(isExpression(length)){
					logic.setLength(-1);//���ڵ�������ʽ
					//rule.setExpression(length);
				} else {
					int l = Integer.parseInt(length);
					logic.setLength(l);
					//rule.setExpression(null);
				}
				for(int j = 0; j < nList.getLength(); j++){
					Node field = nList.item(j);
					if(field instanceof Element){
						Element itemCurrent = (Element)field;
						if(itemCurrent.getTagName().equals(ConstantV2.LOGICDEV)){
							Node ivalue = itemCurrent.getAttributeNode(ConstantV2.LOGICDEV_VALUE);
							if(ivalue != null){
								Node isequence = logicField.getAttributeNode(ConstantV2.LOGICDEV_SEQUENCE);
								String lvalue = ivalue.getNodeValue();
								String lsequence = isequence.getNodeValue();
								if(lsequence.equals(logic.getSequence())){
									NodeList fieldList = field.getChildNodes();
									System.out.println("Э���ֶ��б��ȣ�" + fieldList.getLength());
									for(int k = 0; k < fieldList.getLength(); k++){
										Node child = fieldList.item(k);
										if(child instanceof Element){
											System.out.println("------" + child.getNodeName());
											System.out.println("------" + child.getFirstChild().getNodeValue());
											Element childCurrent = (Element)child;
											if(childCurrent.getTagName().equals(ConstantV2.RULEDEV)){
												processRuleDev(childCurrent, null, logic, lvalue);
											} else if (childCurrent.getTagName().equals(ConstantV2.PROTOCOLINSPIRE)){
												processProRoot(childCurrent, logic, lvalue);
											}
										}
									}
								}
							}
						}
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				logic = null;
			}
			if(root != null){
				List<LogicField> list = root.getLogicChilds();
				list.add(logic);
				root.setLogicChilds(list);
			}
		}
	}
	//������ڵ�����
	private void processProRoot(Element proRoot, LogicField logic, String logicValue){
		Node seq = proRoot.getAttributeNode(ConstantV2.PROTOCOLINSPIRE_SEQUENCE);
		String target = seq.getNodeValue();//��ȡЭ�����
		
		ProtocolRoot treeRoot = new ProtocolRoot();//��ʼ��Э����ڵ�
		List<RuleField> ruleChilds = new ArrayList<RuleField>();
		List<LogicField> logicChilds = new ArrayList<LogicField>();
		treeRoot.setRuleChilds(ruleChilds);
		treeRoot.setLogicChilds(logicChilds);
		
		int initIndex = 0;//ȷ�������ĵ��±�
		if(this.xmlDocuments.size() > 0){
			Document doc = this.xmlDocuments.get(initIndex);
			Element element = doc.getDocumentElement();
			System.out.println("��Ԫ��Ϊ��" + element.getTagName());
			NodeList protoList = element.getElementsByTagName(Constant.PROTOCOLFILE);
			System.out.println("Э���б��ȣ�" + protoList.getLength());
			for(int i = 0; i < protoList.getLength(); i++){
				Node node = protoList.item(i);
				if(node instanceof Element){
					Element inode = (Element)node;
					Node sequence = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_SEQUENCE);
					if(sequence.getNodeValue().equals(target)){//�ж��ǲ���Ҫ�����Э��
						Node name = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_NAME);
						Node hlength = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_HEADERLENGTH);
						Node prelength = inode.getAttributeNode(ConstantV2.PROTOCOLFILE_PRELENGTH);
						System.out.println("PROCESS PROTOCOLFILE sequence:" + sequence.getNodeValue());
						System.out.println("PROCESS PROTOCOLFILE name:" + name.getNodeValue());
						System.out.println("PROCESS PROTOCOLFILE hlength:" + hlength.getNodeValue());
						System.out.println("PROCESS PROTOCOLFILE prelength:" + prelength.getNodeValue());
						//���ڵ㸳��ֵ
						treeRoot.setSequence(sequence.getNodeValue());
						treeRoot.setName(name.getNodeValue());
						int length = Integer.parseInt(hlength.getNodeValue());
						treeRoot.setLength(length);
						int prlength = Integer.parseInt(prelength.getNodeValue());
						treeRoot.setPrelength(prlength);
						
						NodeList fieldList = node.getChildNodes();
						System.out.println("Э���ֶ��б��ȣ�" + fieldList.getLength());
						int index = 0;
						for(int j = 0; j < fieldList.getLength(); j++){
							Node field = fieldList.item(j);
							if(field instanceof Element){
								System.out.println(field.getNodeName());
								System.out.println(field.getFirstChild().getNodeValue());
								index = index + 1;
								Element itemCurrent = (Element)field;
								if(itemCurrent.getTagName().equals(ConstantV2.RULEDEV)){
									processRuleDev(itemCurrent, treeRoot, null, "");
								} else if(itemCurrent.getTagName().equals(ConstantV2.LOGICDEV)){
									processLogicDev(itemCurrent, treeRoot, null, "", fieldList);
								}
							}
						}
						System.out.println("��ЧЭ���ֶ��б��ȣ�" + index);
						//��ӵ��߼��ڵ���
						if(logic != null){
							Map<String, ProtocolRoot> map = logic.getProChilds();
							map.put(logicValue, treeRoot);
							logic.setProChilds(map);
						}
					}
				} 
			}
		}
	}
	//�ж��Ƿ���ʽ
	private boolean isExpression(String expression){
		boolean result = false;
		int pos1 = expression.indexOf("length");
		int pos2 = expression.indexOf("prelength");
		if(!(pos1 < 0 && pos2 < 0)){
			result = true;
		}
		return result; 
	}
	//��ʼ�����ݽڵ�
	public PacketMapService packetsParsser(){
		Date begin = new Date();
		System.out.println("Э�������ģ�ʹ������ݰ���ʽ��ʼʱ�䣺" + begin.toLocaleString());
		PacketMapService packets = new PacketMapService();
		List<Map> listMap = new ArrayList<Map>();
		PacketParser parser = new PacketParser();
		parser.setPacketData(this.packetData);
		parser.setTreeRoot(this.treeRoot);
		listMap = parser.packetParse();
		packets.setPacketData(listMap);
		System.out.println("���ݰ��ܳ��ȣ�" + this.packetData.size());
		for(Map one : listMap){
			System.out.println("�ֶγ��ȣ�" + one.size());
		}
		System.out.println("�µĽ����������ڹ���......");
		Date end = new Date();
		System.out.println("Э�������ģ�ʹ������ݰ���ʽ����ʱ�䣺" + end.toLocaleString());
        FileWriter writer;
        try {
            writer = new FileWriter("C:/tree.txt");
            writer.write("Э�������ģ�ʹ������ݰ���ʽ��ʼʱ�䣺" + begin.toLocaleString());
            writer.write("Э�������ģ�ʹ������ݰ���ʽ����ʱ�䣺" + end.toLocaleString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return packets;
	}
	//����Э���ֶν��н���
	public PacketMapService packetsParserBySequence(String sequence){
		ProtocolTreeNode treeNode = new ProtocolTreeNode();
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo = treeNode.protocolTreeShow(this.treeRoot, sequence, fieldInfo);
		Date begin = new Date();
		System.out.println("Э�������ģ�ʹ������ݰ���ʽ��ʼʱ�䣺" + begin.toLocaleString());
		PacketMapService packets = new PacketMapService();
		List<Map> listMap = new ArrayList<Map>();
		PacketParser parser = new PacketParser();
		parser.setPacketData(this.packetData);
		parser.setTreeRoot(this.treeRoot);
		listMap = parser.packetParseBySequence(fieldInfo);
		packets.setPacketData(listMap);
		System.out.println("���ݰ��ܳ��ȣ�" + this.packetData.size());
		for(Map one : listMap){
			System.out.println("�ֶγ��ȣ�" + one.size());
		}
		System.out.println("�µĽ����������ڹ���......");
		Date end = new Date();
		System.out.println("Э�������ģ�ʹ������ݰ���ʽ����ʱ�䣺" + end.toLocaleString());
        FileWriter writer;
        try {
            writer = new FileWriter("C:/tree_field.txt");
            writer.write("Э�������ģ�ʹ������ݰ���ʽ��ʼʱ�䣺" + begin.toLocaleString());
            writer.write("Э�������ģ�ʹ������ݰ���ʽ����ʱ�䣺" + end.toLocaleString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return packets;
	}
	
	public PacketClassService classesParser(){
		PacketClassService classes = new PacketClassService();
		PacketMapService packets = this.packetsParsser();
		ProtocolClassService classService = new ProtocolClassService();
		int docIndex = this.xmlDocuments.size() - 1;
		Document doc = this.xmlDocuments.get(docIndex);//������������ļ�
		Element element = doc.getDocumentElement();
		System.out.println("��Ԫ��Ϊ��" + element.getTagName());
		Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_CLASSNAME);
		System.out.println("��������" + proLength.getNodeName() + " "
				+ "����ֵ��" + proLength.getNodeValue());
		classService.initClasses(proLength.getNodeValue(), packets, classes);
		System.out.println("classesParser����������...");
		return classes;
	}
}
