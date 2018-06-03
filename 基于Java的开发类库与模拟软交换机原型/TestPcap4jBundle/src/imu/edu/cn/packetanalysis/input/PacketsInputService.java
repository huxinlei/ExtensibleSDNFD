package imu.edu.cn.packetanalysis.input;

import java.util.List;
public interface PacketsInputService {
	/**
	 * @author huxinlei
	 * @function 数据包获取接口
	 * @date 2015-3-8
	 */
	public List<byte[]> getPacketData();
}
