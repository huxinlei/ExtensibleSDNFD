package imu.edu.cn.packetanalysis.core;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import imu.edu.cn.packetanalysis.tools.Constant;

public class ProtocolFileReader {
	/**
	 * @author huxinlei
	 * @function 获取xml文件列表
	 * @date 2015-3-8
	 */
	private List<Document> xmlDocuments;
	
	//获取xml文档列表
	public List<Document> getXmlDocuments(){
		return this.xmlDocuments;
	}
	
	public void initDocuments(String fileName){
		String filePath = System.getProperty("java.class.path");
		int position = filePath.indexOf(";");
		boolean goOnFlag = true;
		this.xmlDocuments = new ArrayList<Document>();
		if(position > -1){
			filePath = filePath.substring(0, position);	
		}

		while(goOnFlag){
			String realPath = filePath + "\\xml\\" + fileName;
			System.out.println(realPath);
			
			try{
				Document doc = null;
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(new File(realPath));
				this.xmlDocuments.add(doc);
				Element element = doc.getDocumentElement();
				System.out.println("根元素为：" + element.getTagName());
				Node inspire = element.getAttributeNode("inspire");
		
				System.out.println(inspire.getNodeName() + " : " + inspire.getNodeValue() + "||类型：" + inspire.getNodeValue());
				if(inspire.getNodeValue().equals("true")){
					System.out.println("继续...");
					Node inspireFile = element.getElementsByTagName("protocolinspire").item(0);
					System.out.println(inspireFile.getNodeName() + " : " + inspireFile.getNodeValue() + "||类型：" + inspireFile.getNodeValue());		
					fileName = inspireFile.getFirstChild().getNodeValue();
					goOnFlag = true;
				}else{
					goOnFlag = false;
				}
			}catch(Exception e){
				System.out.println("ERROR:" + e.toString());
				goOnFlag = false;
			}			
		}

	}
}
