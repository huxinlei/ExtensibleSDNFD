package imu.edu.cn.packetanalysis.output;

import java.util.List;
public interface PacketOutputService<T> {
	/**
	 * @author huxinlei
	 * @function ���������ݰ���ȡ�ӿ�
	 * @date 2015-3-9
	 */
	public List<T> getPacketData();
}
