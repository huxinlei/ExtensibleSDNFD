package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.tools.Constant;
public class AnalysisController {
	/**
	 * @author huxinlei
	 * @function �������������
	 * @date 2015-3-9 
	 * @�����޸����ڣ�2015-3-9
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
		this.protocolParser.setXmlDocuments(this.protocolReader.getXmlDocuments());//Ϊ�������ṩxml�ĵ���Ϣ
		
		this.packetInput = packetInput;
		this.protocolParser.setPacketData(this.packetInput.getPacketData());//����ǰ�����б�
	}
	
	public void setPacketInput(PacketsInputService packetInput){
		this.packetInput = packetInput;
		this.protocolParser.setPacketData(this.packetInput.getPacketData());//����ǰ�����б�
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
			System.out.println("�����������Map��ʽ�洢!");
			
		}else if(outputType.toLowerCase().equals(Constant.OUTPUT_TYPE_CLASS)){
			this.packetOutput = this.classesParser();
			System.out.println("���������������ʽ�洢!");
			
		}else{
			System.out.println("����������������!");
			
		}
		return this.packetOutput;
	}
}
