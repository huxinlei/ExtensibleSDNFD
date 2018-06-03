package imu.edu.cn.packetanalysis.core;

import imu.edu.cn.packetanalysis.input.PacketsInputService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketClassService;

import imu.edu.cn.packetanalysis.tools.Constant;
public class AnalysisControllerV2 {
	/**
	 * @author huxinlei
	 * @function �������������
	 * @date 2015-3-9 
	 * @�����޸����ڣ�2015-3-9
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

	public PacketOutputService analysisRunBySequence(String sequence){
		this.packetOutput = this.protocolParser.packetsParserBySequence(sequence);
		System.out.println("�����������Map��ʽ�洢!");
		return this.packetOutput;
	}
}
