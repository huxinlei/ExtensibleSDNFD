package imu.edu.cn.packetanalysis.output;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class PacketMapService implements PacketOutputService<Map> {
	/**
	 * @author huxinlei
	 * @function 解析数据Map类
	 * @date 2015-3-9
	 */
	private List<Map> packetData;
	
	public List<Map> getPacketData(){
		
		if(this.packetData == null){
			List<Map> packetData = new ArrayList<Map>();
		}
		
		return packetData;
	}
	
	public void setPacketData(List<Map> packetData){
		this.packetData = packetData;
	}
}
