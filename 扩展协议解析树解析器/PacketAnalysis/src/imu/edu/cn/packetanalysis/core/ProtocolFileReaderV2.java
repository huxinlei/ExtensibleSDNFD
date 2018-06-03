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
	 * @function ��ȡxml�����ļ�
	 * @date 2015-12-1
	 */
	private List<Document> xmlDocuments;
	//��ȡxml�ĵ��б�
	public List<Document> getXmlDocuments(){
		return this.xmlDocuments;
	}
	public void initDocuments(String fileName){
		//�ҵ������ļ���
		String filePath = System.getProperty("java.class.path");
		int position = filePath.indexOf(";");
		this.xmlDocuments = new ArrayList<Document>();
		if(position > -1){
			filePath = filePath.substring(0, position);
		}
		//���xml�ļ��ľ���·��
		String realPath = filePath + "\\xml\\" + fileName;
		System.out.println(realPath);
		//��ȡ�ļ����ĵ�����
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
