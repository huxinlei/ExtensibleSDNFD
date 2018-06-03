package imu.edu.cn.packetanalysis.core;

import java.io.File;

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
import imu.edu.cn.packetanalysis.tools.TypeConvertor;

public class ProtocolFileParser {
	/**
	 * @author huxinlei
	 * @function ���������
	 * @date 2015-3-9 
	 * @�����޸����ڣ�2015-3-11
	 */
	private List<Document> xmlDocuments;
	private List<byte[]> packetData;
	
	public void setXmlDocuments(List<Document> xmlDocuments){
		this.xmlDocuments = xmlDocuments;
	}
	
	public void setPacketData(List<byte[]> packetData){
		this.packetData = packetData;
	}
	//��ʼ�汾����ʱ�Ȳ������Ŵ����⣬����������չʵ��
	public PacketMapService packetsParsser(){
		
		PacketMapService packets = new PacketMapService();
		List<Map> listMap = new ArrayList<Map>();
		System.out.println("packetsParser����������...");
		
		//���xml�����ĵ���ϢΪ�գ���������
		if(this.xmlDocuments.size() <= 0){
			return packets;
		}
		
		try{
			
			int docIndex = 0;//Ĭ��Ϊ0
			System.out.println("document index:" + docIndex);
			for(int i = 0; i < this.packetData.size(); i++){
				System.out.println("��" + i + "���ĳ���:" + this.packetData.get(i).length);
			}
			
			
			if(this.xmlDocuments.size() > 1){
				System.out.println("test run in...");
				int i = 0;
				
				for(; i < this.xmlDocuments.size() - 1; i++){
					Document doc = this.xmlDocuments.get(i);
					Element element = doc.getDocumentElement();
					System.out.println("��Ԫ��Ϊ��" + element.getTagName());
					Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_HL);
					System.out.println("��������" + proLength.getNodeName() + " "
							+ "����ֵ��" + proLength.getNodeValue());
					
					int headerLength = Integer.parseInt(proLength.getNodeValue());//bitΪ��λ��ת�����ֽ�
					int headerTypeL = headerLength / 8;
					List<byte[]> packetTemp = new ArrayList<byte[]>();
					for(int j = 0; j < this.packetData.size(); j++){
						byte[] temp = this.packetData.get(j);
						int newLength = temp.length - headerTypeL;
						byte[] newTemp = new byte[temp.length - headerTypeL];
						for(int k = 0; k < newLength; k++){
							newTemp[k] = temp[k + headerTypeL];
						}
						packetTemp.add(newTemp);
					}
					this.packetData = packetTemp;//���½�������
				}
				
				docIndex = i - 1;
				System.out.println("document index:" + docIndex);
				for(i = 0; i < this.packetData.size(); i++){
					System.out.println("��" + i + "���ĳ���:" + this.packetData.get(i).length);
				}
			}
			
			
			if(this.xmlDocuments.size() > 0){
				
				Document doc = this.xmlDocuments.get(docIndex);//������������ļ�
				Element element = doc.getDocumentElement();
				System.out.println("��Ԫ��Ϊ��" + element.getTagName());
				Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_HL);
				System.out.println("��������" + proLength.getNodeName() + " "
						+ "����ֵ��" + proLength.getNodeValue());
				
				int headerLength = Integer.parseInt(proLength.getNodeValue());//bitΪ��λ��ת�����ֽ�
				int headerTypeL = headerLength / 8;
				
				for(int j = 0; j < this.packetData.size(); j++){
						
					int currentByteIndex = 0;//��¼��ǰ�ֽ�λ��
					Map temp = new HashMap();
					NodeList nodeList = element.getChildNodes();
					byte[] currentData = this.packetData.get(j);//��õ�ǰ���������ݰ�
					
					temp.put(proLength.getNodeName(), proLength.getNodeValue());
					for(int i = 0; i < nodeList.getLength(); i++){
						
						
						Node item = nodeList.item(i);
					
						if(item instanceof Element){
						
							System.out.println("�ڵ�����:" + item.getNodeName());
							System.out.println("�ڵ��ֽ���:" + item.getFirstChild().getNodeValue());
						    Element itemCurrent = (Element)item;
							if(itemCurrent.getTagName().equals(Constant.RULEDEV)){
								Node name = itemCurrent.getAttributeNode(Constant.RULEDEV_NAME);
								System.out.println(item.getFirstChild().getNodeValue());//�������
								int fieldLength = Integer.parseInt(item.getFirstChild().getNodeValue().trim());
								int fieldTypeL = (fieldLength/8)==0?1:(fieldLength/8);
								byte[] tempByte = new byte[fieldTypeL];
								int beginByte = 0;
								for(int k = currentByteIndex; k < currentByteIndex + fieldTypeL; k++){
									tempByte[beginByte] = currentData[k];
									beginByte++;
								}
								temp.put(name.getNodeValue(), tempByte);
							    currentByteIndex = currentByteIndex + fieldTypeL;
								
							}else if(itemCurrent.getTagName().equals(Constant.LOGICDEV)){
								
								Node logicLength = itemCurrent.getAttributeNode(Constant.LOGICDEV_LENGTH);
								Node name = itemCurrent.getAttributeNode(Constant.LOGICDEV_NAME);
								Node value = itemCurrent.getAttributeNode(Constant.LOGICDEV_VALUE);
								
								int fieldValue = Integer.parseInt(value.getNodeValue().trim());
								int fieldTypeL = Integer.parseInt(logicLength.getNodeValue().trim())/8 == 0?1:
										Integer.parseInt(logicLength.getNodeValue().trim())/8;
								byte[] tempByte = new byte[fieldTypeL];
								int beginByte = 0;
								
								for(int k = currentByteIndex; k < currentByteIndex + fieldTypeL; k++){
									tempByte[beginByte] = currentData[k];
									beginByte++;
								}
								
								//����logicdev�ֶ�ֵ�Ƿ����
								if(fieldTypeL > 4){
									System.out.println("�ֶγ��ȳ�������Χ");
									return packets;
								}
								byte[] valueByteTemp = new byte[4];
								int betweenLength = 4 - fieldTypeL;

								for(int k = 0; k < fieldTypeL; k++){
									valueByteTemp[k] = tempByte[fieldTypeL - k - 1];
								}
								for(int k = 0; k < betweenLength; k++){
									valueByteTemp[k + fieldTypeL] = 0;
								}
								for(int k = 0; k < 4; k++){
									System.out.println(valueByteTemp[k]);
								}
								//��ȡ��ǰֵ
								int currentValue = TypeConvertor.bytesToInt(valueByteTemp, 0);
								System.out.println("��ǰlogicdev��ֵ:" + currentValue);
								System.out.println("��ǰname��ֵ:" + name.getNodeValue());
								if(fieldValue == currentValue){
									temp.put(name.getNodeValue(), currentValue);
									currentByteIndex = currentByteIndex + fieldTypeL;
									System.out.println("fieldTypeL:" + fieldTypeL);
									System.out.println("currentByteIndex:" + currentByteIndex);
									NodeList lnodeList = itemCurrent.getChildNodes();
									System.out.println("�ӽڵ��б���:" + lnodeList.getLength());
									for(int k = 0; k < lnodeList.getLength(); k++){
										Node lItem = lnodeList.item(k);
										System.out.println("Child Node" + k + lItem.getNodeName());
										if(lItem instanceof Element){
											Element lItemCurrent = (Element)lItem;
											if(lItemCurrent.getTagName().equals(Constant.RULEDEV)){
												Node lName = lItemCurrent.getAttributeNode(Constant.RULEDEV_NAME);
												System.out.println("Child Node" + k + lName.getNodeName());
												int lFieldLength = Integer.parseInt(lItemCurrent.getFirstChild().getNodeValue().trim());
												int lFieldTypeL = (lFieldLength/8)==0?1:(lFieldLength/8);
												byte[] lTempByte = new byte[lFieldTypeL];
												int lBeginByte = 0;
												for(int u = currentByteIndex; u < currentByteIndex + lFieldTypeL; u++){
													lTempByte[lBeginByte] = currentData[u];
													lBeginByte++;
												}
												temp.put(lName.getNodeValue(), lTempByte);
											    currentByteIndex = currentByteIndex + lFieldTypeL;
											}
											
										}
									}
							    }
							       
							}
						}
							
					}
					
					int dataLength = this.packetData.get(j).length - headerTypeL;
					byte[] newData = new byte[dataLength];
					for(int i = 0; i < dataLength; i++){
						newData[i] = currentData[i + headerTypeL];
					}
					
					temp.put(Constant.PROTOCOLDATA, newData);
					listMap.add(temp);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		packets.setPacketData(listMap);
		
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
