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
	 * @function 具体解析类
	 * @date 2015-3-9 
	 * @最终修改日期：2015-3-11
	 */
	private List<Document> xmlDocuments;
	private List<byte[]> packetData;
	
	public void setXmlDocuments(List<Document> xmlDocuments){
		this.xmlDocuments = xmlDocuments;
	}
	
	public void setPacketData(List<byte[]> packetData){
		this.packetData = packetData;
	}
	//初始版本，暂时先不考虑排错问题，留待将来扩展实现
	public PacketMapService packetsParsser(){
		
		PacketMapService packets = new PacketMapService();
		List<Map> listMap = new ArrayList<Map>();
		System.out.println("packetsParser解析进行中...");
		
		//如果xml配置文档信息为空，结束程序
		if(this.xmlDocuments.size() <= 0){
			return packets;
		}
		
		try{
			
			int docIndex = 0;//默认为0
			System.out.println("document index:" + docIndex);
			for(int i = 0; i < this.packetData.size(); i++){
				System.out.println("第" + i + "个的长度:" + this.packetData.get(i).length);
			}
			
			
			if(this.xmlDocuments.size() > 1){
				System.out.println("test run in...");
				int i = 0;
				
				for(; i < this.xmlDocuments.size() - 1; i++){
					Document doc = this.xmlDocuments.get(i);
					Element element = doc.getDocumentElement();
					System.out.println("根元素为：" + element.getTagName());
					Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_HL);
					System.out.println("属性名：" + proLength.getNodeName() + " "
							+ "属性值：" + proLength.getNodeValue());
					
					int headerLength = Integer.parseInt(proLength.getNodeValue());//bit为单位，转换成字节
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
					this.packetData = packetTemp;//更新解析数据
				}
				
				docIndex = i - 1;
				System.out.println("document index:" + docIndex);
				for(i = 0; i < this.packetData.size(); i++){
					System.out.println("第" + i + "个的长度:" + this.packetData.get(i).length);
				}
			}
			
			
			if(this.xmlDocuments.size() > 0){
				
				Document doc = this.xmlDocuments.get(docIndex);//获得最后的配置文件
				Element element = doc.getDocumentElement();
				System.out.println("根元素为：" + element.getTagName());
				Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_HL);
				System.out.println("属性名：" + proLength.getNodeName() + " "
						+ "属性值：" + proLength.getNodeValue());
				
				int headerLength = Integer.parseInt(proLength.getNodeValue());//bit为单位，转换成字节
				int headerTypeL = headerLength / 8;
				
				for(int j = 0; j < this.packetData.size(); j++){
						
					int currentByteIndex = 0;//记录当前字节位置
					Map temp = new HashMap();
					NodeList nodeList = element.getChildNodes();
					byte[] currentData = this.packetData.get(j);//获得当前待处理数据包
					
					temp.put(proLength.getNodeName(), proLength.getNodeValue());
					for(int i = 0; i < nodeList.getLength(); i++){
						
						
						Node item = nodeList.item(i);
					
						if(item instanceof Element){
						
							System.out.println("节点名称:" + item.getNodeName());
							System.out.println("节点字节数:" + item.getFirstChild().getNodeValue());
						    Element itemCurrent = (Element)item;
							if(itemCurrent.getTagName().equals(Constant.RULEDEV)){
								Node name = itemCurrent.getAttributeNode(Constant.RULEDEV_NAME);
								System.out.println(item.getFirstChild().getNodeValue());//输出测试
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
								
								//处理logicdev字段值是否相等
								if(fieldTypeL > 4){
									System.out.println("字段长度超出处理范围");
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
								//获取当前值
								int currentValue = TypeConvertor.bytesToInt(valueByteTemp, 0);
								System.out.println("当前logicdev的值:" + currentValue);
								System.out.println("当前name的值:" + name.getNodeValue());
								if(fieldValue == currentValue){
									temp.put(name.getNodeValue(), currentValue);
									currentByteIndex = currentByteIndex + fieldTypeL;
									System.out.println("fieldTypeL:" + fieldTypeL);
									System.out.println("currentByteIndex:" + currentByteIndex);
									NodeList lnodeList = itemCurrent.getChildNodes();
									System.out.println("子节点列表长度:" + lnodeList.getLength());
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
		Document doc = this.xmlDocuments.get(docIndex);//获得最后的配置文件
		Element element = doc.getDocumentElement();
		System.out.println("根元素为：" + element.getTagName());
		Node proLength = element.getAttributeNode(Constant.PROTOCOLFILE_CLASSNAME);
		System.out.println("属性名：" + proLength.getNodeName() + " "
				+ "属性值：" + proLength.getNodeValue());
		classService.initClasses(proLength.getNodeValue(), packets, classes);
		System.out.println("classesParser解析进行中...");
		return classes;
	}

}
