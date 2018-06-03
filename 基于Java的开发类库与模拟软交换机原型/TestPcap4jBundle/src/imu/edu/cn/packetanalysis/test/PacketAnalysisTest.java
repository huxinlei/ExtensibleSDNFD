package imu.edu.cn.packetanalysis.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import imu.edu.cn.packetanalysis.tools.Constant;
import imu.edu.cn.packetanalysis.tools.TypeConvertor;

import imu.edu.cn.packetanalysis.tools.PacketSimulator;
import imu.edu.cn.packetanalysis.input.PacketFileService;
import imu.edu.cn.packetanalysis.input.PacketNetworkService;

import imu.edu.cn.packetanalysis.compare.CompareParser;
import imu.edu.cn.packetanalysis.core.ProtocolFileReader;
import imu.edu.cn.packetanalysis.core.AnalysisController;
import imu.edu.cn.packetanalysis.core.AnalysisControllerV2;
import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.entry.LogicDevClass;

public class PacketAnalysisTest {
	/**
	 * @author huxinlei
	 * @function 程序测试类
	 * @date 2015-3-8
	 * @最终修改日期：2015-3-12
	 */
	public static void main(String[] args) {
		//List<Map> test1 = new ArrayList<Map>();
		//Map item1 = new HashMap();
		//item1.put("item1_1", "value1");
		//item1.put("item1_2", "value2");
		
		//Map item2 = new HashMap();
		//item2.put("item2_1", "value1");
		//item2.put("item2_2", "value2");
		
		//test1.add(item1);
		//test1.add(item2);
		
		//String value1 = test1.get(0).get("item1_1").toString();
		//System.out.println(value1);
		//String value2 = test1.get(1).get("item2_2").toString();
		//System.out.println(value2);
		
		//TestPacketFileService();//测试模拟数据包生成
		
		//TestPacketNetworkService();//测试网络数据包生成
		
		//TestProtocolFileReader();//测试xml文件读取类
		
		//TestAnalysisControllerChain();//测试控制类,链方案的可行性
		
		TestAnalysisControllerV2();//测试扩展协议解析树模型
		
		//TestAnalysisControllerV3();//测试单字段扩展协议解析树模型
		
		//TestTypeConvertor();//测试类型转换工具类
		
		//TestAnalysisControllerLogicDev();//测试控制类，逻辑控件的可行性
	}
	
