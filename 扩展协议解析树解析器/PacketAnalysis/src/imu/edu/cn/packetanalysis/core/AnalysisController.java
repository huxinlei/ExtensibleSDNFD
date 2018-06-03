package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.tools.Constant;
public class AnalysisController {
	/**
	 * @author huxinlei
	 * @function 解析程序控制类
	 * @date 2015-3-9 
	 * @最终修改日期：2015-3-9
	 */
	private ProtocolFileParser protocolParser;
	private ProtocolFileReader protocolReader;
	
	private PacketsInputService packetInput;
	private PacketOutputService packetOutput;

	public AnalysisController(String fileName, 
			PacketsInputService packetInput){
		this.protocolParser = new ProtocolFileParser();
		this.protocolReader = new ProtocolFileReader();
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
}
