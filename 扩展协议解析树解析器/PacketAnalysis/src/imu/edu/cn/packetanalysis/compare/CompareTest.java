package imu.edu.cn.packetanalysis.compare;

import imu.edu.cn.packetanalysis.core.AnalysisControllerV2;
import imu.edu.cn.packetanalysis.input.PacketNetworkService;
import imu.edu.cn.packetanalysis.output.PacketMapService;
import imu.edu.cn.packetanalysis.output.PacketOutputService;
import imu.edu.cn.packetanalysis.tools.Constant;

public class CompareTest {

	public static void main(String[] args) {

		try{
			PacketNetworkService networkService = new PacketNetworkService();
			try{
				networkService.initPacketData();
				PacketOutputService packetOutputM = new PacketMapService();
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

}