	public static void TestPacketFileService(){
		PacketSimulator.packetSimulator();
		System.out.println("=====================================");
		PacketSimulator.packetSimulator2();//生成包含逻辑器的数据包
		PacketFileService fileService = new PacketFileService();
		try{
			fileService.initPacketData("D:\\protocolfile.pro");
			List<byte[]> packetData = fileService.getPacketData();
			System.out.println("列表长度：" + packetData.size());
			if(packetData.size() > 0){
				
				for(int i = 0; i < packetData.size(); i++){
					System.out.println("第" + i + "个byte数组长度：" + packetData.get(i).length);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void TestPacketNetworkService(){
		PacketNetworkService networkService = new PacketNetworkService();
		try{
			networkService.initPacketData();
			List<byte[]> packetData = networkService.getPacketData();
			System.out.println("列表长度：" + packetData.size());
			if(packetData.size() > 0){
				
				for(int i = 0; i < packetData.size(); i++){
					System.out.println("第" + (i+1) + "个byte数组长度：" + packetData.get(i).length);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static void TestProtocolFileReader(){
		ProtocolFileReader xmlReader = new ProtocolFileReader();
		xmlReader.initDocuments("testXml.xml");
		System.out.println(xmlReader.getXmlDocuments().size());
	}
	/**
	 * 扩展协议解析树解析对比测试
	 */
	public static void TestAnalysisControllerV2(){

		try{
			PacketNetworkService networkService = new PacketNetworkService();
			try{
				networkService.initPacketData();
				//PacketFileService fileService = new PacketFileService();
				//fileService.initPacketData("D:\\protocolfile.pro");
				//PacketsInputService packetInput = fileService;
				PacketOutputService packetOutputM = new PacketMapService();
				//PacketOutputService packetOutputC = new PacketClassService();
				String xmlName = "protocol.xml";
				AnalysisControllerV2 protocolController = new AnalysisControllerV2(xmlName, networkService);
				packetOutputM = protocolController.analysisRun(Constant.OUTPUT_TYPE_MAP);
				

				CompareParser parser = new CompareParser(networkService);
				packetOutputM = parser.analysisRun();

				System.out.println("解析后结果:" + packetOutputM.getPacketData().size());
				System.out.println("解析后结果:" );
				//List<Map> packetAnalysis = packetOutputM.getPacketData();			
				//packetOutputC = protocolController.analysisRun(Constant.OUTPUT_TYPE_CLASS);
			}catch(Exception e){
				e.printStackTrace();
			}				
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
	/**
	 * 扩展协议解析树单字段解析对比测试
	 */
	public static void TestAnalysisControllerV3(){

		try{
			PacketNetworkService networkService = new PacketNetworkService();
			try{
				networkService.initPacketData();
				PacketOutputService packetOutputM = new PacketMapService();
				String xmlName = "protocol.xml";
				AnalysisControllerV2 protocolController = new AnalysisControllerV2(xmlName, networkService);
				String field = "1.11";
				packetOutputM = protocolController.analysisRunBySequence(field);
				

				CompareParser parser = new CompareParser(networkService);
				packetOutputM = parser.analysisRun();

				System.out.println("解析后结果:" + packetOutputM.getPacketData().size());
				System.out.println("解析后结果:" );
			}catch(Exception e){
				e.printStackTrace();
			}				
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
	public static void TestAnalysisControllerChain(){

		try{
			
			PacketFileService fileService = new PacketFileService();
			fileService.initPacketData("D:\\protocolfile.pro");
			PacketsInputService packetInput = fileService;
			PacketOutputService packetOutputM = new PacketMapService();
			PacketOutputService packetOutputC = new PacketClassService();
			String xmlName = "testXml.xml";
			AnalysisController protocolController = new AnalysisController(xmlName, packetInput);
			packetOutputM = protocolController.analysisRun(Constant.OUTPUT_TYPE_MAP);
			
			System.out.println("解析后结果:" + packetOutputM.getPacketData().size());
			System.out.println("解析后结果:" );
			List<Map> packetAnalysis = packetOutputM.getPacketData();
			for(int i = 0; i < packetAnalysis.size(); i++){
				System.out.println("数据量:" + packetAnalysis.get(i).size());
				Map temp = packetAnalysis.get(i);
				String headerLength = temp.get(Constant.PROTOCOLFILE_HL).toString();
				System.out.println("HeaderLength:" + headerLength);
				
				byte[] rulefield1 = (byte[])temp.get("rulefield1");
				for(int j = 0; j < rulefield1.length; j++){
					System.out.print(rulefield1[j] + " ");
				}
				System.out.println();
				
				byte[] rulefield2 = (byte[])temp.get("rulefield2");
				for(int j = 0; j < rulefield2.length; j++){
					System.out.print(rulefield2[j] + " ");
				}
				System.out.println();
				
				byte[] rulefield3 = (byte[])temp.get("rulefield3");
				for(int j = 0; j < rulefield3.length; j++){
					System.out.print(rulefield3[j] + " ");
				}
				System.out.println();
				
				byte[] rulefield4 = (byte[])temp.get("rulefield4");
				for(int j = 0; j < rulefield4.length; j++){
					System.out.print(rulefield4[j] + " ");
				}
				System.out.println();
				
				byte[] rulefield5 = (byte[])temp.get("rulefield5");
				for(int j = 0; j < rulefield5.length; j++){
					System.out.print(rulefield5[j] + " ");
				}
				System.out.println();
				
				byte[] protocoldata = (byte[])temp.get(Constant.PROTOCOLDATA);
				System.out.println("数据字段长度:" + protocoldata.length);
				for(int j = 0; j < protocoldata.length; j++){
					System.out.print(protocoldata[j] + " ");
				}
				System.out.println();
			}
			
			//packetOutputC = protocolController.analysisRun(Constant.OUTPUT_TYPE_CLASS);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}		
	}
	
	public static void TestAnalysisControllerLogicDev(){

		try{
			
			PacketFileService fileService = new PacketFileService();
			fileService.initPacketData("D:\\protocolfile1.pro");
			PacketsInputService packetInput = fileService;
			PacketOutputService packetOutputM = new PacketMapService();
			PacketOutputService packetOutputC = new PacketClassService();
			String xmlName = "protocol_logicdev.xml";
			AnalysisController protocolController = new AnalysisController(xmlName, packetInput);
			packetOutputM = protocolController.analysisRun(Constant.OUTPUT_TYPE_MAP);
			
			System.out.println("解析后结果:" + packetOutputM.getPacketData().size());
			System.out.println("解析后结果:" );
			List<Map> packetAnalysis = packetOutputM.getPacketData();
			for(int i = 0; i < packetAnalysis.size(); i++){
				System.out.println("数据量:" + packetAnalysis.get(i).size());
				Map temp = packetAnalysis.get(i);
				String headerLength = temp.get(Constant.PROTOCOLFILE_HL).toString();
				System.out.println("HeaderLength:" + headerLength);
				
				byte[] rulefield1 = (byte[])temp.get("rulefield1");
				for(int j = 0; j < rulefield1.length; j++){
					System.out.print(rulefield1[j] + " ");
				}
				System.out.println();
				System.out.println("test logic value:" + temp.get("logicfield1"));
				int logicValue = Integer.parseInt(temp.get("logicfield1").toString());
				System.out.println(logicValue);
				
				if(logicValue == 0){
				
					byte[] rulefield2 = (byte[])temp.get("rulefield2");
					for(int j = 0; j < rulefield2.length; j++){
						System.out.print(rulefield2[j] + " ");
					}
					System.out.println();
				
					byte[] rulefield3 = (byte[])temp.get("rulefield3");
					for(int j = 0; j < rulefield3.length; j++){
						System.out.print(rulefield3[j] + " ");
					}
					System.out.println();
				
					byte[] rulefield4 = (byte[])temp.get("rulefield4");
					for(int j = 0; j < rulefield4.length; j++){
						System.out.print(rulefield4[j] + " ");
					}
					System.out.println();
				
					byte[] protocoldata = (byte[])temp.get(Constant.PROTOCOLDATA);
					System.out.println("数据字段长度:" + protocoldata.length);
					for(int j = 0; j < protocoldata.length; j++){
						System.out.print(protocoldata[j] + " ");
					}
				}else if(logicValue == 1){
					byte[] rulefield2 = (byte[])temp.get("rulefield2");
					for(int j = 0; j < rulefield2.length; j++){
						System.out.print(rulefield2[j] + " ");
					}
					System.out.println();
					
					byte[] rulefield3 = (byte[])temp.get("rulefield3");
					for(int j = 0; j < rulefield3.length; j++){
						System.out.print(rulefield3[j] + " ");
					}
					System.out.println();
					
					byte[] protocoldata = (byte[])temp.get(Constant.PROTOCOLDATA);
					System.out.println("数据字段长度:" + protocoldata.length);
					for(int j = 0; j < protocoldata.length; j++){
						System.out.print(protocoldata[j] + " ");
					}
				}
				System.out.println();
			}
			
			packetOutputC = protocolController.analysisRun(Constant.OUTPUT_TYPE_CLASS);
			
			for(int i = 0; i < packetOutputC.getPacketData().size(); i++){
				LogicDevClass logicdev = (LogicDevClass)packetOutputC.getPacketData().get(i);
				
				System.out.println("logicfield1:" + logicdev.getLogicfield1());
				if(logicdev.getLogicfield1() == 0){
					System.out.println("rulefield1 size:" + logicdev.getRulefield1().length);
					System.out.println("rulefield2 size:" + logicdev.getRulefield2().length);
					System.out.println("rulefield3 size:" + logicdev.getRulefield3().length);
					System.out.println("rulefield4 size:" + logicdev.getRulefield4().length);
					System.out.println("protocoldata size:" + logicdev.getProtocoldata().length);
				}else if(logicdev.getLogicfield1() == 1){
					System.out.println("rulefield1 size:" + logicdev.getRulefield1().length);
					System.out.println("rulefield2 size:" + logicdev.getRulefield2().length);
					System.out.println("rulefield3 size:" + logicdev.getRulefield3().length);
					System.out.println("protocoldata size:" + logicdev.getProtocoldata().length);
				}
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}			
	}
	
	public static void TestTypeConvertor(){
		int testNum = Integer.MAX_VALUE;
		byte[] testByte = TypeConvertor.intToBytes(testNum);
		
		for(int i = 0; i < testByte.length; i++){
			System.out.print(testByte[i] + " ");
		}
		
		byte[] testByte1 = new byte[4];
		testByte1[0] = 1;
		testByte1[1] = 0;
		testByte1[2] = 0;
		testByte1[3] = 0;
		
		int testNum1 = TypeConvertor.bytesToInt(testByte1, 0);
		System.out.println(testNum1);
		System.out.println(testNum1 == Integer.MAX_VALUE);
		
	}
}
