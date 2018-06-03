package imu.edu.cn.packetanalysis.output;

import java.util.List;
import java.util.ArrayList;

public class PacketClassService implements PacketOutputService<Object> {
	/**
	 * @author huxinlei
	 * @function 解析数据Class类
	 * @date 2015-3-9
	 */
	private List<Object> packetData;
	
	public List<Object> getPacketData(){
		
		if(this.packetData == null){
			List<Object> packetData = new ArrayList<Object>();
		}
		return packetData;
	}
	
	public void setPacketData(List<Object> packetData){
		this.packetData = packetData;
	}
}
