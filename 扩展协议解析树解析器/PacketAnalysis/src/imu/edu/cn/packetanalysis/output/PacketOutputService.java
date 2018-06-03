package imu.edu.cn.packetanalysis.output;

import java.util.List;
public interface PacketOutputService<T> {
	/**
	 * @author huxinlei
	 * @function 解析后数据包获取接口
	 * @date 2015-3-9
	 */
	public List<T> getPacketData();
}
