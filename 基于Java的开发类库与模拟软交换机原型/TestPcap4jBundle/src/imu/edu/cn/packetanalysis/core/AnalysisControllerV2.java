package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.tools.Constant;
public class AnalysisControllerV2 {
	/**
	 * @author huxinlei
	 * @function 解析程序控制类
	 * @date 2015-3-9 
	 * @最终修改日期：2015-3-9
	 */
	private ProtocolFileParserV2 protocolParser;
	private ProtocolFileReaderV2 protocolReader;
	
	private PacketsInputService packetInput;
	private PacketOutputService packetOutput;

	public AnalysisControllerV2(String fileName, 
			PacketsInputService packetInput){
		this.protocolParser = new ProtocolFileParserV2();
		this.protocolReader = new ProtocolFileReaderV2();
		this.protocolReader.initDocuments(fileName);
		this.protocolParser.setXmlDocuments(this.protocolReader.getXmlDocuments());//为解析类提供xml文档信息
		
		this.packetInput = packetInput;
		this.protocolParser.setPacketData(this.packetInput.getPacketData());//解析前数据列表
	}
	
	public void setPacketInput(PacketsInputService packetInput){
		this.packetInput = packetInput;
		this.protocolParser.setPacketData(this.packetInput.getPacketData());//解析前数据列表
	}
	
	private PacketMapService packetsParser(){
		return this.protocolParser.packetsParsser();
	}
	
	private PacketClassService classesParser(){
		return this.protocolParser.classesParser();
	}
	
	public PacketOutputService analysisRun(String outputType){
		
		if(outputType.toLowerCase().equals(Constant.OUTPUT_TYPE_MAP)){
			
			this.packetOutput = this.packetsParser();
			System.out.println("解析结果将以Map形式存储!");
			
		}else if(outputType.toLowerCase().equals(Constant.OUTPUT_TYPE_CLASS)){
			
			this.packetOutput = this.classesParser();
			System.out.println("解析结果将以类形式存储!");
			
		}else{
			
			System.out.println("参数输入有误，请检查!");
			
		}
		return this.packetOutput;
	}

	public PacketOutputService analysisRunBySequence(String sequence){
		this.packetOutput = this.protocolParser.packetsParserBySequence(sequence);
		System.out.println("解析结果将以Map形式存储!");
		return this.packetOutput;
	}
}
