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
public class ProtocolFileReaderV2 {
	/**
	 * @author huxinlei
	 * @function 获取xml配置文件
	 * @date 2015-12-1
	 */
	private List<Document> xmlDocuments;
	//获取xml文档列表
	public List<Document> getXmlDocuments(){
		return this.xmlDocuments;
	}
	public void initDocuments(String fileName){
		//找到工程文件夹
		String filePath = System.getProperty("java.class.path");
		int position = filePath.indexOf(";");
		this.xmlDocuments = new ArrayList<Document>();
		if(position > -1){
			filePath = filePath.substring(0, position);
		}
		//获得xml文件的绝对路径
		String realPath = filePath + "\\xml\\" + fileName;
		System.out.println(realPath);
		//获取文件中文档对象
		try{
			Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new File(realPath));
			this.xmlDocuments.add(doc);
		}catch(Exception e){
			System.out.println("ERROR:" + e.toString());
		}
		
	}
}
