package imu.edu.cn.basecom;

import org.pcap4j.util.MacAddress;

import java.util.List;
public class ActionExecutor {
	//ת�����������ݰ��ŵ����Ͷ˿���
	public void executeActions(BasePacket packet, List<Port> portList){
		ActionSet actions = packet.getAlist();
		if(actions != null && actions.getCurLength() > 0){
			Action action = actions.popAction();
			if(action != null && action.getType() == Constant.FORWARD){
				for(int i = 0; i < portList.size(); i++){
					MacAddress portMac = (MacAddress)portList.get(i).getPInterface().getLinkLayerAddresses().get(i);
					if(portMac.equals(action.getPortAddress())){
						portList.get(i).getOutBuffer().pushPacket(packet.getOrignalDatas());
						break;
					}
				}
			}
		}
	}
}
